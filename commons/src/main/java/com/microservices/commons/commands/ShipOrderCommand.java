package com.microservices.commons.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record ShipOrderCommand(@TargetAggregateIdentifier String shipmendId, String orderId) {
}
