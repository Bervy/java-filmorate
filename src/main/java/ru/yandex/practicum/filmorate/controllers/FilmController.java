package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> findAll() {
        log.info("Request received for all movies");
        return filmService.getFilmStorage().findAll();
    }

    @GetMapping("/{filmId}")
    public Film getFilm(@PathVariable("filmId") Long filmId) {
        log.info("Request received for get film with id {}", filmId);
        return filmService.getFilmStorage().getFilm(filmId);
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        log.info("Request received for create film {}", film.getName());
        return filmService.getFilmStorage().create(film);
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        log.info("Request received for update film {}", film.getName());
        return filmService.getFilmStorage().update(film);
    }

    @DeleteMapping
    public Film delete(@RequestBody @Valid Film film) {
        log.info("Request received for delete film {}", film.getName());
        return filmService.getFilmStorage().delete(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") @Min(0) long filmId,
                        @PathVariable("userId") @Min(0) long userId) {
        log.info("Request received for add like to film with id {}", filmId);
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") @Min(0) long filmId,
                           @PathVariable("userId") @Min(0) long userId) {
        log.info("Request received for delete like from film with id {}", filmId);
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getSortedFilmsByLikes(@RequestParam(value = "count", required = false) Long count) {
        log.info("Request received for get sorted films by likes");
        return filmService.getSortedFilmsByLikes(count);
    }
}