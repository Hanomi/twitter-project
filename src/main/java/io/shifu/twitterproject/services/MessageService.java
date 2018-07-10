package io.shifu.twitterproject.services;

import io.shifu.twitterproject.model.Message;
import io.shifu.twitterproject.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    Optional<Message> findById(Long id);

    void save(Message message);

    Page<Message> findAll(Integer pageNumber);

    Page<Message> findAllByUser(User user, Integer pageNumber);

    Page<Message> findAllByAnswer(Message message, Integer pageNumber);
}
