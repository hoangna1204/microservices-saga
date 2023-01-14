package com.microservices.productservice.aggregates;

import com.microservices.productservice.commands.CreateProductCommand;
import com.microservices.productservice.data.Product;
import com.microservices.productservice.events.ProductCreatedEvent;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {
  @AggregateIdentifier
  private String productId;
  private String name;
  private BigDecimal price;
  private Integer quantity;

  public ProductAggregate() {
  }

  public ProductAggregate(CreateProductCommand command) {
    var productCreatedEvent = new ProductCreatedEvent();
    BeanUtils.copyProperties(command, productCreatedEvent);
    AggregateLifecycle.apply(productCreatedEvent);
  }

  @EventSourcingHandler
  public void on(ProductCreatedEvent event) {
    this.quantity = event.getQuantity();
    this.productId = event.getProductId();
    this.price = event.getPrice();
    this.name = event.getName();
  }


}
