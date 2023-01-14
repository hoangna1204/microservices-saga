package com.microservices.orderservice.aggregates;

import com.microservices.commons.commands.CancelOrderCommand;
import com.microservices.commons.commands.CompleteOrderCommand;
import com.microservices.commons.events.OrderCancelledEvent;
import com.microservices.commons.events.OrderCompletedEvent;
import com.microservices.orderservice.commands.CreateOrderCommand;
import com.microservices.orderservice.events.OrderCreatedEvent;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Log4j2
@Aggregate
public class OrderAggregate {
  @AggregateIdentifier
  private String orderId;
  private String productId;
  private String userId;
  private String addressId;
  private Integer quantity;
  private String orderStatus;

  public OrderAggregate() {
  }

  @CommandHandler
  public OrderAggregate(CreateOrderCommand command) {
    log.info("Validate createOrderCommand: {}", command);
    var orderCreatedEvent = new OrderCreatedEvent();
    BeanUtils.copyProperties(command, orderCreatedEvent);
    log.info("Create orderCreatedEvent: {}", command);
    AggregateLifecycle.apply(orderCreatedEvent);
  }

  @EventSourcingHandler
  public void on(OrderCreatedEvent event) {
    this.orderId = event.getOrderId();
    this.productId = event.getProductId();
    this.userId = event.getUserId();
    this.addressId = event.getAddressId();
    this.quantity = event.getQuantity();
    this.orderStatus = event.getOrderStatus();
    log.info("Order event cancelled");
  }

  @CommandHandler
  public void handle(CompleteOrderCommand command) {
    log.info("Validate completeOrderCommand: {}", command);
    var orderCompletedEvent = new OrderCompletedEvent(command.orderId(), command.orderStatus());
    log.info("Create orderCompletedEvent: {}", orderCompletedEvent);
    AggregateLifecycle.apply(orderCompletedEvent);
  }

  @EventSourcingHandler
  public void on(OrderCompletedEvent event) {
    this.orderStatus = event.orderStatus();
    log.info("Order event completed");
  }

  @CommandHandler
  public void handle(CancelOrderCommand command) {
    log.info("Validate cancelOrderCommand: {}", command);
    var orderCancelledEvent = new OrderCancelledEvent(command.orderId(), command.orderStatus());
    log.info("Create orderCancelledEvent: {}", orderCancelledEvent);
    AggregateLifecycle.apply(orderCancelledEvent);
  }

  @EventSourcingHandler
  public void on(OrderCancelledEvent event) {
    this.orderStatus = event.orderStatus();
    log.info("Order event cancelled");
  }
}
