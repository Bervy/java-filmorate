package ru.yandex.practicum.filmorate.exceptions;


public enum ExceptionDescriptions {
    LIKE_NOT_FOUND("The movie with id %d dont have like from user with id %d"),
    LIKE_ALREADY_EXISTS("The movie with id %d dont have like from user with id %d"),
    FILM_NOT_FOUND("The movie with id %d not found"),
    USER_ALREADY_EXISTS("User with id %d is already friends with user id %d"),
    USER_OR_FRIEND_NOT_FOUND("Wrong user id or friend id"),
    USER_NOT_FOUND("The user with id %d not found"),
    USER_DONT_EXISTS_ANOTHER_USER("User with id %d dont have friend with id %d"),
    EMPTY_FILM_ID("Wrong film id"),
    EMPTY_USER_ID("Wrong user id"),
    EMPTY_RATING("Rating not specified"),
    EMPTY_RELEASE_DATE("Release date not specified"),
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