
package com.example.demo.consumer;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.dto.OrderCreatedEvent;
import com.example.demo.dto.PaymentFailedEvent;
import com.example.demo.dto.PaymentSuccessEvent;

@Component
public class PaymentConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // Demo-only attempt tracking, separately for each order
    private final Map<String, AtomicInteger> attempts =
            new ConcurrentHashMap<>();

    // Constructor injection
    public PaymentConsumer(
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @RetryableTopic(
            attempts = "3",
            backOff = @BackOff(delay = 5000),
            retryTopicSuffix = "-retry",
            dltTopicSuffix = "-dlt"
    )
    @KafkaListener(
            topics = "order-created-topic",
            groupId = "payment-group"
    )
    public void consume(OrderCreatedEvent event) {

        String orderId = String.valueOf(event.getOrderId());

        AtomicInteger counter = attempts.computeIfAbsent(
                orderId,
                id -> new AtomicInteger(0)
        );

        int attempt = counter.incrementAndGet();

        System.out.println("----------------------------------");
        System.out.println("Processing Order: " + orderId);
        System.out.println("Attempt: " + attempt);
        System.out.println("Timestamp: " + LocalDateTime.now());

        // Scenario 1: Fail only on first attempt.
        // Retry should succeed on second attempt.
        if ("ORD-RETRY-001".equals(orderId) && attempt == 1) {

            System.out.println(
                    "Temporary payment processing failure!"
            );

            throw new RuntimeException(
                    "Payment gateway temporarily unavailable"
            );
        }

        // Scenario 2: Fail on every attempt.
        // Message should eventually move to DLT.
        if ("ORD-DLT-001".equals(orderId)) {

            System.out.println(
                    "Permanent simulated failure for " + orderId
            );

            throw new RuntimeException(
                    "Payment gateway permanently unavailable"
            );
        }

        System.out.println(
                "Payment processing attempt completed for " + orderId
        );

        // Business payment validation
        boolean success = event.getAmount() <= 5000;

        if (success) {

            PaymentSuccessEvent payment =
                    new PaymentSuccessEvent(
                            orderId,
                            "PAY-" + orderId,
                            "SUCCESS",
                            event.getAmount()
                    );

            kafkaTemplate.send(
                    "payment-success",
                    orderId,
                    payment
            );

            System.out.println(
                    "Payment successful for order: " + orderId
            );

        } else {

            PaymentFailedEvent payment =
                    new PaymentFailedEvent(
                            orderId,
                            "PAY-" + orderId,
                            "FAILED",
                            "Payment limit exceeded"
                    );

            kafkaTemplate.send(
                    "payment-failed",
                    orderId,
                    payment
            );

            System.out.println(
                    "Payment rejected for order: " + orderId
            );
        }

        System.out.println("----------------------------------");
    }

    // Called after all retry attempts fail
    @DltHandler
    public void handleDlt(OrderCreatedEvent event) {

        String orderId = String.valueOf(event.getOrderId());

        System.err.println("========== DLT MESSAGE ==========");

        System.err.println("Order ID: " + orderId);

        System.err.println(
                "Payment processing failed after all attempts."
        );

        System.err.println(
                "Manual investigation required."
        );

        System.err.println("=================================");
    }
}
