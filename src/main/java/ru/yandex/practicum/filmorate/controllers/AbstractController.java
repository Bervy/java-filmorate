package ru.yandex.practicum.filmorate.controllers;

import java.util.Optional;

public abstract class AbstractController<T> implements FindAbstractController<T> {

    public abstract Optional<T> save(T t);

    public abstract Optional<T> update(T t);

    public abstract void delete(Long id);
}