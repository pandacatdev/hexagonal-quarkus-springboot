package com.hexagonal.shop.adapter.out.persistent;

import static org.assertj.core.api.Assertions.assertThat;

import com.hexagonal.shop.model.product.Product;
import com.hexagonal.shop.model.product.ProductId;
import com.hexagonal.shop.application.port.out.persistence.ProductRepository;
import jakarta.enterprise.inject.Instance;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;

public abstract class AbstractProductRepositoryTest {

  @Inject
  Instance<ProductRepository> productRepositoryInstance;

  private ProductRepository productRepository;

  @BeforeEach
  void initRepository() {
    productRepository = productRepositoryInstance.get();
  }

  @Test
  void givenTestProductsAndATestProductId_findById_returnsATestProduct() {
    ProductId productId = DemoProducts.COMPUTER_MONITOR.id();

    Optional<Product> product = productRepository.findById(productId);

    assertThat(product).contains(DemoProducts.COMPUTER_MONITOR);
  }

  @Test
  void givenTheIdOfAProductNotPersisted_findById_returnsAnEmptyOptional() {
    ProductId productId = new ProductId("00000");

    Optional<Product> product = productRepository.findById(productId);

    assertThat(product).isEmpty();
  }

  @Test
  void
  givenTestProductsAndASearchQueryNotMatchingAndProduct_findByNameOrDescription_returnsAnEmptyList() {
    String query = "not matching any product";

    List<Product> products = productRepository.findByNameOrDescription(query);

    assertThat(products).isEmpty();
  }

  @Test
  void
  givenTestProductsAndASearchQueryMatchingOneProduct_findByNameOrDescription_returnsThatProduct() {
    String query = "lights";

    List<Product> products = productRepository.findByNameOrDescription(query);

    assertThat(products).containsExactlyInAnyOrder(DemoProducts.LED_LIGHTS);
  }

  @Test
  void
  givenTestProductsAndASearchQueryMatchingTwoProducts_findByNameOrDescription_returnsThoseProducts() {
    String query = "monitor";

    List<Product> products = productRepository.findByNameOrDescription(query);

    assertThat(products)
        .containsExactlyInAnyOrder(DemoProducts.COMPUTER_MONITOR, DemoProducts.MONITOR_DESK_MOUNT);
  }
}
