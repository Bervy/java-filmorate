package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAll();

    Optional<User> save(User user);

    Optional<User> update(User user);

    void delete(Long id);

    Optional<User> findById(Long id);
}