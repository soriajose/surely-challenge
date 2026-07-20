package com.surely.challenge.adapter.servicetest.product;

import com.surely.challenge.adapter.service.product.controller.GetTopExpensiveProductsController;
import com.surely.challenge.adapter.service.product.dto.ProductResponseDTO;
import com.surely.challenge.product.input.GetTopExpensiveProductsInput;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.user.exception.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetTopExpensiveProductsControllerUnitTest {

    private GetTopExpensiveProductsController controller;

    @Mock
    private GetTopExpensiveProductsInput getTopExpensiveProductsInput;

    @BeforeEach
    void setup() {
        controller = new GetTopExpensiveProductsController(getTopExpensiveProductsInput);
    }

    @Test
    public void getTopExpensiveProducts_userExists_returnsHTTP200Success() {
        String document = "123456";
        Product product = Product.factoryProduct("Prod", "Desc", BigDecimal.TEN);
        product.setId(1L);
        when(getTopExpensiveProductsInput.getTopProducts(document)).thenReturn(List.of(product));

        ResponseEntity<?> response = controller.getTopExpensiveProducts(document);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ProductResponseDTO> body = (List<ProductResponseDTO>) response.getBody();
        assertEquals(1, body.size());
        assertEquals("Prod", body.get(0).getName());
    }

    @Test
    public void getTopExpensiveProducts_userNotExists_throwsUserException() {
        String document = "000000";
        when(getTopExpensiveProductsInput.getTopProducts(document)).thenThrow(new UserException("No existe un usuario"));

        UserException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                UserException.class,
                () -> controller.getTopExpensiveProducts(document)
        );

        org.junit.jupiter.api.Assertions.assertEquals("No existe un usuario", thrown.getMessage());
    }

    @Test
    public void getTopExpensiveProducts_unexpectedError_throwsRuntimeException() {
        String document = "123456";
        when(getTopExpensiveProductsInput.getTopProducts(document)).thenThrow(new RuntimeException("Error"));

        RuntimeException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> controller.getTopExpensiveProducts(document)
        );
        
        org.junit.jupiter.api.Assertions.assertEquals("Error", thrown.getMessage());
    }
}
