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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
    "com.hexagonal.shop"
})
public class SpringAppConfig {

  @Autowired
  CartRepository cartRepository;

  @Autowired ProductRepository productRepository;

  @Bean
  GetCartUseCase getCartUseCase() {
    return new GetCartService(cartRepository);
  }

  @Bean
  EmptyCartUseCase emptyCartUseCase() {
    return new EmptyCartService(cartRepository);
  }

  @Bean
  FindProductsUseCase findProductsUseCase() {
    return new FindProductsService(productRepository);
  }

  @Bean
  AddToCartUseCase addToCartUseCase() {
    return new AddToCartService(cartRepository, productRepository);
  }
}
