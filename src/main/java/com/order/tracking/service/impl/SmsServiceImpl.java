package com.order.tracking.service.impl;

import com.order.tracking.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    @Override
    public void sendOrderStatusUpdate(String phoneNumber, String message) {
        sendSms(phoneNumber, message);
    }

    @Override
    public void sendOrderConfirmation(String phoneNumber, String message) {
        sendSms(phoneNumber, message);
    }

    @Override
    public void sendOrderCancellation(String phoneNumber, String message) {
        sendSms(phoneNumber, message);
    }

    private void sendSms(String phoneNumber, String message) {
        try {
            // TODO: Implement actual SMS sending logic using a third-party service
            log.info("SMS sent to {}: {}", phoneNumber, message);
        } catch (Exception e) {
            log.error("Failed to send SMS to: {}", phoneNumber, e);
            throw new RuntimeException("Failed to send SMS", e);
        }
    }
} 