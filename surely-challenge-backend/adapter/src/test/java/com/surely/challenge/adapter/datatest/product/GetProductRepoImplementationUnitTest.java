package com.surely.challenge.adapter.datatest.product;

import com.surely.challenge.adapter.data.product.model.ProductEntity;
import com.surely.challenge.adapter.data.product.repoimplementation.GetProductRepoImplementation;
import com.surely.challenge.adapter.data.product.repository.ProductRepository;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.product.output.GetProductGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProductRepoImplementationUnitTest {

    private GetProductGateway getProductGateway;

    @Mock
    private ProductRepository productRepositorySpringData;

    @BeforeEach
    void setup() {
        getProductGateway = new GetProductRepoImplementation(productRepositorySpringData);
    }

    @Test
    public void findById_productExists_returnsProductSuccess() {

        ProductEntity entity = new ProductEntity();
        entity.setId(10L);
        entity.setName("Test Product");
        entity.setDescription("Test Description");
        entity.setPrice(new BigDecimal("100.0"));

        when(productRepositorySpringData.findByIdAndDeletedAtIsNull(10L)).thenReturn(Optional.of(entity));


        Product result = getProductGateway.findById(10L);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Test Product", result.getName());
    }

    @Test
    public void findById_productNotExists_returnsNullSuccess() {

        when(productRepositorySpringData.findByIdAndDeletedAtIsNull(99L)).thenReturn(Optional.empty());

        Product result = getProductGateway.findById(99L);

        assertNull(result);
    }
}
