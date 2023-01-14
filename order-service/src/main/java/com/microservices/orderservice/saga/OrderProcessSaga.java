package com.microservices.orderservice.saga;

import com.microservices.commons.commands.*;
import com.microservices.commons.events.*;
import com.microservices.commons.models.User;
import com.microservices.commons.queries.GetUserPaymentQueries;
import com.microservices.orderservice.events.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Log4j2
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderProcessSaga {
  private transient CommandGateway commandGateway;
  private transient QueryGateway queryGateway;

  @StartSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void handle(OrderCreatedEvent event) {
    log.info("OrderCreatedEvent in Saga for order id: {}", event.getOrderId());
    var getUserPaymentQuery = new GetUserPaymentQueries(event.getUserId());
    User user = null;
    try {
      user = queryGateway.query(getUserPaymentQuery, ResponseTypes.instanceOf(User.class)).join();
    } catch (Exception e) {
      log.error(e.getMessage());
      cancelOrderCommand(event.getOrderId());
    }

    var validatePaymentCommand = new ValidatePaymentCommand(
        UUID.randomUUID().toString(),
        event.getOrderId(),
        user.getCardDetails()
    );
    commandGateway.sendAndWait(validatePaymentCommand);
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void handle(PaymentProcessedEvent event) {
    log.info("PaymentProcessedEvent in Saga for order id: {}", event.orderId());
    try {
      var shipOrderCommand = new ShipOrderCommand(UUID.randomUUID().toString(), event.orderId());
      commandGateway.send(shipOrderCommand);
    } catch (Exception e) {
      log.error(e.getMessage());
      cancelPaymentCommand(event);
    }
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void handle(OrderShippedEvent event) {
    log.info("OrderShippedEvent in Saga for order id: {}", event.orderId());
    var completeOrderCommand = new CompleteOrderCommand(event.orderId(), "APPROVED");
    commandGateway.send(completeOrderCommand);
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void handle(OrderCompletedEvent event) {
    log.info("OrderCompletedEvent in Saga for order id: {}", event.orderId());
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void handle(OrderCancelledEvent event) {
    log.info("OrderCancelledEvent in Saga for order id: {}", event.orderId());
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void handle(PaymentCancelledEvent event) {
    log.info("PaymentCancelledEvent in Saga for order id: {}", event.orderId());
    cancelOrderCommand(event.orderId());
  }

  private void cancelOrderCommand(String orderId) {
    var cancelOrderCommand = new CancelOrderCommand(orderId);
    commandGateway.send(cancelOrderCommand);
  }

  private void cancelPaymentCommand(PaymentProcessedEvent event) {
    var cancelPaymentCommand = new CancelPaymentCommand(event.paymentId(), event.orderId());
    commandGateway.send(cancelPaymentCommand);
  }
}
