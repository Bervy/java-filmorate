package ru.yandex.practicum.filmorate.exceptions;

public class FilmorateAlreadyExistsException extends RuntimeException {

    public FilmorateAlreadyExistsException(String message) {
        super(message);
    }
}