package ru.yandex.practicum.filmorate.controllers.impl;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.controllers.AbstractController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.impl.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/films")
public class FilmController implements AbstractController<Film> {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @Override
    @GetMapping("/{filmId}")
    public Optional<Film> findById(@PathVariable("filmId") @Min(0) Long filmId) {
        return filmService.findById(filmId);
    }

    @Override
    @PostMapping
    public Optional<Film> save(@RequestBody @Valid Film film) {
        return filmService.save(film);
    }

    @Override
    @PutMapping
    public Optional<Film> update(@RequestBody @Valid Film film) {
        return filmService.update(film);
    }

    @Override
    @DeleteMapping("/{filmId}")
    public void delete(@PathVariable("filmId") @Min(0) Long filmId) {
        filmService.delete(filmId);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") @Min(0) Long filmId,
                        @PathVariable("userId") @Min(0) Long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") @Min(0) Long filmId,
                           @PathVariable("userId") @Min(0) Long userId) {
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getSortedFilmsByLikes(@RequestParam(value = "count", required = false, defaultValue = "10")
                                            @Min(1) Long count) {
        return filmService.getSortedFilmsByLikes(count);
    }
}