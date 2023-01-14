package com.microservices.commons.events;

public record OrderCancelledEvent(String orderId, String orderStatus) {
}
