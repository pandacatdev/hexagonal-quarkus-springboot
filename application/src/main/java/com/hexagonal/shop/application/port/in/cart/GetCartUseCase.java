package com.hexagonal.shop.application.port.in.cart;

import com.hexagonal.shop.model.cart.Cart;
import com.hexagonal.shop.model.customer.CustomerId;

public interface GetCartUseCase {
  Cart getCart(CustomerId customerId);
}
