package com.microservices.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  private String productId;
  private String userId;
  private String addressId;
  private Integer quantity;
}
