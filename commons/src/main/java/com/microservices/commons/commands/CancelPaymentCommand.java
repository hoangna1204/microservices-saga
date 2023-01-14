package com.microservices.commons.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CancelPaymentCommand(@TargetAggregateIdentifier String paymentId, String orderId) {
  private static final String paymentStatus = "CANCELLED";

  public String paymentStatus() {
    return paymentStatus;
  }
}
