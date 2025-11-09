package com.hexagonal.shop.adapter.out.persistent.jpa;

import com.hexagonal.shop.adapter.TestProfileWithMySQL;
import com.hexagonal.shop.adapter.out.persistent.AbstractCartRepositoryTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(TestProfileWithMySQL.class)
public class JpaCartRepositoryTest extends AbstractCartRepositoryTest{}
