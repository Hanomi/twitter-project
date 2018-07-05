package io.shifu.twitterproject.services;

import io.shifu.twitterproject.model.Message;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageService {
    List<Message> findAll();

    void save(Message message);

    Page<Message> findAll(Integer pageNumber);
}
