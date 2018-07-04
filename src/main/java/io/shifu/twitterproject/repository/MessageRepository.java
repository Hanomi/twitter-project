package io.shifu.twitterproject.repository;

import io.shifu.twitterproject.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
