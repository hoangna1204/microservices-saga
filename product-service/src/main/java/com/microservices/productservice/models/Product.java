package com.microservices.productservice.models;

import java.math.BigDecimal;

public record Product(String name, BigDecimal price, Integer quantity) {
}
