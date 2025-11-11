package com.hexagonal.shop.adapter.in.rest.common;

import static com.hexagonal.shop.adapter.in.rest.common.RestUtils.clientErrorException;

import com.hexagonal.shop.model.customer.CustomerId;
import jakarta.ws.rs.core.Response;

public final class CustomerIdParser {

  private CustomerIdParser() {}

  public static CustomerId parseCustomerId(String string) {
    try {
      return new CustomerId(Integer.parseInt(string));
    } catch (IllegalArgumentException e) {
      throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid 'customerId'");
    }
  }
}
