package ru.yandex.practicum.filmorate.service;

import java.util.Optional;

public abstract class AbstractService<T> implements FindAbstractService<T> {

    public abstract Optional<T> save(T t);

    public abstract Optional<T> update(T t);

    public abstract void delete(Long id);
}