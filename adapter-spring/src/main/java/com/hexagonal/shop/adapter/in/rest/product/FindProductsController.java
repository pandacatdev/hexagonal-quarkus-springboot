package com.hexagonal.shop.adapter.in.rest.product;

import static com.hexagonal.shop.adapter.in.rest.common.RestUtils.clientErrorException;

import com.hexagonal.shop.application.port.in.product.FindProductsUseCase;
import com.hexagonal.shop.model.product.Product;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class FindProductsController {

  private final FindProductsUseCase findProductsUseCase;

  public FindProductsController(FindProductsUseCase findProductsUseCase) {
    this.findProductsUseCase = findProductsUseCase;
  }

  @GetMapping
  public List<ProductResponse> findProducts(
      @RequestParam(value = "query", required = false) String query) {
    System.out.println("[FindProductsController] findProducts: " + query);
    if (query == null) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Missing 'query'");
    }

    List<Product> products;

    try {
      products = findProductsUseCase.findByNameOrDescription(query);
    } catch (IllegalArgumentException e) {
      throw clientErrorException(HttpStatus.BAD_REQUEST, "Invalid 'query'");
    }

    return products.stream().map(ProductResponse::fromDomainModel).toList();
  }
}
