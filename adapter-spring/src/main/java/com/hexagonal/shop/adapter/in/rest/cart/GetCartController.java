package com.hexagonal.shop.adapter.in.rest.cart;

import static com.hexagonal.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

import com.hexagonal.shop.application.port.in.cart.GetCartUseCase;
import com.hexagonal.shop.model.cart.Cart;
import com.hexagonal.shop.model.customer.CustomerId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class GetCartController {

  private final GetCartUseCase getCartUseCase;

  public GetCartController(GetCartUseCase getCartUseCase) {
    this.getCartUseCase = getCartUseCase;
  }

  @GetMapping("/{customerId}")
  public CartResponse getCart(@PathVariable("customerId") String customerIdString) {
    CustomerId customerId = parseCustomerId(customerIdString);
    Cart cart = getCartUseCase.getCart(customerId);
    return CartResponse.fromDomainModel(cart);
  }
}
