package ru.yandex.practicum.filmorate.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.FilmDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "classpath:data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class FilmStorageTests {
    private final FilmDao filmDao;

    @Autowired
    public FilmStorageTests(@Qualifier("filmRepositoryImpl") FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @Test
    void shouldFindFilmById() {
        Optional<Film> filmOptional = filmDao.findById(1L);
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film -> assertThat(film).hasFieldOrPropertyWithValue("id", 1L));
    }

    @Test
    void shouldCreateFilm() {
        Film newFilm = createNewFilm();
        filmDao.save(newFilm);
        assertThat(filmDao.findAll()).contains(newFilm);
    }

    @Test
    void shouldDeleteFilm() {
        filmDao.delete(1L);
        assertThat(filmDao.findAll().size()).isEqualTo(2);
    }

    @Test
    void shouldFindAllFilms() {
        assertThat(filmDao.findAll().size()).isEqualTo(3);
    }

    @Test
    void shouldUpdateFilm() {
        Film updatedFilm = createUpdatedFilm();
        filmDao.update(updatedFilm);
        assertThat(updatedFilm.getDescription())
                .isEqualTo(filmDao.findById(2L).get().getDescription());
    }

    @Test
    void shouldFindPopularFilms() {
        Optional<Film> film01 = filmDao.findById(1L);
        Optional<Film> film02 = filmDao.findById(2L);
        filmDao.addLike(2, 1);
        assertThat(film02.get()).
                isEqualTo(filmDao.getSortedFilmsByLikes(10L)
                        .stream().findFirst().orElse(null));
        filmDao.addLike(1, 3);
        filmDao.addLike(1, 2);
        assertThat(film01.get()).
                isEqualTo(filmDao.getSortedFilmsByLikes(10L)
                        .stream().findFirst().orElse(null));
        List<Film> twoMostPopularFilms = filmDao.getSortedFilmsByLikes(2L);
        assertThat(twoMostPopularFilms).hasSize(2);
    }

    @Test
    void shouldAddFilmLikeAndRemoveFilmLike() {
        filmDao.addLike(1, 1);
        assertThat(filmDao.findById(1L).get().getLikes().size()).isEqualTo(1);
        filmDao.deleteLike(1, 1);
        assertThat(filmDao.findById(1L).get().getLikes().size()).isEqualTo(0);
    }

    private Film createNewFilm() {
        return Film.builder()
                .id(4L)
                .name("new_film_name")
                .description("new_film_description")
                .duration(120L)
                .releaseDate(LocalDate.of(1993, 1, 29))
                .mpa(new Rating(1, "G"))
                .genres(new ArrayList<>())
                .build();
    }

    private Film createUpdatedFilm() {
        return Film.builder()
                .id(2L)
                .name("updated_film_name")
                .description("updated_film_description")
                .duration(120L)
                .releaseDate(LocalDate.of(1990, 1, 29))
                .mpa(new Rating(1, "G"))
                .genres(new ArrayList<>())
                .build();
    }
}