package com.microservices.orderservice.events;

import com.microservices.orderservice.data.Order;
import com.microservices.orderservice.data.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderEventsHandler {
  private final OrderRepository orderRepository;

  @EventHandler
  public void on(OrderCreatedEvent event) {
    var order = new Order();
    BeanUtils.copyProperties(event, order);
    orderRepository.save(order);
  }
}
