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

import java.util.*;

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

    @GetMapping({"/", "/edit/{messageId}",
            "/pages/{pageId}", "/pages/{pageId}/edit/{messageId}",
            "/user/{userId}", "/user/{userId}/edit/{messageId}",
            "/user/{userId}/pages/{pageId}", "/user/{userId}/pages/{pageId}/edit/{messageId}"})
    public String getView(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                          @PathVariable("userId") Optional<Long> userId,
                          @PathVariable("pageId") Optional<Integer> pageId,
                          @PathVariable("messageId") Optional<Long> messageId) {
        String url = userId.map(user_id -> "/user/" + user_id).orElse("") + pageId.map(page_id -> "/pages/" + page_id).orElse("");
        if (fillForm(model, messageId)) return "redirect:" + url;
        model.addAttribute("currentUrl", url);
        model.addAttribute("pagePath", userId.map(aLong -> "/user/" + aLong + "/pages/").orElse("/pages/"));
        model.addAttribute("userId", userId);
        Page<Message> page;
        if (userId.isPresent()) {
            Optional<User> optionalUser = userService.findById(userId.get());
            if (!optionalUser.isPresent()) return "redirect:/";
            page = messageService.findAllByUser(optionalUser.get(), pageId.orElse(1));
        } else {
            page = messageService.findAll(pageId.orElse(1));
        }
        makePage(page, model);

        model.addAttribute("liked", getUserLikes(user, page.getContent()));


        return "index";
    }

    @PostMapping({"/", "/edit/{messageId}",
            "/pages/{pageId}", "/pages/{pageId}/edit/{messageId}",
            "/user/{userId}", "/user/{userId}/edit/{messageId}",
            "/user/{userId}/pages/{pageId}", "/user/{userId}/pages/{pageId}/edit/{messageId}"})
    public String postView(Model model, @ModelAttribute("messageForm") Message message,
                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                           @PathVariable("userId") Optional<Long> userId,
                           @PathVariable("pageId") Optional<Integer> pageId,
                           @PathVariable("messageId") Optional<Long> messageId, BindingResult bindingResult) {
        messageValidator.validate(message, bindingResult);
        String url = userId.map(user_id -> "/user/" + user_id).orElse("") + pageId.map(page_id -> "/pages/" + page_id).orElse("");
        if (!bindingResult.hasErrors()) {
            if (messageId.isPresent()) {
                message.setUser(userService.findByEmail(user.getUsername()));
                messageService.save(message);
                return "redirect:" + (url.isEmpty() ? "/" : url);
            } else {
                message.setUser(userService.findByEmail(user.getUsername()));
                message.setDate(new Date());
                messageService.save(message);
                model.addAttribute("messageForm", new Message());
            }
        }
        model.addAttribute("currentUrl", url);
        model.addAttribute("pagePath", userId.map(aLong -> "/user/" + aLong + "/pages/").orElse("/pages/"));
        model.addAttribute("userId", userId);
        Page<Message> page;
        if (userId.isPresent()) {
            Optional<User> optionalUser = userService.findById(userId.get());
            if (!optionalUser.isPresent()) return "redirect:/";
            page = messageService.findAllByUser(optionalUser.get(), pageId.orElse(1));
        } else {
            page = messageService.findAll(pageId.orElse(1));
        }
        makePage(page, model);

        model.addAttribute("liked", getUserLikes(user, page.getContent()));

        return "index";
    }

    @GetMapping({"/like/{likeId}",
            "/pages/{pageId}/like/{likeId}",
            "/user/{userId}/like/{likeId}",
            "/user/{userId}/pages/{pageId}/like/{likeId}",
            "/message/{messageId}/like/{likeId}",
            "/message/{messageId}/pages/{pageId}/like/{likeId}"
    })
    public String like(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                       @PathVariable("likeId") Long likeId,
                       @PathVariable("messageId") Optional<Long> messageId,
                       @PathVariable("userId") Optional<Long> userId,
                       @PathVariable("pageId") Optional<Integer> pageId) {
        String url = messageId.map(message_Id -> "/message/" + message_Id).orElse("") + userId.map(user_id -> "/user/" + user_id).orElse("") + pageId.map(page_id -> "/pages/" + page_id).orElse("");
        if (user != null && !user.getUsername().equals(messageService.findById(likeId).get().getUser().getEmail())) {
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

    @GetMapping({"/retweet/{retweetId}",
            "/pages/{pageId}/retweet/{retweetId}",
            "/user/{userId}/retweet/{retweetId}",
            "/user/{userId}/pages/{pageId}/retweet/{retweetId}",
            "/message/{messageId}/retweet/{retweetId}",
            "/message/{messageId}/pages/{pageId}/retweet/{retweetId}"
    })
    public String retweet(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                          @PathVariable("retweetId") Long retweetId,
                          @PathVariable("messageId") Optional<Long> messageId,
                          @PathVariable("userId") Optional<Long> userId,
                          @PathVariable("pageId") Optional<Integer> pageId) {
        String url = messageId.map(message_Id -> "/message/" + message_Id).orElse("") + userId.map(user_id -> "/user/" + user_id).orElse("") + pageId.map(page_id -> "/pages/" + page_id).orElse("");
        if (user != null && !user.getUsername().equals(messageService.findById(retweetId).get().getUser().getEmail())) {
            Message message = new Message();
            Message oldMessage = messageService.findById(retweetId).get();
            message.setText(oldMessage.getText());
            message.setDate(new Date());
            message.setUser(userService.findByEmail(user.getUsername()));
            message.setRetweet(oldMessage.getId());
            messageService.save(message);
        }
        return "redirect:" + (url.isEmpty() ? "/" : url);
    }

    @GetMapping({"/message/{messageId}", "/message/{messageId}/edit/{editId}",
            "/message/{messageId}/pages/{pageId}", "/message/{messageId}/pages/{pageId}/edit/{editId}"})
    public String getMessage(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                             @PathVariable("messageId") Long messageId,
                             @PathVariable("pageId") Optional<Integer> pageId,
                             @PathVariable("editId") Optional<Long> editId) {
        Optional<Message> optionalMessage = messageService.findById(messageId);
        if (optionalMessage.isPresent()) {
            model.addAttribute("currentMessage", optionalMessage.get());
            String url = "/message/" + messageId + pageId.map(page_id -> "/pages/" + page_id).orElse("");

            if (fillForm(model, editId)) return "redirect:" + url;

            model.addAttribute("currentUrl", url);
            model.addAttribute("pagePath", "/message/" + messageId + "/pages/");
            Page<Message> page = messageService.findAllByThread(optionalMessage.get(), pageId.orElse(1));
            makePage(page, model);

            List<Long> userLikes = getUserLikes(user, page.getContent());
            getUserLikes(user, userLikes, optionalMessage.get());
            if (optionalMessage.get().getParentId() != null) {
                Optional<Message> parent = messageService.findById(optionalMessage.get().getParentId());
                parent.ifPresent(message -> model.addAttribute("parentMessage", message));
                getUserLikes(user, userLikes, parent.get());
            }
            model.addAttribute("liked", userLikes);
            return "message";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping({"/message/{messageId}", "/message/{messageId}/edit/{editId}",
            "/message/{messageId}/pages/{pageId}", "/message/{messageId}/pages/{pageId}/edit/{editId}"})
    public String postMessage(Model model, @ModelAttribute("messageForm") Message message,
                              @AuthenticationPrincipal org.springframework.security.core.userdetails.User user,
                              @PathVariable("messageId") Long messageId,
                              @PathVariable("pageId") Optional<Integer> pageId,
                              @PathVariable("editId") Optional<Long> editId, BindingResult bindingResult) {
        Optional<Message> optionalMessage = messageService.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message thread = optionalMessage.get();
            messageValidator.validate(message, bindingResult);

            model.addAttribute("currentMessage", thread);
            String url = "/message/" + messageId + pageId.map(page_id -> "/pages/" + page_id).orElse("");

            if (!bindingResult.hasErrors()) {
                message.setUser(userService.findByEmail(user.getUsername()));

                //todo привести в нормальный вид
                if (thread.getThread() == null) {
                    // задаём настройки новому топику с ответами
                    message.setThread(thread.getId());
                    thread.setThread(thread.getId());
                    thread.setLvl(0l);
                    thread.setLft(1l);
                    thread.setRgt(2l);
                    messageService.save(thread);
                } else {
                    message.setThread(thread.getThread());
                }
                // указываем сообщение, как ответ
                message.setParentId(thread.getId());


                if (editId.isPresent()) {
                    // для редактируемого сообщениях сохраняем атрибуты дерева
                    Optional<Message> byId = messageService.findById(message.getId());
                    if (byId.isPresent()) {
                        message.setLvl(byId.get().getLvl());
                        message.setLft(byId.get().getLft());
                        message.setRgt(byId.get().getRgt());
                    }
                    messageService.save(message);
                    return "redirect:" + url;
                } else {
                    // для нового сообщениях - обновляем дерево
                    message.setDate(new Date());
                    messageService.saveReply(message, thread);
                    model.addAttribute("messageForm", new Message());
                }
            }

            model.addAttribute("currentUrl", url);
            model.addAttribute("pagePath", "/message/" + messageId + "/pages/");
            Page<Message> page = messageService.findAllByThread(messageService.findById(messageId).get(), pageId.orElse(1));
            makePage(page, model);

            List<Long> userLikes = getUserLikes(user, page.getContent());
            getUserLikes(user, userLikes, optionalMessage.get());
            if (thread.getParentId() != null) {
                Optional<Message> parent = messageService.findById(thread.getParentId());
                parent.ifPresent(message1 -> model.addAttribute("parentMessage", message1));
                getUserLikes(user, userLikes, parent.get());
            }
            model.addAttribute("liked", userLikes);
            return "message";
        } else {
            return "redirect:/";
        }
    }

    private void makePage(Page page, Model model) {
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

    private List<Long> getUserLikes(org.springframework.security.core.userdetails.User user, List<Message> content) {
        List<Long> result = new ArrayList<>();
        if (user != null) {
            for (Message msg : content) {
                for (Like like : msg.getLikes()) {
                    if (like.getUser().equals(user.getUsername())) {
                        result.add(like.getMessage());
                    }
                }
            }
        }
        return result;
    }

    private List<Long> getUserLikes(org.springframework.security.core.userdetails.User user, List<Long> likes, Message message) {
        if (user != null) {
            for (Like like : message.getLikes()) {
                if (like.getUser().equals(user.getUsername())) {
                    likes.add(like.getMessage());
                }
            }
        }
        return likes;
    }

    private boolean fillForm(Model model, Optional<Long> editId) {
        if (editId.isPresent()) {
            Optional<Message> editMessage = messageService.findById(editId.get());
            if (!editMessage.isPresent()) return true;
            model.addAttribute("messageForm", editMessage.get());
            model.addAttribute("editMode", true);
        } else {
            model.addAttribute("messageForm", new Message());
        }
        return false;
    }
}
