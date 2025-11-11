package com.hexagonal.shop.adapter.in.rest.cart;

import com.hexagonal.shop.model.cart.Cart;
import com.hexagonal.shop.model.money.Money;
import java.util.List;

public record CartResponse(
    List<CartLineItemResponse> lineItems, int numberOfItems, Money subTotal) {

  static CartResponse fromDomainModel(Cart cart) {
    return new CartResponse(
        cart.lineItems().stream().map(CartLineItemResponse::fromDomainModel).toList(),
        cart.numberOfItems(),
        cart.subTotal());
  }
}
