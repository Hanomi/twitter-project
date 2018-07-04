package io.shifu.twitterproject.controller;

import io.shifu.twitterproject.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    private final MessageService messageService;

    @Autowired
    public WelcomeController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping("/")
    public String index(Model model) {

        // все сообщения на главной
        model.addAttribute("messagesList", messageService.findAll());

        // заголовок
        model.addAttribute("title", "Твиттер");
        return "index";
    }
}
