package com.surely.challenge.adapter.data.cart.repository;

import com.surely.challenge.adapter.data.cart.model.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByIdAndDeletedAtIsNull(Long id);
}
