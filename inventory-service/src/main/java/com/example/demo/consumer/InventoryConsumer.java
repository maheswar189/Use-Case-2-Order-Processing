package com.example.demo.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.demo.dto.OrderCreatedEvent;

@Component
public class InventoryConsumer {

	@KafkaListener(topics = "order-created-topic")
	public void consume(OrderCreatedEvent event) {

	    System.out.println("Inventory reserved for " + event.orderId());

	}
}
