package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserDao;

import java.util.List;
import java.util.Optional;

import static ru.yandex.practicum.filmorate.exceptions.ExceptionDescriptions.USER_NOT_FOUND;
import static ru.yandex.practicum.filmorate.repository.sqloperations.UserSqlOperations.*;

@Repository
public class UserRepositoryImpl implements UserDao {

    private static final String USER_TABLE_NAME = "users";
    private static final String USER_TABLE_ID_COLUMN_NAME = "user_id";
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(GET_ALL_USERS.getTitle(), new UserMapper());
    }

    @Override
    public Optional<User> save(User user) {
        userInsertAndSetId(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> update(User user) {
        jdbcTemplate.update(UPDATE_USER.getTitle(),
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        return Optional.of(user);
    }

    @Override
    public void delete(Long userId) {
        try {
            jdbcTemplate.update(DELETE_FILM_LIKE_BY_USER_ID.getTitle(), userId);
            jdbcTemplate.update(DELETE_FRIENDSHIP_BY_USER_ID.getTitle(), userId);
            jdbcTemplate.update(DELETE_USER.getTitle(), userId);
        } catch (DataAccessException e) {
            throw new FilmorateNotFoundException(USER_NOT_FOUND.getTitle() + e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(Long userId) {
        try {
            return Optional.ofNullable(jdbcTemplate.
                    queryForObject(GET_USER_BY_USER_ID.getTitle(), new UserMapper(), userId));
        } catch (DataAccessException e) {
            throw new FilmorateNotFoundException(USER_NOT_FOUND.getTitle() + e.getMessage());
        }
    }

    private SimpleJdbcInsert getUserSimpleJdbcInsert() {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(USER_TABLE_NAME)
                .usingGeneratedKeyColumns(USER_TABLE_ID_COLUMN_NAME);
    }

    private void userInsertAndSetId(User user) {
        SimpleJdbcInsert simpleJdbcInsert = getUserSimpleJdbcInsert();
        long userId = simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue();
        user.setId(userId);
    }
}