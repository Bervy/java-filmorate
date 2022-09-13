package ru.yandex.practicum.filmorate.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.controllers.FindAbstractController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.impl.GenreServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genres")
public class GenreController implements FindAbstractController<Genre> {
    private final GenreServiceImpl genreService;

    @Autowired
    public GenreController(GenreServiceImpl genreService) {
        this.genreService = genreService;
    }

    @Override
    @GetMapping
    public List<Genre> findAll() {
        return genreService.findAll();
    }

    @Override
    @GetMapping("/{genreId}")
    public Optional<Genre> findById(@PathVariable Long genreId) {
        return genreService.findById(genreId);
    }
}