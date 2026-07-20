package com.surely.challenge.adapter.data.product.repository;

import com.surely.challenge.adapter.data.product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByIdAndDeletedAtIsNull(Long id);
    List<ProductEntity> findAllByDeletedAtIsNull();
}
