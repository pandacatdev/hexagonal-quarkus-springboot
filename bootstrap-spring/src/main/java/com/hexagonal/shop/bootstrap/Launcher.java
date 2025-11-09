package com.hexagonal.shop.bootstrap;

import com.hexagonal.shop.SpringAppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.hexagonal.shop")
@Import(SpringAppConfig.class)
public class Launcher {

  public static void main(String[] args) {
    SpringApplication.run(Launcher.class, args);
  }
}
