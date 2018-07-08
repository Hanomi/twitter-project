package io.shifu.twitterproject.services;

import io.shifu.twitterproject.model.Like;
import io.shifu.twitterproject.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    private LikeRepository likeRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void save(Like like) {
        likeRepository.save(like);
    }

    @Override
    public boolean liked(long id, String user) {
        return likeRepository.existsByMessageAndUser(id, user);
    }

    @Override
    public void delete(long id, String user) {
        likeRepository.deleteByMessageAndUser(id, user);
    }
}
