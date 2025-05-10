package com.order.tracking.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_STATUS_QUEUE = "order.status.queue";
    public static final String ORDER_STATUS_ROUTING_KEY = "order.status";
    public static final String ORDER_STATUS_DLX = "order.status.dlx";
    public static final String ORDER_STATUS_DLQ = "order.status.dlq";

    @Bean
    public Queue orderStatusQueue() {
        return QueueBuilder.durable(ORDER_STATUS_QUEUE)
                .withArgument("x-dead-letter-exchange", ORDER_STATUS_DLX)
                .withArgument("x-dead-letter-routing-key", ORDER_STATUS_DLQ)
                .build();
    }

    @Bean
    public Queue orderStatusDLQ() {
        return QueueBuilder.durable(ORDER_STATUS_DLQ).build();
    }

    @Bean
    public DirectExchange orderExchange() {
        return ExchangeBuilder.directExchange(ORDER_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public DirectExchange orderStatusDLX() {
        return ExchangeBuilder.directExchange(ORDER_STATUS_DLX)
                .durable(true)
                .build();
    }

    @Bean
    public Binding orderStatusBinding(Queue orderStatusQueue, DirectExchange orderExchange) {
        return BindingBuilder
                .bind(orderStatusQueue)
                .to(orderExchange)
                .with(ORDER_STATUS_ROUTING_KEY);
    }

    @Bean
    public Binding orderStatusDLQBinding(Queue orderStatusDLQ, DirectExchange orderStatusDLX) {
        return BindingBuilder
                .bind(orderStatusDLQ)
                .to(orderStatusDLX)
                .with(ORDER_STATUS_DLQ);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setRetryTemplate(retryTemplate());
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                // TODO: Implement error handling for failed message delivery
                System.err.println("Message delivery failed: " + cause);
            }
        });
        return rabbitTemplate;
    }
} 