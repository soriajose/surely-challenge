package com.surely.challenge.adapter.data.cart.repoimplementation;

import com.surely.challenge.adapter.data.cart.mapper.CartDataMapper;
import com.surely.challenge.adapter.data.cart.model.CartEntity;
import com.surely.challenge.adapter.data.cart.repository.CartRepository;
import com.surely.challenge.adapter.data.cartitem.model.CartItemEntity;
import com.surely.challenge.cart.exception.CartException;
import com.surely.challenge.cart.input.RemoveProductFromCartInput;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.output.RemoveProductFromCartGateway;
import org.springframework.stereotype.Service;

@Service
public class RemoveProductRepoImplementation implements RemoveProductFromCartGateway {

    private final CartRepository cartRepository;

    public RemoveProductRepoImplementation(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public Cart removeProduct(Cart cart) {

        CartEntity cartEntity = CartDataMapper.dataCoreToEntityMapper(cart);

        cartEntity = cartRepository.save(cartEntity);

        return CartDataMapper.dataEntityToCoreMapper(cartEntity);
    }
}
