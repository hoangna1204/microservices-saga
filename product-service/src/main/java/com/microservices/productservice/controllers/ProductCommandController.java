package com.microservices.productservice.controllers;

import com.microservices.productservice.commands.CreateProductCommand;
import com.microservices.productservice.models.Product;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductCommandController {
  private final CommandGateway commandGateway;

  @PostMapping
  public String addProduct(@RequestBody Product product) {
    var createProductCommand = CreateProductCommand.builder()
        .productId(UUID.randomUUID().toString())
        .name(product.name())
        .price(product.price())
        .quantity(product.quantity())
        .build();
    return commandGateway.sendAndWait(createProductCommand);
  }
}
