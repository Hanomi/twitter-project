package io.shifu.twitterproject.repository;


import io.shifu.twitterproject.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByMessageAndUser(long messageId, String user);

    void deleteByMessageAndUser(long messageId, String user);
}
