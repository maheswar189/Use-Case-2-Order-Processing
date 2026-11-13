package com.example.notification.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.notification.dto.PaymentSuccessEvent;
import com.example.notification.service.EmailNotificationService;
import com.example.notification.service.SmsNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PaymentNotificationConsumer {

    private final EmailNotificationService emailService;
    private final SmsNotificationService smsService;
    private final ObjectMapper objectMapper;

    public PaymentNotificationConsumer(
            EmailNotificationService emailService,
            SmsNotificationService smsService,
            ObjectMapper objectMapper) {

        this.emailService = emailService;
        this.smsService = smsService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "payment-success",groupId = "notification-group")
    public void consume(String message) throws Exception {

        System.out.println("Received payment success event: " + message);

        PaymentSuccessEvent event =
                objectMapper.readValue(message, PaymentSuccessEvent.class);

        // Fan-out to notification channels
        emailService.sendPaymentSuccessEmail(event);

        smsService.sendPaymentSuccessSms(event);
    }
}