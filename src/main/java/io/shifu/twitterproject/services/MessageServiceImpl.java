package io.shifu.twitterproject.services;

import io.shifu.twitterproject.model.Message;
import io.shifu.twitterproject.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public void save(Message message) {
        message.setDate(new Date());
        messageRepository.save(message);
    }

    @Override
    public Page<Message> findAll(Integer pageNumber) {
        return messageRepository.findAll(PageRequest.of(pageNumber-1, PAGE_SIZE, Sort.Direction.DESC, "date"));
    }
}
