package io.shifu.twitterproject.services;

import io.shifu.twitterproject.model.Message;
import io.shifu.twitterproject.model.User;
import io.shifu.twitterproject.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public Page<Message> findAll(Integer pageNumber) {
        return messageRepository.findAll(PageRequest.of(pageNumber-1, PAGE_SIZE, Sort.Direction.DESC, "date"));
    }

    @Override
    public Page<Message> findAllByUser(User user, Integer pageNumber) {
        return messageRepository.findAllByUser(user, PageRequest.of(pageNumber-1, PAGE_SIZE, Sort.Direction.DESC, "date"));
    }
}
