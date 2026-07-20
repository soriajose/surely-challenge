package com.surely.challenge.adapter.data.user.repoimplementation;

import com.surely.challenge.adapter.data.user.mapper.UserDataMapper;
import com.surely.challenge.adapter.data.user.repository.UserRepository;
import com.surely.challenge.user.model.User;
import com.surely.challenge.user.output.GetUserGateway;
import org.springframework.stereotype.Service;

@Service
public class GetUserRepoImplementation implements GetUserGateway {

    private final UserRepository userRepository;

    public GetUserRepoImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByDocument(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber)
                .map(UserDataMapper::dataCoreMapper)
                .orElse(null);
    }
}
