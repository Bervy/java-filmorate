package ru.yandex.practicum.filmorate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.GenreDao;
import ru.yandex.practicum.filmorate.service.FindAbstractService;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.WRONG_GENRE_ID;

@Service
public class GenreServiceImpl implements FindAbstractService<Genre> {
    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(@Qualifier("genreRepositoryImpl") GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Optional<Genre> findById(Long genreId) {
        if (genreId < 0) {
            throw new FilmorateNotFoundException(WRONG_GENRE_ID.getTitle());
        }
        return genreDao.getGenreById(genreId);
    }

    @Override
    public List<Genre> findAll() {
        return genreDao.getGenres();
    }
}