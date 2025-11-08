package com.hexagonal.shop.application.port.in.cart;

import com.hexagonal.shop.model.customer.CustomerId;

public interface EmptyCartUseCase {

  void emptyCart(CustomerId customerId);
}
