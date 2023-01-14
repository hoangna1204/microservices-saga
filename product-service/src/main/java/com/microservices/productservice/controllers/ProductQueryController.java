package com.microservices.productservice.controllers;

import com.microservices.productservice.models.Product;
import com.microservices.productservice.queries.GetProductsQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductQueryController {
  private final QueryGateway queryGateway;

  @GetMapping
  public List<Product> getAllProducts() {
    var getProductsQuery = new GetProductsQuery();
    return queryGateway.query(getProductsQuery, ResponseTypes.multipleInstancesOf(Product.class)).join();
  }
}
