package com.example.demo.consumer;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.dto.OrderCreatedEvent;
@Component
public class PaymentConsumer {

	@KafkaListener(topics = "order-created-topic")
	public void consume(OrderCreatedEvent event) {

	    System.out.println("Payment initiated for " + event.orderId() +"Timestamp: " +LocalDateTime.now() );

	}
}
