package com.hexagonal.shop.adapter.out.persistent.jpa.product;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JpaProductPanacheRepository
    implements PanacheRepositoryBase<ProductJpaEntity, String> {}
