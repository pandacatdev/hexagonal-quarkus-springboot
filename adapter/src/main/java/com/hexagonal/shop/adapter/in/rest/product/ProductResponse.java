package com.hexagonal.shop.adapter.in.rest.product;

import com.hexagonal.shop.model.money.Money;
import com.hexagonal.shop.model.product.Product;

public record ProductResponse(String id, String name, Money price, int itemsInStock) {

  public static ProductResponse fromDomainModel(Product product) {
    return new ProductResponse(
        product.id().value(), product.name(), product.price(), product.itemsInStock());
  }
}
