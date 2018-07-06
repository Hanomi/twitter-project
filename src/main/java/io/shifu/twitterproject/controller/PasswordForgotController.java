package io.shifu.twitterproject.controller;

import io.shifu.twitterproject.model.User;
import io.shifu.twitterproject.services.EmailService;
import io.shifu.twitterproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class PasswordForgotController {

    private UserService userService;
    private EmailService emailService;

    @Autowired
    public PasswordForgotController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/forgot-password")
    public String getForgotForm(Model model) {
        model.addAttribute("active", "login");
        model.addAttribute("title", "Ввостановление пароля");
        return "forgot";
    }

    @PostMapping("/forgot-password")
    public String postForgotForm(Model model, @RequestParam("email") String email) {

        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("message", "Неверно указан почтовый адрес: " + email);
        } else {
            user.setResetToken(UUID.randomUUID().toString());
            userService.saveChange(user);
            // Send email
            String appUrl = "http://localhost:8080";
            String subject = "Password reset confirmation";
            String text = "To reset your password, please click the link below:\n"
                    + appUrl + "/reset?token=" + user.getResetToken();
            emailService.sendEmail(user.getEmail(), subject, text);
            model.addAttribute("message", "Инструкция для смены пароля отправлена на: " + email);
        }

        model.addAttribute("active", "login");
        model.addAttribute("title", "Ввостановление пароля");
        return "forgot";
    }

    @GetMapping("/reset")
    public String reset(Model model, @RequestParam("token") String token) {
        if (token == null || token.isEmpty()) {
            model.addAttribute("error", "Ссылка недействительна.");
            model.addAttribute("active", "login");
            model.addAttribute("title", "Введите данные своего аккаунта");
            return "login";
        }
        User user = userService.findByResetToken(token);
        if (user == null) { // No token found in DB
            model.addAttribute("error", "Ссылка недействительна.");
            model.addAttribute("active", "login");
            model.addAttribute("title", "Введите данные своего аккаунта");
            return "login";
        } else { // Token found
            model.addAttribute("active", "login");
            model.addAttribute("title", "Смена пароля");
            return "change-password";
        }
    }

    @PostMapping("/reset")
    public String resetPost(Model model, @RequestParam("token") String token,
                            @RequestParam("password") String password,
                            @RequestParam("confirmPassword") String confirmPassword) {
        if (token == null || token.isEmpty()) {
            model.addAttribute("error", "Ссылка недействительна.");
            model.addAttribute("active", "login");
            model.addAttribute("title", "Введите данные своего аккаунта");
            return "login";
        }
        User user = userService.findByResetToken(token);
        if (user == null) { // No token found in DB
            model.addAttribute("error", "Ссылка недействительна.");
            model.addAttribute("active", "login");
            model.addAttribute("title", "Введите данные своего аккаунта");
            return "login";
        } else { // Token found
            //todo validate password
            if (password.equals(confirmPassword)) {
                user.setPassword(password);
                user.setConfirmPassword(confirmPassword);
                userService.changePassword(user);
                model.addAttribute("message", "Пароль изменён.");
                model.addAttribute("active", "login");
                model.addAttribute("title", "Введите данные своего аккаунта");
                return "login";
            } else {
                model.addAttribute("message", "Пароли не совпадают.");
                model.addAttribute("active", "login");
                model.addAttribute("title", "Введите данные своего аккаунта");
                return "change-password";
            }
        }
    }
}
