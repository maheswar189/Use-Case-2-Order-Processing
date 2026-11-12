package com.example.demo.dto;

import java.util.List;

public class OrderCreatedEvent{
        String orderId;
        String customerId;
        Double amount;
        List<String> items;
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getCustomerId() {
			return customerId;
		}
		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		public List<String> getItems() {
			return items;
		}
		public void setItems(List<String> items) {
			this.items = items;
		}
        
        
        
}