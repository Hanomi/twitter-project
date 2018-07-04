package io.shifu.twitterproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String getLoginPage(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Неверные данные или аккаунт не активен.");
        }

        if (logout != null) {
            model.addAttribute("message", "Выход выполнен");
        }

        model.addAttribute("title", "Введите данные своего аккаунта");
        return "login";
    }
}
