package com.hexagonal.shop.adapter.out.persistent.inmemory;

import com.hexagonal.shop.adapter.out.persistent.AbstractCartRepositoryTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class InMemoryCartRepositoryTest extends AbstractCartRepositoryTest {}
