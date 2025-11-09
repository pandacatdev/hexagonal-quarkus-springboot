package com.hexagonal.shop.adapter.out.persistent.jpa.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartSpringDataRepository extends JpaRepository<CartJpaEntity, Integer> {}
