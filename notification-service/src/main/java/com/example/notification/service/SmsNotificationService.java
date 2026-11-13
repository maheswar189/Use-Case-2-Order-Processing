package com.example.notification.service;

import org.springframework.stereotype.Service;

import com.example.notification.dto.PaymentSuccessEvent;

@Service
public class SmsNotificationService {

	 public void sendPaymentSuccessSms(PaymentSuccessEvent event) {

	        System.out.println("========== SMS NOTIFICATION ==========");

	        System.out.println("Sending SMS to customer C101");

	        System.out.println("Your order "
	                + event.getOrderId()
	                + " payment was successful.");

	        System.out.println("======================================");
	    }
}
