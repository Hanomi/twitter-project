package io.shifu.twitterproject.services;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendEmail(String mail, String subject, String text);
}
