package com.hexagonal.shop.adapter.out.persistent.http.cart;

public record CartLineItemHttpDto(
    Integer cartId,
    CartProductSnapshotDto product,
    int quantity
) {}
