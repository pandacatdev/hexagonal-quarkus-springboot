package com.hexagonal.shop.adapter.out.persistent.http.cart;

import java.math.BigDecimal;

public record CartProductSnapshotDto(
    String id,
    String name,
    String description,
    String priceCurrency,
    BigDecimal priceAmount,
    int itemsInStock
) {}
