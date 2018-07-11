package io.shifu.twitterproject.controller;

import io.shifu.twitterproject.model.User;
import io.shifu.twitterproject.services.EmailService;
import io.shifu.twitterproject.services.UserService;
import io.shifu.twitterproject.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class UserController {
    private UserValidator userValidator;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    public void setUserValidator(UserValidator userValidator, UserService userService, EmailService emailService) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Неверные данные или аккаунт не активен.");
        }
        if (logout != null) {
            model.addAttribute("message", "Выход выполнен");
        }
        model.addAttribute("active", "login");
        model.addAttribute("title", "Введите данные своего аккаунта");
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userForm", new User());

        model.addAttribute("active", "login");
        model.addAttribute("title", "Регистрация аккаунта");
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("active", "login");
            model.addAttribute("title", "Регистрация аккаута");
            return "registration";
        }

        // Disable user until they click on confirmation link in email
        userForm.setEnabled(false);

        // Generate random 36-character string token for confirmation link
        userForm.setConfirmationToken(UUID.randomUUID().toString());

        // Send email
        String appUrl = "http://localhost:8080";
        String subject = "Registration Confirmation";
        String text = "To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/confirm?token=" + userForm.getConfirmationToken();
        emailService.sendEmail(userForm.getEmail(), subject, text);

        // Save user
        userService.save(userForm);

        model.addAttribute("message", "A confirmation e-mail has been sent to " + userForm.getEmail());
        model.addAttribute("active", "login");
        model.addAttribute("title", "Введите данные своего аккаунта");
        return "login";
    }

    // Process confirmation link
    @GetMapping("/confirm")
    public String confirm(Model model, @RequestParam("token") String token) {
        if (token == null || token.isEmpty()) {
            model.addAttribute("active", "login");
            model.addAttribute("error", "Ссылка недействительна.");
            return "login";
        }
        User user = userService.findByConfirmationToken(token);
        if (user == null) { // No token found in DB
            model.addAttribute("error", "Ссылка недействительна.");
        } else { // Token found
            model.addAttribute("message", "Аккаунт успешно активирован");
            userService.activationUser(user);
        }
        model.addAttribute("active", "login");
        model.addAttribute("title", "Введите данные своего аккаунта");
        return "login";
    }
}
