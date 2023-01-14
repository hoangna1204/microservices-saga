package com.microservices.productservice.projections;

import com.microservices.productservice.data.ProductRepository;
import com.microservices.productservice.models.Product;
import com.microservices.productservice.queries.GetProductsQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class ProductProjection {
  private final ProductRepository productRepository;


  @QueryHandler
  public List<Product> handle(GetProductsQuery query) {
    var productData = productRepository.findAll();
    return productData.stream()
        .map(product -> new Product(product.getName(), product.getPrice(), product.getQuantity()))
        .collect(Collectors.toList());
  }
}
