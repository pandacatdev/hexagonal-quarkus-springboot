package com.hexagonal.shop.bootstrap;

import com.hexagonal.shop.adapter.in.rest.cart.AddToCartController;
import com.hexagonal.shop.adapter.in.rest.cart.EmptyCartController;
import com.hexagonal.shop.adapter.in.rest.cart.GetCartController;
import com.hexagonal.shop.adapter.in.rest.product.FindProductsController;
import com.hexagonal.shop.adapter.out.persistent.inmemory.InMemoryCartRepository;
import com.hexagonal.shop.adapter.out.persistent.inmemory.InMemoryProductRepository;
import com.hexagonal.shop.adapter.out.persistent.jpa.EntityManagerFactoryFactory;
import com.hexagonal.shop.adapter.out.persistent.jpa.cart.JpaCartRepository;
import com.hexagonal.shop.adapter.out.persistent.jpa.product.JpaProductRepository;
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
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.core.Application;
import java.util.Set;

/**
 * The application configuration for the Undertow server. Instantiates the adapters and use cases,
 * and wires them.
 *
 */
public class RestEasyUndertowShopApplication extends Application {

  private CartRepository cartRepository;
  private ProductRepository productRepository;

  // We're encouraged to use "automatic discovery of resources", but I want to define them manually.
  @SuppressWarnings("deprecation")
  @Override
  public Set<Object> getSingletons() {
    initPersistenceAdapters();
    return Set.of(
        addToCartController(),
        getCartController(),
        emptyCartController(),
        findProductsController());
  }

  private void initPersistenceAdapters() {
    // Get persistence property, default to inmemory
    String persistence = System.getProperty("persistence", "inmemory");

    switch (persistence) {
      case "inmemory" -> initInMemoryAdapters();
      case "mysql" -> initMySqlAdapters();
      default -> throw new IllegalArgumentException(
          "Invalid 'persistence' property: '%s' (allowed: 'inmemory', 'mysql')"
              .formatted(persistence));
    }
  }

  private void initInMemoryAdapters() {
    cartRepository = new InMemoryCartRepository();
    productRepository = new InMemoryProductRepository();
  }

  // The EntityManagerFactory doesn't need to get closed before the application is stopped
  @SuppressWarnings("PMD.CloseResource")
  private void initMySqlAdapters() {
    EntityManagerFactory entityManagerFactory =
        EntityManagerFactoryFactory.createMySqlEntityManagerFactory(
            "jdbc:mysql://localhost:3306/shop", "root", "test");

    cartRepository = new JpaCartRepository(entityManagerFactory);
    productRepository = new JpaProductRepository(entityManagerFactory);
  }

  private AddToCartController addToCartController() {
    AddToCartUseCase addToCartUseCase = new AddToCartService(cartRepository, productRepository);
    return new AddToCartController(addToCartUseCase);
  }

  private GetCartController getCartController() {
    GetCartUseCase getCartUseCase = new GetCartService(cartRepository);
    return new GetCartController(getCartUseCase);
  }

  private EmptyCartController emptyCartController() {
    EmptyCartUseCase emptyCartUseCase = new EmptyCartService(cartRepository);
    return new EmptyCartController(emptyCartUseCase);
  }

  private FindProductsController findProductsController() {
    FindProductsUseCase findProductsUseCase = new FindProductsService(productRepository);
    return new FindProductsController(findProductsUseCase);
  }
}
