package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderCreatedEvent;
import com.example.demo.service.OrderProducer;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderProducer producer;

	@PostMapping
	public String createOrder(@RequestBody OrderCreatedEvent event) {

		producer.publishOrderCreatedEvent(event);

		return "Order published successfully" +"Timestamp: " +LocalDateTime.now() ;
	}
}
