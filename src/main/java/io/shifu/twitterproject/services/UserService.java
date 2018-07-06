package io.shifu.twitterproject.services;

import io.shifu.twitterproject.model.User;

public interface UserService {

    void save(User user);

    void saveVk(User user);

    void saveChange(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByConfirmationToken(String confirmationToken);

    User findByResetToken(String resetToken);

    void activationUser(User user);

    User findByVkId(Long id);

    void changePassword(User user);
}
