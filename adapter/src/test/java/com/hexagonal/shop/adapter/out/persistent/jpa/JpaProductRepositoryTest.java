package com.hexagonal.shop.adapter.out.persistent.jpa;

import com.hexagonal.shop.adapter.out.persistent.AbstractProductRepositoryTest;
import com.hexagonal.shop.adapter.out.persistent.jpa.product.JpaProductRepository;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class JpaProductRepositoryTest extends AbstractProductRepositoryTest<JpaProductRepository> {

  private static MySQLContainer<?> mysql;
  private static EntityManagerFactory entityManagerFactory;

  @BeforeAll
  static void startDatabase() {
    mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.1"));
    mysql.start();

    entityManagerFactory =
        EntityManagerFactoryFactory.createMySqlEntityManagerFactory(
            mysql.getJdbcUrl(), "root", "test");
  }

  @Override
  protected JpaProductRepository createProductRepository() {
    return new JpaProductRepository(entityManagerFactory);
  }

  @AfterAll
  static void stopDatabase() {
    entityManagerFactory.close();
    mysql.stop();
  }
}
