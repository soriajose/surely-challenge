package com.surely.challenge.adapter.data.product.repository;

import com.surely.challenge.adapter.data.product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetTopExpensiveProductsRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = """
            SELECT DISTINCT p.* 
            FROM products p
            INNER JOIN cart_items ci ON p.id = ci.product_id
            INNER JOIN carts c ON ci.cart_id = c.id
            INNER JOIN users u ON c.user_id = u.id
            WHERE u.document_number = :documentNumber 
              AND c.status = 'CLOSED'
              AND c.deleted_at IS NULL
              AND ci.deleted_at IS NULL
            ORDER BY p.price DESC 
            LIMIT :limit
            """, nativeQuery = true)
    List<ProductEntity> findTopExpensiveProductsByDocument(@Param("documentNumber") String documentNumber, @Param("limit") int limit);

}
