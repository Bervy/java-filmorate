package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private static final String USER_ALREADY_EXISTS = "User with id %d is already friends" +
            "with user id %d";
    private static final String USER_NOT_FOUND = "Wrong user id or friend id";
    private static final String USER_DONT_EXISTS_ANOTHER_USER = "User with id %d dont have friend " +
            "with id %d";

    UserStorage userStorage;

    public void addFriend(long userId, long friendId) {
        User user = getValidUser(userId);
        User friend = getValidUser(friendId);
        if (isUserExistsAnotherUser(user, friendId)) {
            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS, friendId, userId));
        } else {
            user.getFriends().add(friendId);
            friend.getFriends().add(userId);
            log.info("User with id {} added to friends user with id {}", userId, friendId);
        }
    }

    public void deleteFriend(long userId, long friendId) {
        User user = getValidUser(userId);
        User friend = getValidUser(friendId);
        if (isUserExistsAnotherUser(user, friendId)) {
            user.getFriends().remove(friendId);
            friend.getFriends().remove(userId);
            log.info("User with id {} deleted friend user with id {}", userId, friendId);
        } else {
            throw new UserNotFoundException(String.format(USER_DONT_EXISTS_ANOTHER_USER, userId, friendId));
        }
    }

    public List<User> getFriends(long userId) {
        User user = getValidUser(userId);
        List<User> userFriends = new ArrayList<>();
        for (Long friendId : user.getFriends()) {
            userFriends.add(userStorage.getUsers().get(friendId));
        }
        return userFriends;
    }

    public List<User> getCommonFriends(long userId, long friendId) {
        Set<Long> userFriendsIds = getValidUserIds(userId);
        Set<Long> anotherUserFriendsIds = getValidUserIds(friendId);
        if (userFriendsIds.isEmpty() || anotherUserFriendsIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> userCommonFriendsIds = userFriendsIds.stream()
                .filter(anotherUserFriendsIds::contains)
                .collect(Collectors.toList());
        List<User> userFriends = new ArrayList<>();
        for (Long id : userCommonFriendsIds) {
            userFriends.add(userStorage.getUsers().get(id));
        }
        return userFriends;
    }

    private boolean isUserExists(long userId) {
        return userStorage.getUsers().containsKey(userId);
    }

    private boolean isUserExistsAnotherUser(User user, long anotherUser) {
        return user.getFriends().contains(anotherUser);
    }

    private User getValidUser(long userId) {
        if (isUserExists(userId)) {
            return userStorage.getUsers().get(userId);
        }
        throw new UserNotFoundException(USER_NOT_FOUND);
    }

    private Set<Long> getValidUserIds(Long userId) {
        return getValidUser(userId).getFriends();
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    @Autowired()
    public void setUserStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }
}