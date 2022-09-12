package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
public
class Rating {
    private final long id;
    private String name;

    public Rating(long id, String name) {
        this.id = id;
        this.name = name;
    }
}