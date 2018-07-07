package io.shifu.twitterproject.services;

import io.shifu.twitterproject.model.User;

import java.util.Optional;

public interface UserService {

    void save(User user);

    void saveVk(User user);

    void saveChange(User user);

    Optional<User> findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByConfirmationToken(String confirmationToken);

    User findByResetToken(String resetToken);

    void activationUser(User user);

    User findByVkId(Long id);

    void changePassword(User user);
}
