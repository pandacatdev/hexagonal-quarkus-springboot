package com.hexagonal.shop.adapter.in.rest.cart;

import static com.hexagonal.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

import com.hexagonal.shop.application.port.in.cart.EmptyCartUseCase;
import com.hexagonal.shop.model.customer.CustomerId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class EmptyCartController {

  private final EmptyCartUseCase emptyCartUseCase;

  public EmptyCartController(EmptyCartUseCase emptyCartUseCase) {
    this.emptyCartUseCase = emptyCartUseCase;
  }

  @DeleteMapping("/{customerId}")
  public ResponseEntity<Void> deleteCart(@PathVariable("customerId") String customerIdString) {
    CustomerId customerId = parseCustomerId(customerIdString);
    emptyCartUseCase.emptyCart(customerId);
    return ResponseEntity.noContent().build();
  }
}
