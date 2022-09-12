package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreDao;

import java.util.List;

import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.WRONG_GENRE_ID;

@Service
public class GenreService {
    private final GenreDao genreDao;

    @Autowired
    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Genre findGenreById(long genreId) {
        if (genreId < 0) {
            throw new FilmorateNotFoundException(WRONG_GENRE_ID.getTitle());
        }
        return genreDao.getGenreById(genreId);
    }

    public List<Genre> findAll() {
        return genreDao.getGenres();
    }
}