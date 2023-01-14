package com.microservices.commons.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CancelOrderCommand(@TargetAggregateIdentifier String orderId) {
  private static final String orderStatus = "CANCELLED";

  public String orderStatus() {
    return orderStatus;
  }
}
