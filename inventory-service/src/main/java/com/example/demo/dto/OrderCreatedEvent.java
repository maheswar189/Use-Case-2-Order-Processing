package com.example.demo.dto;

import java.util.List;

public record OrderCreatedEvent(
        String orderId,
        String customerId,
        Double amount,
        List<String> items
) {}