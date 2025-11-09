package com.hexagonal.shop.adapter.in.rest.cart;

import static com.hexagonal.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;
import static com.hexagonal.shop.adapter.in.rest.common.ProductIdParser.parseProductId;
import static com.hexagonal.shop.adapter.in.rest.common.RestUtils.clientErrorException;

import com.hexagonal.shop.application.port.in.cart.AddToCartUseCase;
import com.hexagonal.shop.application.port.in.cart.ProductNotFoundException;
import com.hexagonal.shop.model.cart.Cart;
import com.hexagonal.shop.model.cart.NotEnoughItemsInStockException;
import com.hexagonal.shop.model.customer.CustomerId;
import com.hexagonal.shop.model.product.ProductId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class AddToCartController {

  private final AddToCartUseCase addToCartUseCase;

  public AddToCartController(AddToCartUseCase addToCartUseCase) {
    this.addToCartUseCase = addToCartUseCase;
  }

  @PostMapping("/{customerId}/line-items")
  public CartResponse addLineItem(
      @PathVariable("customerId") String customerIdString,
      @RequestParam("productId") String productIdString,
      @RequestParam("quantity") int quantity) {
    CustomerId customerId = parseCustomerId(customerIdString);
    ProductId productId = parseProductId(productIdString);

    try {
      Cart cart = addToCartUseCase.addToCart(customerId, productId, quantity);
      return CartResponse.fromDomainModel(cart);
    } catch (ProductNotFoundException e) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "The requested product does not exist");
    } catch (NotEnoughItemsInStockException e) {
      throw clientErrorException(
          HttpStatus.BAD_REQUEST, "Only %d items in stock".formatted(e.itemsInStock()));
    }
  }
}
