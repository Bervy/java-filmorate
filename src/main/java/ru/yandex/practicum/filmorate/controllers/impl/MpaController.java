package ru.yandex.practicum.filmorate.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.controllers.FindAbstractController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.impl.MpaServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mpa")
public class MpaController implements FindAbstractController<Rating> {
    private final MpaServiceImpl mpaService;

    @Autowired
    public MpaController(MpaServiceImpl mpaService) {
        this.mpaService = mpaService;
    }

    @Override
    @GetMapping
    public List<Rating> findAll() {
        return mpaService.findAll();
    }

    @Override
    @GetMapping("/{mpaId}")
    public Optional<Rating> findById(@PathVariable Long mpaId) {
        return mpaService.findById(mpaId);
    }
}