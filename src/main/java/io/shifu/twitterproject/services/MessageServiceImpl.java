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
    private static final int PAGE_SIZE = 10;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void saveReply(Message message, Message parent) {

        message.setLvl(parent.getLvl()+1);
        message.setLft(parent.getRgt());
        message.setRgt(parent.getRgt()+1);

        messageRepository.updateKeys(parent.getThread(), parent.getRgt());

        messageRepository.save(message);
    }

    @Override
    public Page<Message> findAll(Integer pageNumber) {
        return messageRepository.findAllByThreadIsNull(PageRequest.of(pageNumber-1, PAGE_SIZE, Sort.Direction.DESC, "date"));
    }

    @Override
    public Page<Message> findAllByUser(User user, Integer pageNumber) {
        return messageRepository.findAllByUser(user, PageRequest.of(pageNumber-1, PAGE_SIZE, Sort.Direction.DESC, "date"));
    }

    @Override
    public List<Message> findAllByThread(Message message) {
        //return messageRepository.findAllByThread(threadId, PageRequest.of(pageNumber-1, PAGE_SIZE, Sort.Direction.DESC, "date"));
        return messageRepository.findByThread(message.getThread(), message.getLft(), message.getRgt());
    }
}
