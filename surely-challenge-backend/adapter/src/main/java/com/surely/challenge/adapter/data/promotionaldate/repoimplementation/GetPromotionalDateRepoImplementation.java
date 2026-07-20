package com.surely.challenge.adapter.data.promotionaldate.repoimplementation;

import com.surely.challenge.adapter.data.promotionaldate.repository.PromotionalDateRepository;
import com.surely.challenge.cart.output.GetPromotionalDateGateway;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GetPromotionalDateRepoImplementation implements GetPromotionalDateGateway {

    private final PromotionalDateRepository promotionalDateRepository;

    public GetPromotionalDateRepoImplementation(PromotionalDateRepository promotionalDateRepository) {
        this.promotionalDateRepository = promotionalDateRepository;
    }

    @Override
    public boolean isPromotionalDate(LocalDate date) {
        return promotionalDateRepository.existsByDate(date);
    }
}
