package com.hexagonal.shop.adapter.in.rest.common;

import static com.hexagonal.shop.adapter.in.rest.common.RestUtils.clientErrorException;

import com.hexagonal.shop.model.product.ProductId;
import org.springframework.http.HttpStatus;

public final class ProductIdParser {

  private ProductIdParser() {}

  public static ProductId parseProductId(String string) {
    if (string == null) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Missing 'productId'");
    }

    try {
      return new ProductId(string);
    } catch (IllegalArgumentException e) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Invalid 'productId'");
    }
  }
}
