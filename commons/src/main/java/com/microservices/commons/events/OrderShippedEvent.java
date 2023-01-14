package com.microservices.commons.events;

public record OrderShippedEvent(String shipmentId, String orderId, String shipmentStatus) {
}
