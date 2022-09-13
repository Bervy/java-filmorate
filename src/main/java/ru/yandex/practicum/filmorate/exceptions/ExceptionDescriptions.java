package ru.yandex.practicum.filmorate.exceptions;


public enum ExceptionDescriptions {
    FILM_NOT_FOUND("The movie with id %d not found"),
    USER_NOT_FOUND("The user with id %d not found"),
    EMPTY_RATING("Rating not specified"),
    EMPTY_RELEASE_DATE("Release date not specified"),
    EMPTY_BIRTHDAY("Birthday not specified"),
    RATING_NOT_FOUND("Rating not found"),
    WRONG_POPULAR_FILMS_COUNT("Invalid popular movies counter"),
    WRONG_GENRE_ID("Wrong genre id"),
    GENRE_NOT_FOUND("Genre not found"),
    USER_ALREADY_LIKED("User already liked this film"),
    FRIEND_ALREADY_EXISTS("Friend already exists");

    private final String title;

    ExceptionDescriptions(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}