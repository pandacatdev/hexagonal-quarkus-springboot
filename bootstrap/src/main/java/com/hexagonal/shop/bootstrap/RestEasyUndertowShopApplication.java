package com.hexagonal.shop.bootstrap;

import com.hexagonal.shop.adapter.in.rest.cart.AddToCartController;
import com.hexagonal.shop.adapter.in.rest.cart.EmptyCartController;
import com.hexagonal.shop.adapter.in.rest.cart.GetCartController;
import com.hexagonal.shop.adapter.in.rest.product.FindProductsController;
import com.hexagonal.shop.adapter.out.persistent.inmemory.InMemoryCartRepository;
import com.hexagonal.shop.adapter.out.persistent.inmemory.InMemoryProductRepository;
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
    cartRepository = new InMemoryCartRepository();
    productRepository = new InMemoryProductRepository();
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
