package com.surely.challenge.adapter.data.cart.repoimplementation;

import com.surely.challenge.adapter.data.cart.mapper.CartDataMapper;
import com.surely.challenge.adapter.data.cart.model.CartEntity;
import com.surely.challenge.adapter.data.cart.repository.CartRepository;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.output.CreateCartGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCartRepoImplementation implements CreateCartGateway {

    private final CartRepository cartRepository;

    public CreateCartRepoImplementation(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public Cart saveCart(Cart cart) {

        CartEntity cartEntity = CartDataMapper.dataCoreToEntityMapper(cart);

        cartEntity = cartRepository.save(cartEntity);

        return CartDataMapper.dataEntityToCoreMapper(cartEntity);
    }
}
