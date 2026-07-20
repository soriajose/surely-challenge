package com.surely.challenge.cart.output;

import java.time.LocalDate;

public interface GetPromotionalDateGateway {
    boolean isPromotionalDate(LocalDate date);
}
