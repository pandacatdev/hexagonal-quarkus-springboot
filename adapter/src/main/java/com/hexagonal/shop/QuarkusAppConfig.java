package com.hexagonal.shop;

import com.hexagonal.shop.application.port.in.cart.AddToCartUseCase;
import com.hexagonal.shop.application.port.in.cart.EmptyCartUseCase;
import com.hexagonal.shop.application.port.in.cart.GetCartUseCase;
import com.hexagonal.shop.application.port.in.product.FindProductsUseCase;
import com.hexagonal.shop.application.port.out.persistence.CartRepository;
import com.hexagonal.shop.application.port.out.persistence.ProductRepository;
import com.hexagonal.shop.application.service.cart.AddToCartService;
import com.hexagonal.shop.application.service.cart.EmptyCartService;
import com.hexagonal.shop.application.service.cart.GetCartService;
import com.hexagonal.shop.application.service.product.FindProductsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

public class QuarkusAppConfig {

  @Inject
  Instance<CartRepository> cartRepository;

  @Inject
  Instance<ProductRepository> productRepository;

  @Produces
  @ApplicationScoped
  GetCartUseCase getCartUseCase() {
    return new GetCartService(cartRepository.get());
  }

  @Produces
  @ApplicationScoped
  EmptyCartUseCase emptyCartUseCase() {
    return new EmptyCartService(cartRepository.get());
  }

  @Produces
  @ApplicationScoped
  FindProductsUseCase findProductsUseCase() {
    return new FindProductsService(productRepository.get());
  }

  @Produces
  @ApplicationScoped
  AddToCartUseCase addToCartUseCase() {
    return new AddToCartService(cartRepository.get(), productRepository.get());
  }

}
