package com.surely.challenge.adapter.servicetest.cart;

import com.surely.challenge.adapter.service.cart.controller.AddProductToCartController;
import com.surely.challenge.adapter.service.cart.dto.AddProductToCartRequestDTO;
import com.surely.challenge.adapter.service.cart.dto.CartStateResponseDTO;
import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.AddProductToCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.strategy.implementation.NormalPricingStrategy;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddProductToCartControllerUnitTest {

    private AddProductToCartController addProductToCartController;

    @Mock
    private AddProductToCartInput addProductToCartInput;

    @BeforeEach
    void setup() {
        addProductToCartController = new AddProductToCartController(addProductToCartInput);
    }

    @Test
    public void addProductToCart_productAndCartExist_returnsHTTP200Success() {
        AddProductToCartRequestDTO request = new AddProductToCartRequestDTO(5L);
        User user = User.factoryUser("Juan", "Perez", "123", "juan@gmail.com", false);
        Cart cart = Cart.factory(user, new NormalPricingStrategy());
        cart.setId(1L);
        Product product = Product.factoryProduct("Producto 1", "Esto es una descripción", new BigDecimal("100"));
        product.setId(5L);
        cart.addProduct(product);

        when(addProductToCartInput.addProduct(5L, 1L)).thenReturn(cart);


        ResponseEntity<?> result = addProductToCartController.addProductToCart(1L, request);


        assertEquals(HttpStatus.OK, result.getStatusCode());
        CartStateResponseDTO body = (CartStateResponseDTO) result.getBody();
        assertEquals(1L, body.getCartId());
        assertEquals(new BigDecimal("100"), body.getTotal());
        assertEquals(1, body.getItems().size());
    }

    @Test
    public void addProductToCart_cartNotFound_throwsCartException() {

        AddProductToCartRequestDTO request = new AddProductToCartRequestDTO(5L);
        CartException exception = new CartException("No existe un cart con el ID: 99");
        when(addProductToCartInput.addProduct(5L, 99L)).thenThrow(exception);

        CartException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                CartException.class,
                () -> addProductToCartController.addProductToCart(99L, request)
        );

        org.junit.jupiter.api.Assertions.assertEquals("No existe un cart con el ID: 99", thrown.getMessage());
    }
}
