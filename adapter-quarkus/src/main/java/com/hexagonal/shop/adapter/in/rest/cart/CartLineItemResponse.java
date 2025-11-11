package com.hexagonal.shop.adapter.in.rest.cart;

import com.hexagonal.shop.model.cart.CartLineItem;
import com.hexagonal.shop.model.money.Money;
import com.hexagonal.shop.model.product.Product;

public record CartLineItemResponse(
    String productId, String productName, Money price, int quantity) {

  public static CartLineItemResponse fromDomainModel(CartLineItem lineItem) {
    Product product = lineItem.product();
    return new CartLineItemResponse(
        product.id().value(), product.name(), product.price(), lineItem.quantity());
  }
}
