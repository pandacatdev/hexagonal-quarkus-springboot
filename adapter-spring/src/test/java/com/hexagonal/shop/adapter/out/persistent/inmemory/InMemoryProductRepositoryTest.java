package com.hexagonal.shop.adapter.out.persistent.inmemory;

import com.hexagonal.shop.adapter.out.persistent.AbstractProductRepositoryTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class InMemoryProductRepositoryTest extends AbstractProductRepositoryTest {}

