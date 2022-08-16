package ru.yandex.practicum.filmorate.controllers;

import java.util.List;

public interface AbstractController<T> {

    List<T> findAll();

    T create(T t);

    T update(T t);

    T delete(T t);
}