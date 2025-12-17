package com.hexagonal.shop.adapter.out.persistent.http.product;

import com.hexagonal.shop.model.money.Money;
import com.hexagonal.shop.model.product.Product;
import com.hexagonal.shop.model.product.ProductId;
import java.util.Currency;

public class ProductHttpMapper {
  private ProductHttpMapper() {}

  public static Product toModelEntity(ProductHttpDto dto) {
    return new Product(
        new ProductId(dto.id()),
        dto.name(),
        dto.description(),
        new Money(
            Currency.getInstance(dto.priceCurrency()),
            dto.priceAmount()
        ),
        dto.itemsInStock()
    );
  }

  public static ProductHttpDto toHttpDto(Product product) {
    return new ProductHttpDto(
        product.id().value(),
        product.name(),
        product.description(),
        product.price().currency().getCurrencyCode(),
        product.price().amount(),
        product.itemsInStock()
    );
  }
}
