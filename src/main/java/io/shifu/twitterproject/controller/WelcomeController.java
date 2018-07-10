package io.shifu.twitterproject.controller;

import io.shifu.twitterproject.model.Like;
import io.shifu.twitterproject.model.Message;
import io.shifu.twitterproject.model.User;
import io.shifu.twitterproject.services.LikeService;
import io.shifu.twitterproject.services.MessageService;
import io.shifu.twitterproject.services.UserService;
import io.shifu.twitterproject.validator.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class WelcomeController {

    private final MessageService messageService;
    private final MessageValidator messageValidator;
    private final UserService userService;
    private final LikeService likeService;

    @Autowired
    public WelcomeController(MessageService messageService, MessageValidator messageValidator, UserService userService, LikeService likeService) {
        this.messageService = messageService;
        this.messageValidator = messageValidator;
        this.userService = userService;
        this.likeService = likeService;
    }

    @GetMapping({"/", "/pages/{pageId}", "/edit/{messageId}", "/pages/{pageId}/edit/{messageId}",
            "/user/{id}", "/user/{id}/pages/{pageId}", "/user/{id}/edit/{messageId}", "/user/{id}/pages/{pageId}/edit/{messageId}"})
    public String getView(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user, @PathVariable("id") Optional<Long> id,
                                  @PathVariable("pageId") Optional<Integer> pageId, @PathVariable("messageId") Optional<Long> messageId) {
        String url = id.map(user_id -> "/user/" + user_id).orElse("") + pageId.map(page_id -> "/pages/" + page_id).orElse("");
        if (messageId.isPresent()) {
            Optional<Message> optionalMessage = messageService.findById(messageId.get());
            if (!optionalMessage.isPresent()) return "redirect:" + url;
            model.addAttribute("messageForm", optionalMessage.get());
            model.addAttribute("editMode", true);
        } else {
            model.addAttribute("messageForm", new Message());
        }
        model.addAttribute("currentUrl", url);
        model.addAttribute("pagePath", id.map(aLong -> "/user/" + aLong + "/pages/").orElse("/pages/"));
        model.addAttribute("userId", id);
        Page<Message> page;
        if (id.isPresent()) {
            Optional<User> optionalUser = userService.findById(id.get());
            if (!optionalUser.isPresent()) return "redirect:/";
            page = messageService.findAllByUser(optionalUser.get(), pageId.orElse(1));
        } else {
            page = messageService.findAll(pageId.orElse(1));
        }
        makePage(page, model);


        List<Long> likedList = new ArrayList<>();
        if (user != null) {
            for (Message msg : page.getContent()) {
                for (Like like : msg.getLikes()) {
                    if (like.getUser().equals(user.getUsername())) {
                        likedList.add(like.getMessage());
                    }
                }
            }
        }
        model.addAttribute("liked", likedList);


        return "index";
    }

    @PostMapping({"/", "/pages/{pageId}", "/edit/{messageId}", "/pages/{pageId}/edit/{messageId}",
            "/user/{id}", "/user/{id}/pages/{pageId}", "/user/{id}/edit/{messageId}", "/user/{id}/pages/{pageId}/edit/{messageId}"})
    public String postView(Model model, @ModelAttribute("messageForm") Message message,
                                  @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                                  @PathVariable("id") Optional<Long> id, @PathVariable("pageId") Optional<Integer> pageId,
                                  @PathVariable("messageId") Optional<Long> messageId, BindingResult bindingResult) {
        messageValidator.validate(message, bindingResult);
        String url = id.map(user_id -> "/user/" + user_id).orElse("") + pageId.map(page_id -> "/pages/" + page_id).orElse("");
        if (!bindingResult.hasErrors()) {
            if (messageId.isPresent()) {
                message.setUser(userService.findByUsername(user.getUsername()));
                messageService.save(message);
                return "redirect:" + (url.isEmpty() ? "/" : url);
            } else {
                message.setUser(userService.findByUsername(user.getUsername()));
                message.setDate(new Date());
                messageService.save(message);
                model.addAttribute("messageForm", new Message());
            }
        }
        model.addAttribute("currentUrl", url);
        model.addAttribute("pagePath", id.map(aLong -> "/user/" + aLong + "/pages/").orElse("/pages/"));
        model.addAttribute("userId", id);
        Page<Message> page;
        if (id.isPresent()) {
            Optional<User> optionalUser = userService.findById(id.get());
            if (!optionalUser.isPresent()) return "redirect:/";
            page = messageService.findAllByUser(optionalUser.get(), pageId.orElse(1));
        } else {
            page = messageService.findAll(pageId.orElse(1));
        }
        makePage(page, model);
        return "index";
    }

    @GetMapping({"/like/{likeId}", "/pages/{pageId}/like/{likeId}",
            "/user/{id}/like/{likeId}", "/user/{id}/pages/{pageId}/like/{likeId}"})
    public String like(Model model, @PathVariable("likeId") Long likeId, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user, @PathVariable("id") Optional<Long> id, @PathVariable("pageId") Optional<Integer> pageId) {
        String url = id.map(user_id -> "/user/" + user_id).orElse("") + pageId.map(page_id -> "/pages/" + page_id).orElse("");
        if (user != null && !user.getUsername().equals(messageService.findById(likeId).get().getUser().getUsername())) {
            if (likeService.liked(likeId, user.getUsername())) {
                likeService.delete(likeId, user.getUsername());
            } else {
                Like like = new Like();
                like.setMessage(likeId);
                like.setUser(user.getUsername());
                likeService.save(like);
            }
        }
        return "redirect:" + (url.isEmpty() ? "/" : url);
    }

    @GetMapping({"/retweet/{retweetId}", "/pages/{pageId}/retweet/{retweetId}",
            "/user/{id}/retweet/{retweetId}", "/user/{id}/pages/{pageId}/retweet/{retweetId}"})
    public String retweet(Model model, @PathVariable("retweetId") Long retweetId, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user, @PathVariable("id") Optional<Long> id, @PathVariable("pageId") Optional<Integer> pageId) {
        String url = id.map(user_id -> "/user/" + user_id).orElse("") + pageId.map(page_id -> "/pages/" + page_id).orElse("");
        if (user != null && !user.getUsername().equals(messageService.findById(retweetId).get().getUser().getUsername())) {
            Message message = new Message();
            Message oldMessage = messageService.findById(retweetId).get();
            message.setText(oldMessage.getText());
            message.setDate(new Date());
            message.setUser(userService.findByUsername(user.getUsername()));
            message.setRetweet(oldMessage);
            messageService.save(message);
        }
        return "redirect:" + (url.isEmpty() ? "/" : url);
    }

    @GetMapping({"/message/{messageId}", "/message/{messageId}/pages/{pageId}"})
    public String getMessage(Model model, @PathVariable("messageId") Long messageId, @PathVariable("pageId") Optional<Integer> pageId, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        Optional<Message> optionalMessage = messageService.findById(messageId);
        if (optionalMessage.isPresent()) {
            model.addAttribute("currentMessage", optionalMessage.get());
            String url = "/message/" + messageId + pageId.map(page_id -> "/pages/" + page_id).orElse("");
            model.addAttribute("messageForm", new Message());
            model.addAttribute("currentUrl", url);
            model.addAttribute("pagePath", "/message/" + messageId + "/pages/");
            Page<Message> page = messageService.findAllByAnswer(optionalMessage.get(), pageId.orElse(1));
            makePage(page, model);

            List<Long> likedList = new ArrayList<>();
            if (user != null) {
                for (Message msg : page.getContent()) {
                    for (Like like : msg.getLikes()) {
                        if (like.getUser().equals(user.getUsername())) {
                            likedList.add(like.getMessage());
                        }
                    }
                }
                for (Like like : optionalMessage.get().getLikes()) {
                    if (like.getUser().equals(user.getUsername())) {
                        likedList.add(like.getMessage());
                    }
                }
            }
            model.addAttribute("liked", likedList);

            return "message";
        } else {
            return "redirect:/";
        }
    }

    private static void makePage(Page page, Model model) {
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
