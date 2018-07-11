package io.shifu.twitterproject.repository;

import io.shifu.twitterproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByNick(String nick);

    User findByVkId(Long id);

    User findByConfirmationToken(String confirmationToken);

    User findByResetToken(String resetToken);
}
