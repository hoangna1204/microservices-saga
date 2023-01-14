package com.microservices.orderservice.controllers;

import com.microservices.orderservice.commands.CreateOrderCommand;
import com.microservices.orderservice.models.Order;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderCommandController {
  private final CommandGateway commandGateway;

  @PostMapping
  public String createOrder(@RequestBody Order order) {
    var orderId = UUID.randomUUID().toString();
    var createOrderCommand = CreateOrderCommand.builder()
        .orderId(orderId)
        .addressId(order.getAddressId())
        .productId(order.getProductId())
        .quantity(order.getQuantity())
        .orderStatus("CREATED")
        .build();
    commandGateway.sendAndWait(createOrderCommand);
    return orderId;
  }
}
