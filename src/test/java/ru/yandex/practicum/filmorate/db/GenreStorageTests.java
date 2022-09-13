package ru.yandex.practicum.filmorate.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.repository.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "classpath:data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GenreStorageTests {
    private final GenreDao genreDao;

    @Autowired
    public GenreStorageTests(@Qualifier("genreRepositoryImpl") GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Test
    void shouldFindGenreById() {
        Optional<Genre> genre01 = genreDao.getGenreById(1L);
        assertThat(genre01.get().getName()).isEqualTo("Комедия");
    }

    @Test
    void shouldFindAllGenres() {
        assertThat(genreDao.getGenres()).hasSize(6);
    }
}