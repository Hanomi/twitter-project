package io.shifu.twitterproject.controller;

import io.shifu.twitterproject.model.Message;
import io.shifu.twitterproject.services.MessageService;
import io.shifu.twitterproject.services.UserService;
import io.shifu.twitterproject.validator.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WelcomeController {

    private final MessageService messageService;
    private final MessageValidator messageValidator;
    private final UserService userService;

    @Autowired
    public WelcomeController(MessageService messageService, MessageValidator messageValidator, UserService userService) {
        this.messageService = messageService;
        this.messageValidator = messageValidator;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {

        Page<Message> page = messageService.findAll(1);

        makePage(page, model);

        model.addAttribute("messageForm", new Message());
        return "index";
    }

    @PostMapping("/")
    public String messageAdd(Model model, @ModelAttribute("messageForm") Message message,
                             @AuthenticationPrincipal User user, BindingResult bindingResult) {
        messageValidator.validate(message, bindingResult);

        if (!bindingResult.hasErrors()) {
            message.setUser(userService.findByUsername(user.getUsername()));
            messageService.save(message);
            model.addAttribute("messageForm", new Message());
        }

        Page<Message> page = messageService.findAll(1);

        makePage(page, model);

        return "index";
    }

    @GetMapping("/pages/{pageNumber}")
    public String getPage(Model model, @PathVariable("pageNumber") Integer pageNumber) {
        Page<Message> page = messageService.findAll(pageNumber);

        makePage(page, model);

        model.addAttribute("messageForm", new Message());

        return "index";
    }

    @PostMapping("/pages/{pageNumber}")
    public String postPage(Model model, @PathVariable("pageNumber") Integer pageNumber, @ModelAttribute("messageForm") Message message,
                           @AuthenticationPrincipal User user, BindingResult bindingResult) {
        messageValidator.validate(message, bindingResult);

        if (!bindingResult.hasErrors()) {
            message.setUser(userService.findByUsername(user.getUsername()));
            messageService.save(message);
            model.addAttribute("messageForm", new Message());
        }

        Page<Message> page = messageService.findAll(pageNumber);

        makePage(page, model);

        return "index";
    }

    protected static void makePage(Page page, Model model) {
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
        model.addAttribute("messagesList", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("active", "general");
        model.addAttribute("title", "Твиттер");
    }
}
