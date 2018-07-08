package io.shifu.twitterproject.services;

import io.shifu.twitterproject.model.Like;
import org.springframework.transaction.annotation.Transactional;

public interface LikeService {

    void save(Like like);

    boolean liked(long id, String user);

    @Transactional
    void delete(long id, String user);
}
