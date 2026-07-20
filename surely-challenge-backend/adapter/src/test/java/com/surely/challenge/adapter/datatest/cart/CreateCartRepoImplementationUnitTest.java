package com.surely.challenge.adapter.datatest.cart;

import com.surely.challenge.adapter.data.cart.mapper.CartDataMapper;
import com.surely.challenge.adapter.data.cart.model.CartEntity;
import com.surely.challenge.adapter.data.cart.repoimplementation.CreateCartRepoImplementation;
import com.surely.challenge.adapter.data.cart.repository.CartRepository;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.output.CreateCartGateway;
import com.surely.challenge.cart.strategy.implementation.NormalPricingStrategy;
import com.surely.challenge.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCartRepoImplementationUnitTest {

    private CreateCartGateway createCartGateway;

    @Mock
    private CartRepository cartRepository;

    @BeforeEach
    void setup() {
        createCartGateway = new CreateCartRepoImplementation(cartRepository);
    }

    @Test
    public void saveCart_cartValid_saveCartSuccess() {

        User user = User.factoryUser("Juan", "Perez", "12345678", "juan@gmail.com", false);
        Cart cart = Cart.factory(user, new NormalPricingStrategy());
        
        CartEntity entityMock = CartDataMapper.dataCoreToEntityMapper(cart);
        entityMock.setId(10L);

        when(cartRepository.save(any(CartEntity.class))).thenReturn(entityMock);


        Cart result = createCartGateway.saveCart(cart);


        assertNotNull(result);
        assertEquals(10L, result.getId());
    }
}
