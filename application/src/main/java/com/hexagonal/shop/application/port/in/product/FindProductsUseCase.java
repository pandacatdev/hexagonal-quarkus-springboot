package com.hexagonal.shop.application.port.in.product;

import com.hexagonal.shop.model.product.Product;
import java.util.List;

public interface FindProductsUseCase {
  List<Product> findByNameOrDescription(String query);
}
