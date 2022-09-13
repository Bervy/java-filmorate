package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.FilmGenreMapper;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.repository.sqloperations.GenreSqlOperations.*;

@Repository
public class GenreRepositoryImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    public GenreRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getGenres() {
        return jdbcTemplate.query(GET_ALL_GENRES.getTitle(), new GenreMapper());
    }

    @Override
    public Optional<Genre> getGenreById(Long genreId) {
        return Optional.ofNullable
                (jdbcTemplate.queryForObject(GET_GENRE_BY_GENRE_ID.getTitle(), new GenreMapper(), genreId));
    }

    @Override
    public List<Genre> getGenresByFilmId(Long filmId) {
        return jdbcTemplate.query(GET_GENRES_BY_FILM_ID.getTitle(), new GenreMapper(), filmId);
    }

    @Override
    public List<Genre> getGenresIdByFilmId(Long filmId) {
        return jdbcTemplate.query(GET_GENRES_ID_BY_FILM_ID.getTitle(), new FilmGenreMapper(), filmId);
    }
}