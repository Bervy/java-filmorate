package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.validators.UserNameValidator;
import ru.yandex.practicum.filmorate.repository.FriendshipDao;
import ru.yandex.practicum.filmorate.repository.UserDao;
import ru.yandex.practicum.filmorate.service.AbstractService;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.*;

@Service
@Slf4j
public class UserServiceImpl extends AbstractService<User> {

    private UserDao userDao;
    private FriendshipDao friendshipDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userDao.findById(userId);
    }

    @Override
    public Optional<User> save(User user) {
        UserNameValidator.validate(user);
        return userDao.save(user);
    }

    @Override
    public Optional<User> update(User user) {
        userValidation(user);
        return userDao.update(user);
    }

    @Override
    public void delete(Long userId) {
        userIdExistsValidation(userId);
        userDao.delete(userId);
    }

    public void addFriend(Long userId, Long friendId) {
        userIdExistsValidation(userId);
        userIdExistsValidation(friendId);
        friendshipDao.addFriend(userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        userIdExistsValidation(userId);
        userIdExistsValidation(friendId);
        friendshipDao.deleteFriend(userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        userIdExistsValidation(userId);
        return friendshipDao.getFriends(userId);
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        userIdExistsValidation(userId);
        userIdExistsValidation(friendId);
        return friendshipDao.getCommonFriends(userId, friendId);
    }


    private void userValidation(User user) {
        if (user.getId() < 0 || userDao.findById(user.getId()).isEmpty()) {
            throw new FilmorateNotFoundException(String.format(
                    USER_NOT_FOUND.getTitle(), user.getId()));
        }
        if (user.getBirthday() == null) {
            throw new ValidationException(EMPTY_BIRTHDAY.getTitle());
        }
    }

    private void userIdExistsValidation(Long userId) {
        if (userDao.findById(userId).isEmpty()) {
            throw new FilmorateNotFoundException(String.format(
                    USER_NOT_FOUND.getTitle(), userId));
        }
    }

    @Autowired
    @Qualifier("userRepositoryImpl")
    public void setUserDao(UserDao userStorage) {
        this.userDao = userStorage;
    }

    @Autowired
    @Qualifier("friendshipRepositoryImpl")
    public void setFriendshipDao(FriendshipDao friendshipDao) {
        this.friendshipDao = friendshipDao;
    }
}