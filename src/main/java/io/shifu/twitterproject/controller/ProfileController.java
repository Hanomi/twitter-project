package io.shifu.twitterproject.controller;

import io.shifu.twitterproject.model.User;
import io.shifu.twitterproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getForgotForm(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        if (user != null) {
            User currentUser = userService.findByEmail(user.getUsername());
            model.addAttribute("currentNick", currentUser.getNick());
            model.addAttribute("active", "login");
            model.addAttribute("title", "Профиль");
            return "profile";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/profile")
    public String postForgotForm(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user, @RequestParam("nick") String nick) {
        if (user != null) {
            if (!validateNick(model, nick)) {
                User currentUser = userService.findByEmail(user.getUsername());
                currentUser.setNick(nick);
                userService.saveChange(currentUser);
                model.addAttribute("message", "Ник изменён.");
            }
            model.addAttribute("currentNick", nick);
            model.addAttribute("active", "login");
            model.addAttribute("title", "Профиль");
            return "profile";
        } else {
            return "redirect:/";
        }
    }

    private boolean validateNick(Model model, String nick) {
        if (nick.length() < 6 || nick.length() > 32) {
            model.addAttribute("error", "Длина ника должна быть от 6 до 32 символов.");
            return true;
        }
        if (!nick.matches("[A-Za-z]+[A-Za-z0-9_]*")) {
            model.addAttribute("error", "Ник должен состоять только из латинских букв, цифр и символа _, а так же начинаться с буквы");
            return true;
        }
        return false;
    }
}
