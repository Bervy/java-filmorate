package ru.yandex.practicum.filmorate.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.repository.RatingDao;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "classpath:data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class RatingStorageTests {
    private final RatingDao ratingDao;

    @Autowired
    public RatingStorageTests(@Qualifier("ratingImpl") RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Test
    void shouldFindMpaById() {
        String mpa01Name = ratingDao.getRatingById(1L).getName();
        assertThat(mpa01Name).isEqualTo("G");
    }

    @Test
    void shouldFindAllMpa() {
        assertThat(ratingDao.getAllRatings()).hasSize(5);
    }
}