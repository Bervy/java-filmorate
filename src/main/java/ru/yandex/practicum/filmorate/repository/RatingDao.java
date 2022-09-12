package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

public interface RatingDao {

    List<Rating> getAllRatings();

    Rating getRatingById(long ratingId);
}