package com.hexagonal.shop.adapter.out.persistent.http.cart;

import com.hexagonal.shop.model.cart.Cart;
import com.hexagonal.shop.model.cart.CartLineItem;
import com.hexagonal.shop.model.customer.CustomerId;
import com.hexagonal.shop.model.money.Money;
import com.hexagonal.shop.model.product.Product;
import com.hexagonal.shop.model.product.ProductId;
import java.util.Currency;

public final class CartHttpMapper {

  public static Cart toModelEntity(CartHttpDto dto) {
    CustomerId customerId = new CustomerId(dto.customerId());
    Cart cart = new Cart(customerId);

    for (CartLineItemHttpDto lineItemDto : dto.lineItems()) {
      CartLineItem lineItem = new CartLineItem(toModelEntity(lineItemDto.product()),
          lineItemDto.quantity());
      cart.putProductIgnoringNotEnoughItemsInStock(lineItem.product(), lineItem.quantity());
    }

    return cart;
  }

  private static Product toModelEntity(CartProductSnapshotDto dto) {
    return new Product(
        new ProductId(dto.id()),
        dto.name(),
        dto.description(),
        new Money(Currency.getInstance(dto.priceCurrency()), dto.priceAmount()),
        dto.itemsInStock()
    );
  }

  public static CartHttpDto toHttpDto(Cart cart) {
    return new CartHttpDto(
        cart.id().value(),
        cart.lineItems().stream()
            .map(li -> new CartLineItemHttpDto(
                cart.id().value(),
                toHttpDto(li.product()),
                li.quantity()
            ))
            .toList()
    );
  }

  private static CartProductSnapshotDto toHttpDto(Product product) {
    return new CartProductSnapshotDto(
        product.id().value(),
        product.name(),
        product.description(),
        product.price().currency().getCurrencyCode(),
        product.price().amount(),
        product.itemsInStock()
    );
  }
}

