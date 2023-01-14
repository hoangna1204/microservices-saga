package com.microservices.commons.events;

public record PaymentCancelledEvent(String paymentId, String orderId, String paymentStatus) {
}
