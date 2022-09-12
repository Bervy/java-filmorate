package ru.yandex.practicum.filmorate.repository.sqloperations;

public enum RatingSqlOperations {

    GET_ALL_RATINGS("""
            SELECT *
            FROM rating"""),
    GET_RATING_BY_RATING_ID("""
            SELECT *
            FROM rating
            WHERE mpa_id = ?""");

    private final String title;

    RatingSqlOperations(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}