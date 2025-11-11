package com.hexagonal.shop.adapter.in.rest.cart;

import static com.hexagonal.shop.adapter.in.rest.common.CustomerIdParser.parseCustomerId;
import static com.hexagonal.shop.adapter.in.rest.common.ProductIdParser.parseProductId;
import static com.hexagonal.shop.adapter.in.rest.common.RestUtils.clientErrorException;

import com.hexagonal.shop.model.cart.Cart;
import com.hexagonal.shop.model.cart.NotEnoughItemsInStockException;
import com.hexagonal.shop.model.customer.CustomerId;
import com.hexagonal.shop.model.product.ProductId;
import com.hexagonal.shop.application.port.in.cart.AddToCartUseCase;
import com.hexagonal.shop.application.port.in.cart.ProductNotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class AddToCartController {

  private final AddToCartUseCase addToCartUseCase;

  public AddToCartController(AddToCartUseCase addToCartUseCase) {
    this.addToCartUseCase = addToCartUseCase;
  }

  @POST
  @Path("/{customerId}/line-items")
  public CartResponse addLineItem(
      @PathParam("customerId") String customerIdString,
      @QueryParam("productId") String productIdString,
      @QueryParam("quantity") int quantity) {
    CustomerId customerId = parseCustomerId(customerIdString);
    ProductId productId = parseProductId(productIdString);

    try {
      Cart cart = addToCartUseCase.addToCart(customerId, productId, quantity);
      return CartResponse.fromDomainModel(cart);
    } catch (ProductNotFoundException e) {
      throw clientErrorException(
          Response.Status.BAD_REQUEST, "The requested product does not exist");
    } catch (NotEnoughItemsInStockException e) {
      throw clientErrorException(
          Response.Status.BAD_REQUEST, "Only %d items in stock".formatted(e.itemsInStock()));
    }
  }
}
