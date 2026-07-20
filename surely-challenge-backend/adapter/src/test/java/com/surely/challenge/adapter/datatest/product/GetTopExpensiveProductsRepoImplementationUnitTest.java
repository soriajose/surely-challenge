package com.surely.challenge.adapter.datatest.product;

import com.surely.challenge.adapter.data.product.model.ProductEntity;
import com.surely.challenge.adapter.data.product.repoimplementation.GetTopExpensiveProductsRepoImplementation;
import com.surely.challenge.adapter.data.product.repository.GetTopExpensiveProductsRepository;
import com.surely.challenge.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetTopExpensiveProductsRepoImplementationUnitTest {

    private GetTopExpensiveProductsRepoImplementation repoImplementation;

    @Mock
    private GetTopExpensiveProductsRepository repository;

    @BeforeEach
    void setup() {
        repoImplementation = new GetTopExpensiveProductsRepoImplementation(repository);
    }

    @Test
    public void getTopExpensiveProductsByDocument_productsExist_returnsProductsSuccess() {
        String doc = "123456";
        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        entity.setName("Test");
        entity.setDescription("Test desc");
        entity.setPrice(BigDecimal.TEN);

        when(repository.findTopExpensiveProductsByDocument(doc, 5)).thenReturn(List.of(entity));

        List<Product> result = repoImplementation.getTopExpensiveProductsByDocument(doc, 5);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getName());
    }
}
