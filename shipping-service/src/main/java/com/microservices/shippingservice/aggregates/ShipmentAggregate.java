package com.microservices.shippingservice.aggregates;

import com.microservices.commons.commands.ShipOrderCommand;
import com.microservices.commons.events.OrderShippedEvent;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ShipmentAggregate {
  @AggregateIdentifier
  private String shipmentId;
  private String orderId;
  private String shipmentStatus;

  public ShipmentAggregate() {
  }

  public ShipmentAggregate(ShipOrderCommand command) {
    var orderShippedEvent = new OrderShippedEvent(command.shipmendId(), command.orderId(), "COMPLETED");
    AggregateLifecycle.apply(orderShippedEvent);
  }

  @EventSourcingHandler
  public void on(OrderShippedEvent event) {
    this.orderId = event.orderId();
    this.shipmentId = event.shipmentId();
    this.shipmentStatus = event.shipmentStatus();
  }
}
