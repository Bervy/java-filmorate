package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        log.debug("Request received for all users");
        return userService.getUserStorage().findAll();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") long userId) {
        log.debug("Request received for get user with id {}", userId);
        return userService.getUserStorage().getUser(userId);
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        log.info("Request received for create user {}", user.getName());
        return userService.getUserStorage().create(user);
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        log.info("Request received for update user {}", user.getName());
        return userService.getUserStorage().update(user);
    }

    @DeleteMapping
    public User delete(@RequestBody @Valid User user) {
        log.info("Request received for delete user {}", user.getName());
        return userService.getUserStorage().delete(user);
    }


    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") @Min(0)long userId,
                          @PathVariable("friendId") @Min(0)long friendId) {
        log.info("Request received for add friend to user with id {}", userId);
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") @Min(0)long userId,
                             @PathVariable("friendId") @Min(0)long friendId) {
        log.info("Request received for delete friend from user with id {}", userId);
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable("id") @Min(0)long userId) {
        log.info("Request received for get friends from user with id {}", userId);
        return userService.getFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") @Min(0)long userId,
                                       @PathVariable("otherId") @Min(0)long friendId) {
        log.info("Request received for get common friends  user with id {} " +
                "and another user with id {}", userId, friendId);
        return userService.getCommonFriends(userId, friendId);
    }
}