package ru.yandex.practicum.filmorate.controllers;

import java.util.List;

public abstract class AbstractController<T> {

    public abstract List<T> findAll();

    public abstract T create(T t);

    public abstract T update(T t);

    public abstract T delete(T t);
}