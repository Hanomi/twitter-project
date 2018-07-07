package io.shifu.twitterproject.repository;

import io.shifu.twitterproject.model.Message;
import io.shifu.twitterproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByUser(User user, Pageable pageable);
}
