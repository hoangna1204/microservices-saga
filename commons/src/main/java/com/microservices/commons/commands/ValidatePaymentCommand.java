package com.microservices.commons.commands;

import com.microservices.commons.models.CardDetails;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record ValidatePaymentCommand(@TargetAggregateIdentifier String paymentId, String orderId, CardDetails cardDetails) {
}
