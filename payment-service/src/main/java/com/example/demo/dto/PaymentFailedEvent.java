package com.example.demo.dto;

public class PaymentFailedEvent {

	private String orderId;
	private String paymentId;
	private String status;
	private String reason;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public PaymentFailedEvent(String orderId, String paymentId, String status, String reason) {
		super();
		this.orderId = orderId;
		this.paymentId = paymentId;
		this.status = status;
		this.reason = reason;
	}
	
}
