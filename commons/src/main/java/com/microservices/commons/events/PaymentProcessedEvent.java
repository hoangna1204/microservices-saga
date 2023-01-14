package com.microservices.commons.events;

public record PaymentProcessedEvent(String paymentId, String orderId) {
}
