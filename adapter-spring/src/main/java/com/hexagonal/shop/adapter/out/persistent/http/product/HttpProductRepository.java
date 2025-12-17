package com.hexagonal.shop.adapter.out.persistent.http.product;

import com.hexagonal.shop.application.port.out.persistence.ProductRepository;
import com.hexagonal.shop.model.product.Product;
import com.hexagonal.shop.model.product.ProductId;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ConditionalOnProperty(name = "persistence", havingValue = "http")
@Repository
public class HttpProductRepository implements ProductRepository {

  private final WebClient webClient;

  public HttpProductRepository(
      WebClient.Builder builder,
      @Value("${product-service.base-url}") String baseUrl) {
    this.webClient = builder.baseUrl(baseUrl).build();
  }

  @Override
  public void save(Product product) {
    ProductHttpDto dto = ProductHttpMapper.toHttpDto(product);

    webClient.post()
        .uri("/products")
        .bodyValue(dto)
        .retrieve()
        .toBodilessEntity()
        .block(); // for blocking repository API; in reactive app, return Mono<Void>
  }

  @Override
  public Optional<Product> findById(ProductId productId) {
    return webClient.get()
        .uri("/products/{id}", productId.value())
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response -> {
          if (response.statusCode().value() == 404) {
            return Mono.empty();
          }
          return response.createException().flatMap(Mono::error);
        })
        .bodyToMono(ProductHttpDto.class)
        .map(ProductHttpMapper::toModelEntity)
        .map(Optional::of)
        .defaultIfEmpty(Optional.empty())
        .block();
  }

  @Override
  public List<Product> findByNameOrDescription(String query) {
    List<ProductHttpDto> dtos = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/products")
            .queryParam("q", query)
            .build())
        .retrieve()
        .bodyToFlux(ProductHttpDto.class)
        .collectList()
        .block();

    return dtos.stream()
        .map(ProductHttpMapper::toModelEntity)
        .toList();
  }
}
