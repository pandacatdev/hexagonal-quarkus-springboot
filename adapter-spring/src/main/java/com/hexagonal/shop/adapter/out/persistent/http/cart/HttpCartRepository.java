package com.hexagonal.shop.adapter.out.persistent.http.cart;

import com.hexagonal.shop.application.port.out.persistence.CartRepository;
import com.hexagonal.shop.model.cart.Cart;
import com.hexagonal.shop.model.customer.CustomerId;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@ConditionalOnProperty(name = "persistence", havingValue = "http")
@Repository
public class HttpCartRepository implements CartRepository {

  private final WebClient webClient;

  public HttpCartRepository(
      WebClient.Builder builder,
      @Value("${cart-service.base-url}") String baseUrl) {
    this.webClient = builder.baseUrl(baseUrl).build();
  }

  @Override
  public Optional<Cart> findByCustomerId(CustomerId customerId) {
    return webClient.get()
        .uri("/carts/{id}", customerId.value())
        .retrieve()
        .bodyToMono(CartHttpDto.class)
        .map(CartHttpMapper::toModelEntity)
        .blockOptional();
  }

  @Override
  public void save(Cart cart) {
    webClient.post()
        .uri("/carts")
        .bodyValue(CartHttpMapper.toHttpDto(cart))
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void deleteByCustomerId(CustomerId customerId) {
    webClient.delete()
        .uri("/carts/{id}", customerId.value())
        .retrieve()
        .toBodilessEntity()
        .block();
  }
}

