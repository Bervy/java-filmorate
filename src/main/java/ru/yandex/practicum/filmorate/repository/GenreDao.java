package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getGenres();

    Genre getGenreById(long genreId);

    List<Genre> getGenresByFilmId(long filmId);

    List<Genre> getGenresIdByFilmId(long filmId);
}