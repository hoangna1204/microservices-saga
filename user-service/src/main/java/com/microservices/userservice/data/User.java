package com.microservices.userservice.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "tables")
public class User {
  @Id
  private String userId;
  private String email;
  private String password;
  private String firstName;
  private String lastName;

  public User() {
    this.userId = UUID.randomUUID().toString();
  }
}
