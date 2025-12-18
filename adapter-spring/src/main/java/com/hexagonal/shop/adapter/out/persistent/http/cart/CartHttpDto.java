package com.hexagonal.shop.adapter.out.persistent.http.cart;

import java.util.List;

public record CartHttpDto(
    int customerId,
    List<CartLineItemHttpDto> lineItems
) {}