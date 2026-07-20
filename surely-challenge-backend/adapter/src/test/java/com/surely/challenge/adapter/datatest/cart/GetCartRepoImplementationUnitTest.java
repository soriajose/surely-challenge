package com.surely.challenge.adapter.datatest.cart;

import com.surely.challenge.adapter.data.cart.model.CartEntity;
import com.surely.challenge.adapter.data.user.model.UserEntity;
import com.surely.challenge.adapter.data.cart.repoimplementation.GetCartRepoImplementation;
import com.surely.challenge.adapter.data.cart.repository.CartRepository;
import com.surely.challenge.cart.model.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCartRepoImplementationUnitTest {

    private GetCartRepoImplementation getCartRepoImplementation;

    @Mock
    private CartRepository cartRepository;

    @BeforeEach
    void setup() {
        getCartRepoImplementation = new GetCartRepoImplementation(cartRepository);
    }

    @Test
    public void findById_cartExists_returnsCartSuccess() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Juan");
        userEntity.setLastName("Perez");
        userEntity.setDocumentNumber("12345678");
        userEntity.setEmail("juan@mail.com");

        CartEntity entity = new CartEntity();
        entity.setId(1L);
        entity.setUser(userEntity);

        when(cartRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(entity));

        Cart result = getCartRepoImplementation.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void findById_cartNotExists_returnsNullSuccess() {
        when(cartRepository.findByIdAndDeletedAtIsNull(99L)).thenReturn(Optional.empty());

        Cart result = getCartRepoImplementation.findById(99L);

        assertNull(result);
    }
}
