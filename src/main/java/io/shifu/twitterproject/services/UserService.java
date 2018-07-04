package io.shifu.twitterproject.services;

import io.shifu.twitterproject.model.User;

public interface UserService {

    void save(User user);

    void saveVk(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByConfirmationToken(String confirmationToken);

    void activationUser(User user);

    User findByVkId(Long id);
}
