package com.hexagonal.shop.application.service.cart;

import com.hexagonal.shop.model.cart.Cart;
import com.hexagonal.shop.model.cart.NotEnoughItemsInStockException;
import com.hexagonal.shop.model.customer.CustomerId;
import com.hexagonal.shop.model.product.Product;
import com.hexagonal.shop.model.product.ProductId;
import com.hexagonal.shop.application.port.in.cart.AddToCartUseCase;
import com.hexagonal.shop.application.port.in.cart.ProductNotFoundException;
import com.hexagonal.shop.application.port.out.persistence.CartRepository;
import com.hexagonal.shop.application.port.out.persistence.ProductRepository;
import java.util.Objects;

public class AddToCartService implements AddToCartUseCase {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;

  public AddToCartService(
      CartRepository cartRepository, ProductRepository productRepositoryVeryVeryLong) {
    this.cartRepository = cartRepository;
    this.productRepository = productRepositoryVeryVeryLong;
  }

  @Override
  public Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
      throws ProductNotFoundException, NotEnoughItemsInStockException {
    Objects.requireNonNull(customerId, "'customerId' must not be null");
    Objects.requireNonNull(productId, "'productId' must not be null");
    if (quantity < 1) {
      throw new IllegalArgumentException("'quantity' must be greater than 0");
    }

    Product product =
        productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

    Cart cart =
        cartRepository
            .findByCustomerId(customerId)
            .orElseGet(() -> new Cart(customerId));

    cart.addProduct(product, quantity);

    cartRepository.save(cart);

    return cart;
  }
}
