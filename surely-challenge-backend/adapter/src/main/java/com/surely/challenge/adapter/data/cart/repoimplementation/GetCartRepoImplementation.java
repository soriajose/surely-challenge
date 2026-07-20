package com.surely.challenge.adapter.data.cart.repoimplementation;

import com.surely.challenge.adapter.data.cart.mapper.CartDataMapper;
import com.surely.challenge.adapter.data.cart.repository.CartRepository;
import com.surely.challenge.cart.model.Cart;
import com.surely.challenge.cart.output.GetCartGateway;
import org.springframework.stereotype.Service;

@Service
public class GetCartRepoImplementation implements GetCartGateway {

    private final CartRepository cartRepository;

    public GetCartRepoImplementation(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart findById(Long cartId) {
        return cartRepository.findByIdAndDeletedAtIsNull(cartId)
                .map(CartDataMapper::dataEntityToCoreMapper)
                .orElse(null);
    }
}
