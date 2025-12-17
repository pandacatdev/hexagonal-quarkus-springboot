package com.hexagonal.shop.adapter.out.persistent.http.product;

import java.math.BigDecimal;

public record ProductHttpDto(
    String id,
    String name,
    String description,
    String priceCurrency,
    BigDecimal priceAmount,
    int itemsInStock
) {}
