package com.hexagonal.shop.adapter.out.persistent.jpa.cart;

import com.hexagonal.shop.adapter.out.persistent.jpa.product.ProductJpaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CartLineItem")
@Getter
@Setter
public class CartLineItemJpaEntity {
  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  private CartJpaEntity cart;

  @ManyToOne
  private ProductJpaEntity product;

  private int quantity;
}
