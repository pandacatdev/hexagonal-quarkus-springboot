package com.hexagonal.shop.adapter.out.persistent.jpa.product;

import com.hexagonal.shop.model.money.Money;
import com.hexagonal.shop.model.product.Product;
import com.hexagonal.shop.model.product.ProductId;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public final class ProductMapper {

  private ProductMapper() {}

  public static ProductJpaEntity toJpaEntity(Product product) {
    ProductJpaEntity jpaEntity = new ProductJpaEntity();

    jpaEntity.setId(product.id().value());
    jpaEntity.setName(product.name());
    jpaEntity.setDescription(product.description());
    jpaEntity.setPriceCurrency(product.price().currency().getCurrencyCode());
    jpaEntity.setPriceAmount(product.price().amount());
    jpaEntity.setItemsInStock(product.itemsInStock());

    return jpaEntity;
  }

  static Optional<Product> toModelEntityOptional(ProductJpaEntity jpaEntity) {
    return Optional.ofNullable(jpaEntity).map(ProductMapper::toModelEntity);
  }

  public static Product toModelEntity(ProductJpaEntity jpaEntity) {
    return new Product(
        new ProductId(jpaEntity.getId()),
        jpaEntity.getName(),
        jpaEntity.getDescription(),
        new Money(Currency.getInstance(jpaEntity.getPriceCurrency()), jpaEntity.getPriceAmount()),
        jpaEntity.getItemsInStock());
  }

  public static List<Product> toModelEntities(List<ProductJpaEntity> jpaEntities) {
    return jpaEntities.stream().map(ProductMapper::toModelEntity).toList();
  }
}
