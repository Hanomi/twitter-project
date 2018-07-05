package io.shifu.twitterproject.controller;

import io.shifu.twitterproject.model.Message;
import io.shifu.twitterproject.services.MessageService;
import io.shifu.twitterproject.validator.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WelcomeController {

    private final MessageService messageService;
    private final MessageValidator messageValidator;

    @Autowired
    public WelcomeController(MessageService messageService, MessageValidator messageValidator) {
        this.messageService = messageService;
        this.messageValidator = messageValidator;
    }

    @GetMapping("/")
    public String index(Model model) {

        // все сообщения на главной
        model.addAttribute("messagesList", messageService.findAll());

        // форма добавления
        model.addAttribute("messageForm", new Message());

        model.addAttribute("active", "general");
        model.addAttribute("title", "Твиттер");
        return "index";
    }

    @PostMapping("/")
    public String messageAdd(Model model, @ModelAttribute("messageForm") Message message, BindingResult bindingResult) {
        messageValidator.validate(message, bindingResult);

        if (!bindingResult.hasErrors()) {
            messageService.save(message);
            model.addAttribute("messageForm", new Message());
        }

        // все сообщения на главной
        model.addAttribute("messagesList", messageService.findAll());

        model.addAttribute("active", "general");
        model.addAttribute("title", "Твиттер");

        return "index";
    }
}
