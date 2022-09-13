package ru.yandex.practicum.filmorate.controllers;

import java.util.List;
import java.util.Optional;

public interface FindAbstractController<T> {

    List<T> findAll();

    Optional<T> findById(Long id);
}