package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class FilmControllerTest {
    private static final String URL = "/films";
    private static final LocalDate VALID_DATE = LocalDate.of(2009, 12, 10);
    private static final LocalDate INVALID_DATE = LocalDate.of(1895, 12, 27);
    private static final String VALID_NAME = "Avatar";
    private static final String VALID_DESCRIPTION = "Na'Vi";
    private static final String VALID_DESCRIPTION_SIZE_200 = "qwertyuiopqwertyuiopqwertyuiopq" +
            "wertyuiopqwertyuiopqwertyuiopqwertyuiop" +
            "qwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiop" +
            "qwertyuiopqwertyuiopqwertyuiopqwertyuiop";
    private static final String INVALID_DESCRIPTION_SIZE_201 = "qwertyuiopqwertyuiopqwerty" +
            "uiopqwertyuiopqwertyuiopqwertyuiopqwertyuiop" +
            "qwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiop" +
            "qwertyuiopqwertyuiopqwertyuiopqwertyuiopp";
    private static final long VALID_DURATION = 162L;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldReturnStatusOkWhenCreateValidFilm() throws Exception {
        Film newFilm = new Film(1L, VALID_NAME, VALID_DESCRIPTION, VALID_DURATION, VALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(newFilm);
        this.mockMvc.perform(post(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatusOkWhenCreateValidFilmWithDescriptionSize200() throws Exception {
        Film newFilm = new Film(1L, VALID_NAME, VALID_DESCRIPTION_SIZE_200, VALID_DURATION, VALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(newFilm);
        this.mockMvc.perform(post(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatus4xxWhenCreateValidFilmWithDescriptionSize201() throws Exception {
        Film newFilm = new Film(1L, VALID_NAME, INVALID_DESCRIPTION_SIZE_201, VALID_DURATION, VALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(newFilm);
        this.mockMvc.perform(post(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnStatus4xxWhenCreateFilmWithEmptyName() throws Exception {
        Film newFilm = new Film(1L, " ", VALID_DESCRIPTION, VALID_DURATION, VALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(newFilm);
        this.mockMvc.perform(post(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnStatus4xxWhenCreateFilmWithNegativeDuration() throws Exception {
        Film newFilm = new Film(1L, VALID_NAME, VALID_DESCRIPTION, -162L, VALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(newFilm);
        this.mockMvc.perform(post(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnStatusOkWhenCreateFilmWithValidReleaseDate() throws Exception {
        Film newFilm = new Film(1L, VALID_NAME, VALID_DESCRIPTION, VALID_DURATION,
                LocalDate.of(1895, 12, 28),
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(newFilm);
        this.mockMvc.perform(post(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatus4xxWhenCreateFilmWithInvalidReleaseDate() throws Exception {
        Film newFilm = new Film(1L, VALID_NAME, VALID_DESCRIPTION, VALID_DURATION, INVALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(newFilm);
        this.mockMvc.perform(post(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnStatusOkWhenUpdateValidFilm() throws Exception {
        createFilm();
        Film updatedFilm = new Film(1L, VALID_NAME, "Island", VALID_DURATION, VALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String updatedBody = mapper.writeValueAsString(updatedFilm);
        this.mockMvc.perform(put(URL).content(updatedBody).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatusOkWhenUpdateValidFilmWithDescriptionSize200() throws Exception {
        createFilm();
        Film updatedFilm = new Film(1L, VALID_NAME, VALID_DESCRIPTION_SIZE_200, VALID_DURATION, VALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(updatedFilm);
        this.mockMvc.perform(put(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatus4xxWhenUpdateValidFilmWithDescriptionSize201() throws Exception {
        createFilm();
        Film updatedFilm = new Film(1L, VALID_NAME, INVALID_DESCRIPTION_SIZE_201, VALID_DURATION, VALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(updatedFilm);
        this.mockMvc.perform(put(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnStatus4xxWhenUpdateFilmWithEmptyName() throws Exception {
        createFilm();
        Film updatedFilm = new Film(1L, " ", "Island", VALID_DURATION, VALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(updatedFilm);
        this.mockMvc.perform(put(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnStatus4xxWhenUpdateFilmWithNegativeDuration() throws Exception {
        createFilm();
        Film updatedFilm = new Film(1L, VALID_NAME, "Island", -162L, VALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(updatedFilm);
        this.mockMvc.perform(post(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnStatusOkWhenUpdateFilmWithValidReleaseDate() throws Exception {
        createFilm();
        Film updatedFilm = new Film(1L, VALID_NAME, "Island", VALID_DURATION,
                LocalDate.of(1895, 12, 28),
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(updatedFilm);
        this.mockMvc.perform(post(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatus4xxWhenUpdateFilmWithInvalidReleaseDate() throws Exception {
        createFilm();
        Film updatedFilm = new Film(1L, VALID_NAME, "Island", VALID_DURATION, INVALID_DATE,
                new Rating(1, "123"), new ArrayList<>());
        String body = mapper.writeValueAsString(updatedFilm);
        this.mockMvc.perform(post(URL).content(body).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().is4xxClientError());
    }

    private void createFilm() throws Exception {
        String newBody = mapper.writeValueAsString(new Film(1L, VALID_NAME, VALID_DESCRIPTION, VALID_DURATION, VALID_DATE,
                new Rating(1, "123"), new ArrayList<>()));
        this.mockMvc.perform(post(URL).content(newBody).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }
}