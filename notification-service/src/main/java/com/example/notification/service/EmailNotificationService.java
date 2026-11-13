package com.example.notification.service;

import org.springframework.stereotype.Service;

import com.example.notification.dto.PaymentSuccessEvent;

@Service
public class EmailNotificationService {

	 public void sendPaymentSuccessEmail(PaymentSuccessEvent event) {

	        System.out.println("========== EMAIL NOTIFICATION ==========");

	        System.out.println("Sending email to customer "
	                + "C101");

	        System.out.println("Order "
	                + event.getOrderId()
	                + " payment successful");

	        System.out.println("Payment ID: " + event.getPaymentId());
	        System.out.println("Amount: " + event.getAmount());

	        System.out.println("========================================");
	    }
}
