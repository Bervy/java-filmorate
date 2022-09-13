package ru.yandex.practicum.filmorate.service;

import java.util.List;
import java.util.Optional;

public interface FindAbstractService<T> {

    List<T> findAll();

    Optional<T> findById(Long id);
}