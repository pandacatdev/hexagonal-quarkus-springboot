package com.hexagonal.shop.adapter.out.persistent.jpa;

import com.hexagonal.shop.adapter.out.persistent.AbstractCartRepositoryTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test-with-mysql")
public class JpaCartRepositoryTest extends AbstractCartRepositoryTest{}
