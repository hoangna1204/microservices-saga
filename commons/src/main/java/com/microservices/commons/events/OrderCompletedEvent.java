package com.microservices.commons.events;

public record OrderCompletedEvent(String orderId, String orderStatus) {
}
