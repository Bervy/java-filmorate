package ru.yandex.practicum.filmorate.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserDao;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "classpath:data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserStorageTests {
    private final UserDao userDao;

    @Autowired
    public UserStorageTests(@Qualifier("userImpl") UserDao userDao) {
        this.userDao = userDao;
    }

    @Test
    void shouldFindUserById() {
        Optional<User> userOptional = userDao.findById(1L);
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user -> assertThat(user).hasFieldOrPropertyWithValue("id", 1L));
    }

    @Test
    void shouldCreateUser() {
        User newUser = createNewUser();
        userDao.save(newUser);
        assertThat(userDao.findAll()).contains(newUser);
    }

    @Test
    void shouldDeleteUser() {
        userDao.delete(1L);
        assertThat(userDao.findAll().size()).isEqualTo(2);
    }

    @Test
    void shouldFindAllUsers() {
        assertThat(userDao.findAll().size()).isEqualTo(3);
    }

    @Test
    void shouldUpdateUser() {
        User updatedUser = createUpdatedUser();
        userDao.update(updatedUser);
        assertThat(updatedUser.getName())
                .isEqualTo(userDao.findById(1L).get().getName());
    }

    private User createNewUser() {
        return User.builder().
                name("new_user_name").
                login("new_user_login").
                email("new_user@mail.ru").
                birthday(LocalDate.of(1992, 2, 2))
                .build();
    }

    private User createUpdatedUser() {
        return User.builder().
                id(1L).
                name("updated_user_name").
                login("updated_user_login").
                email("updated_user_email").
                birthday(LocalDate.of(1990, 1, 29))
                .build();
    }
}