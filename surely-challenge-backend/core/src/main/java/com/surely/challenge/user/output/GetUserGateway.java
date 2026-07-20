package com.surely.challenge.user.output;

import com.surely.challenge.user.model.User;

public interface GetUserGateway {

    User findByDocument(String document);
}
