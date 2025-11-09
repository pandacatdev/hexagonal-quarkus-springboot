package com.hexagonal.bootstrap.e2e;

import static com.hexagonal.shop.adapter.in.rest.product.ProductsControllerAssertions.assertThatResponseIsProductList;
import static com.hexagonal.shop.adapter.out.persistent.DemoProducts.COMPUTER_MONITOR;
import static com.hexagonal.shop.adapter.out.persistent.DemoProducts.MONITOR_DESK_MOUNT;
import static io.restassured.RestAssured.given;

import com.hexagonal.shop.bootstrap.Launcher;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = Launcher.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-with-mysql")
class FindProductsTest {

  @LocalServerPort
  private Integer port;

  @Test
  void givenTestProductsAndAQuery_findProducts_returnsMatchingProducts() {
    String query = "monitor";

    Response response =
        given().port(port).queryParam("query", query).get("/products").then().extract().response();

    assertThatResponseIsProductList(response, List.of(COMPUTER_MONITOR, MONITOR_DESK_MOUNT));
  }
}