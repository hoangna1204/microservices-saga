package com.microservices.shippingservice.events;

import com.microservices.commons.events.OrderShippedEvent;
import com.microservices.shippingservice.data.Shipment;
import com.microservices.shippingservice.data.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShipmentEventHandler {
  private final ShipmentRepository shipmentRepository;

  @EventHandler
  public void on(OrderShippedEvent event) {
    var shipment = new Shipment();
    BeanUtils.copyProperties(event, shipment);
    shipmentRepository.save(shipment);
  }

}
