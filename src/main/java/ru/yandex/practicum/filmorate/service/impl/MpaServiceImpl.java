package ru.yandex.practicum.filmorate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.RatingDao;
import ru.yandex.practicum.filmorate.service.FindAbstractService;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.RATING_NOT_FOUND;


@Service
public class MpaServiceImpl implements FindAbstractService<Rating> {
    private final RatingDao ratingDao;

    @Autowired
    public MpaServiceImpl(@Qualifier("ratingRepositoryImpl") RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Override
    public Optional<Rating> findById(Long ratingId) {
        if (ratingId < 0) {
            throw new FilmorateNotFoundException(RATING_NOT_FOUND.getTitle());
        }
        return ratingDao.getRatingById(ratingId);
    }

    @Override
    public List<Rating> findAll() {
        return ratingDao.getAllRatings();
    }
}