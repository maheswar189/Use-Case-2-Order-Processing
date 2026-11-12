package com.example.demo.consumer;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.dto.OrderCreatedEvent;
import com.example.demo.dto.PaymentFailedEvent;
import com.example.demo.dto.PaymentSuccessEvent;
@Component
public class PaymentConsumer {

	 private final KafkaTemplate<String, Object> kafkaTemplate;
	 
	// Constructor injection
	    public PaymentConsumer(
	            KafkaTemplate<String, Object> kafkaTemplate) {
	        this.kafkaTemplate = kafkaTemplate;
	    }

	 
	@KafkaListener(topics = "order-created-topic", groupId = "payment-group")
	public void consume(OrderCreatedEvent event) {

	    System.out.println("Payment initiated for " + event.getOrderId() +"Timestamp: " +LocalDateTime.now() );

	    System.out.println("Processing payment: "
                + event.getOrderId() + ", Amount: " + event.getAmount() + ", Timestamp: " + LocalDateTime.now());

        boolean success = event.getAmount() <= 5000;
       
        if (success) {
            PaymentSuccessEvent payment =
                    new PaymentSuccessEvent(
                            event.getOrderId().toString(),
                            "PAY-" + event.getOrderId(),
                            "SUCCESS",
                            event.getAmount()
                    );

            kafkaTemplate.send(
                    "payment-success",
                    String.valueOf(event.getOrderId()),
                    payment
            );

        } else {
            PaymentFailedEvent payment =
                    new PaymentFailedEvent(
                            event.getOrderId(),
                            "PAY-" + event.getOrderId(),
                            "FAILED",
                            "Payment limit exceeded"
                    );

            kafkaTemplate.send(
                    "payment-failed",
                    String.valueOf(event.getOrderId()),
                    payment
            );
        }
    }
	}

