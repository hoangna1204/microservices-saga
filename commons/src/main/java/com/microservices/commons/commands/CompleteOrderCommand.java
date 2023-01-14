package com.microservices.commons.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CompleteOrderCommand(@TargetAggregateIdentifier String orderId, String orderStatus) {
}
