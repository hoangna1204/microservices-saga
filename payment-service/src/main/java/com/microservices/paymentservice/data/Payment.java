package com.microservices.paymentservice.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "payments")
public class Payment {
  @Id
  private String paymentId;
  private String orderId;
  private Date timeStamp;
  private String paymentStatus;
}
