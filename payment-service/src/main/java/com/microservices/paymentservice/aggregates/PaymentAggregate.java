package com.microservices.paymentservice.aggregates;

import com.microservices.commons.commands.CancelPaymentCommand;
import com.microservices.commons.commands.ValidatePaymentCommand;
import com.microservices.commons.events.PaymentCancelledEvent;
import com.microservices.commons.events.PaymentProcessedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Log4j2
@Aggregate
@NoArgsConstructor
public class PaymentAggregate {
  @AggregateIdentifier
  private String paymentId;
  private String orderId;
  private String paymentStatus;

  public PaymentAggregate(ValidatePaymentCommand command) {
    log.info("Executing ValidatePaymentCommand for order id: {} and payment id: {}",
        command.orderId(), command.paymentId());
    var paymentProcessedEvent = new PaymentProcessedEvent(
        command.paymentId(), command.orderId()
    );
    AggregateLifecycle.apply(paymentProcessedEvent);
    log.info("PaymentProcessedEvent applied");
  }

  @EventSourcingHandler
  public void on(PaymentProcessedEvent event) {
    this.paymentId = event.paymentId();
    this.orderId = event.orderId();
  }

  @CommandHandler
  public void handle(CancelPaymentCommand command) {
    var paymentCancelledEvent = new PaymentCancelledEvent(
        command.paymentId(), command.orderId(), command.paymentStatus()
    );
    AggregateLifecycle.apply(paymentCancelledEvent);
  }

  @EventSourcingHandler
  public void on(PaymentCancelledEvent event) {
    this.paymentStatus = event.paymentStatus();
  }
}
