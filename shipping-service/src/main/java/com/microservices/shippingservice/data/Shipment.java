package com.microservices.shippingservice.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "shipments")
public class Shipment {
  @Id
  private String shipmentId;
  private String orderId;
  private String shipmentStatus;
}
