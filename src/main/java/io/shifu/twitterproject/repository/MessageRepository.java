package io.shifu.twitterproject.repository;

import io.shifu.twitterproject.model.Message;
import io.shifu.twitterproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findAllByThreadIsNull(Pageable pageable);

    Page<Message> findAllByUser(User user, Pageable pageable);

    Page<Message> findAllByThread(Long thread, Pageable pageable);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE messages SET rgt = rgt + 2, lft = IF(lft > ?2, lft + 2, lft) WHERE rgt >= ?2 AND thread = ?1")
    void updateKeys(Long thread, Long right_key);

}
