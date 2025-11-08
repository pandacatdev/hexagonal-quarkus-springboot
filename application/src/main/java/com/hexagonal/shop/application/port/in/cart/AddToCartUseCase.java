package com.hexagonal.shop.application.port.in.cart;

import com.hexagonal.shop.model.cart.Cart;
import com.hexagonal.shop.model.cart.NotEnoughItemsInStockException;
import com.hexagonal.shop.model.customer.CustomerId;
import com.hexagonal.shop.model.product.ProductId;

public interface AddToCartUseCase {
  Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
      throws ProductNotFoundException, NotEnoughItemsInStockException;
}
