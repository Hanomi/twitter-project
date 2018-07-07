package io.shifu.twitterproject.controller;

import io.shifu.twitterproject.model.Message;
import io.shifu.twitterproject.model.User;
import io.shifu.twitterproject.services.MessageService;
import io.shifu.twitterproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String getUserMessages(Model model, @PathVariable("id") Long id) {
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()) return "redirect:/";

        Page<Message> page = messageService.findAllByUser(optionalUser.get(),1);

        WelcomeController.makePage(page, model);

        return "index";
    }


}
