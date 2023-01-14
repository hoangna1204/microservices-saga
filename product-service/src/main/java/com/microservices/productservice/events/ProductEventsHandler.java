package com.microservices.productservice.events;

import com.microservices.productservice.data.Product;
import com.microservices.productservice.data.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductEventsHandler {
  private final ProductRepository productRepository;

  @EventHandler
  public void on(ProductCreatedEvent event) {
    var product = new Product();
    BeanUtils.copyProperties(event, product);
    productRepository.save(product);
  }

  @ExceptionHandler
  public void handle(Exception e) throws Exception {
    throw e;
  }
}
