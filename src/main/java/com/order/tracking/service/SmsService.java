package com.order.tracking.service;

public interface SmsService {
    void sendOrderStatusUpdate(String phoneNumber, String message);
    void sendOrderConfirmation(String phoneNumber, String message);
    void sendOrderCancellation(String phoneNumber, String message);
} 