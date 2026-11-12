package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderCreatedEvent;
import com.example.demo.dto.PaymentFailedEvent;
import com.example.demo.dto.PaymentSuccessEvent;

import tools.jackson.databind.ObjectMapper;
@Service
public class OrderProducer{
	 @Autowired
	private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
	
	 private final ObjectMapper objectMapper;
	 public OrderProducer(
	            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate,
	            ObjectMapper objectMapper) {

	        this.kafkaTemplate = kafkaTemplate;
	        this.objectMapper = objectMapper;
	    }
	public void publishOrderCreatedEvent(OrderCreatedEvent event) {
		System.out.println("Publishing order created event: " + event);
		kafkaTemplate.send("order-created-topic", event);
	}
	
	@KafkaListener(topics = "payment-success",groupId = "order-group")
	public void publishPaymentSuccess(String message)
	{
		
		PaymentFailedEvent event =
	            objectMapper.readValue(message, PaymentFailedEvent.class);
		System.out.println(
		        "Order confirmed: " + event.getOrderId()
		    );
	}
	
	@KafkaListener( topics = "payment-failed",groupId = "order-group")
	public void paymentFailed(String message) {
		 PaymentFailedEvent event =
		            objectMapper.readValue(message, PaymentFailedEvent.class);
		    System.out.println(
		        "Payment failed: " + event.getOrderId()
		    );

		    // Update order status to PAYMENT_FAILED
		}
}
