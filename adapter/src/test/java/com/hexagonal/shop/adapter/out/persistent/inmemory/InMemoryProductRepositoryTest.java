package com.hexagonal.shop.adapter.out.persistent.inmemory;

import com.hexagonal.shop.adapter.out.persistent.AbstractProductRepositoryTest;

class InMemoryProductRepositoryTest
    extends AbstractProductRepositoryTest<InMemoryProductRepository> {

  @Override
  protected InMemoryProductRepository createProductRepository() {
    return new InMemoryProductRepository();
  }
}

