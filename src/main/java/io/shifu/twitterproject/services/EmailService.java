package io.shifu.twitterproject.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    public void sendEmail(SimpleMailMessage email);
}
