package com.hexagonal.shop.adapter.out.persistent.jpa.cart;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JpaCartPanacheRepository implements PanacheRepositoryBase<CartJpaEntity, Integer> {}
