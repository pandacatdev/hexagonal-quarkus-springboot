package com.hexagonal.shop.adapter.out.persistent.jpa.cart;

import com.hexagonal.shop.application.port.out.persistence.CartRepository;
import com.hexagonal.shop.model.cart.Cart;
import com.hexagonal.shop.model.customer.CustomerId;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@ConditionalOnProperty(name = "persistence", havingValue = "mysql")
@Repository
public class JpaCartRepository implements CartRepository {

  private final JpaCartSpringDataRepository springDataRepository;

  public JpaCartRepository(JpaCartSpringDataRepository springDataRepository) {
    this.springDataRepository = springDataRepository;
  }

  @Override
  @Transactional
  public void save(Cart cart) {
    springDataRepository.save(CartMapper.toJpaEntity(cart));
  }

  @Override
  @Transactional
  public Optional<Cart> findByCustomerId(CustomerId customerId) {
    Optional<CartJpaEntity> cartJpaEntity = springDataRepository.findById(customerId.value());
    return cartJpaEntity.map(CartMapper::toModelEntity);
  }

  @Override
  @Transactional
  public void deleteByCustomerId(CustomerId customerId) {
    springDataRepository.deleteById(customerId.value());
  }
}
