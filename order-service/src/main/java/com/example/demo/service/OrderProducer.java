package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderCreatedEvent;
@Service
public class OrderProducer{
	 @Autowired
	private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
	
	public void publishOrderCreatedEvent(OrderCreatedEvent event) {
		System.out.println("Publishing order created event: " + event);
		kafkaTemplate.send("order-created-topic", event);
	}
}
