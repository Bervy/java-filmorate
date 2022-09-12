package ru.yandex.practicum.filmorate.controllers;

import java.util.List;
import java.util.Optional;

public interface AbstractController<T> {

    List<T> findAll();

    Optional<T> findById(Long id);

    Optional<T> save(T t);

    Optional<T> update(T t);

    void delete(Long id);
}