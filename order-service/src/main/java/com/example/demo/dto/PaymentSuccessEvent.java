package com.example.demo.dto;

public class PaymentSuccessEvent {
	
	private String orderId;
	private String paymentId;
	private String status;
	private Double amount;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public PaymentSuccessEvent(String orderId, String paymentId, String status, Double amount) {
		super();
		this.orderId = orderId;
		this.paymentId = paymentId;
		this.status = status;
		this.amount = amount;
	}
	

}
