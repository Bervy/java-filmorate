package ru.yandex.practicum.filmorate.controllers.impl;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllers.AbstractController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.impl.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController implements AbstractController<User> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @Override
    @GetMapping("/{userId}")
    public Optional<User> findById(@PathVariable("userId") @Min(0) Long userId) {
        return userService.findById(userId);
    }

    @Override
    @PostMapping
    public Optional<User> save(@RequestBody @Valid User user) {
        return userService.save(user);
    }

    @Override
    @PutMapping
    public Optional<User> update(@RequestBody @Valid User user) {
        return userService.update(user);
    }

    @Override
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") @Min(0) Long userId) {
        userService.delete(userId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") @Min(0) Long userId,
                          @PathVariable("friendId") @Min(0) Long friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") @Min(0) Long userId,
                             @PathVariable("friendId") @Min(0) Long friendId) {
        userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable("id") @Min(0) Long userId) {
        return userService.getFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") @Min(0) Long userId,
                                       @PathVariable("otherId") @Min(0) Long friendId) {
        return userService.getCommonFriends(userId, friendId);
    }
}