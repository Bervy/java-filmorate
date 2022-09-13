package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mapper.RatingMapper;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.RatingDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.repository.sqloperations.RatingSqlOperations.GET_ALL_RATINGS;
import static ru.yandex.practicum.filmorate.repository.sqloperations.RatingSqlOperations.GET_RATING_BY_RATING_ID;

@Repository
public class RatingRepositoryImpl implements RatingDao {

    private final JdbcTemplate jdbcTemplate;

    public RatingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Rating> getAllRatings() {
        return jdbcTemplate.query(GET_ALL_RATINGS.getTitle(), new RatingMapper());
    }

    @Override
    public Optional<Rating> getRatingById(Long ratingId) {
        return Optional.ofNullable
                (jdbcTemplate.queryForObject(GET_RATING_BY_RATING_ID.getTitle(), new RatingMapper(), ratingId));
    }
}