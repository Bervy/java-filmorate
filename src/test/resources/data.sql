DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS rating CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS friendship CASCADE;
DROP TABLE IF EXISTS film_like CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS film_genre CASCADE;

CREATE TABLE rating
(
    mpa_id BIGSERIAL PRIMARY KEY,
    mpa_name VARCHAR(100) NOT NULL
);

CREATE TABLE films
(
    film_id BIGSERIAL PRIMARY KEY,
    film_name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    duration INTEGER,
    release_date DATE,
    mpa_id INTEGER,
    FOREIGN KEY(mpa_id) REFERENCES rating ON DELETE CASCADE
);

CREATE TABLE users
(
    user_id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    login VARCHAR(100),
    email VARCHAR(100),
    birthday DATE
);

CREATE TABLE IF NOT EXISTS friendship
(
    user_id INTEGER NOT NULL,
    friend_id INTEGER NOT NULL,
    state_of_friendship BOOLEAN NOT NULL,
    PRIMARY KEY(user_id, friend_id),
    FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY(friend_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE film_like
(
    film_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    PRIMARY KEY(film_id, user_id),
    FOREIGN KEY(film_id) REFERENCES films(film_id) ON DELETE CASCADE,
    FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE genre
(
    genre_id BIGSERIAL PRIMARY KEY,
    genre_name VARCHAR(100) NOT NULL
);

CREATE TABLE film_genre
(
    film_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL,
    PRIMARY KEY(film_id, genre_id),
    FOREIGN KEY(film_id) REFERENCES films(film_id) ON DELETE CASCADE,
    FOREIGN KEY(genre_id) REFERENCES genre(genre_id) ON DELETE CASCADE
);

MERGE INTO PUBLIC.genre (genre_id, genre_name) VALUES ( 1, 'Комедия' );
MERGE INTO PUBLIC.genre (genre_id, genre_name) VALUES ( 2, 'Драма' );
MERGE INTO PUBLIC.genre (genre_id, genre_name) VALUES ( 3, 'Мультфильм' );
MERGE INTO PUBLIC.genre (genre_id, genre_name) VALUES ( 4, 'Триллер' );
MERGE INTO PUBLIC.genre (genre_id, genre_name) VALUES ( 5, 'Документальный' );
MERGE INTO PUBLIC.genre (genre_id, genre_name) VALUES ( 6, 'Боевик' );

MERGE INTO PUBLIC.rating (mpa_id, mpa_name) VALUES ( 1, 'G' );
MERGE INTO PUBLIC.rating (mpa_id, mpa_name) VALUES ( 2, 'PG' );
MERGE INTO PUBLIC.rating (mpa_id, mpa_name) VALUES ( 3, 'PG-13' );
MERGE INTO PUBLIC.rating (mpa_id, mpa_name) VALUES ( 4, 'R' );
MERGE INTO PUBLIC.rating (mpa_id, mpa_name) VALUES ( 5, 'NC-17' );

INSERT INTO users (user_name, login, email, birthday)
VALUES ('user01', 'usr01', 'user01@email', '1991-01-01');

INSERT INTO users (user_name, login, email, birthday)
VALUES ('user02', 'usr02', 'user02@email', '1992-01-01');

INSERT INTO users (user_name, login, email, birthday)
VALUES ('user03', 'usr03', 'user03@email', '1993-01-01');

INSERT INTO films (film_name, description, duration, release_date, mpa_id)
VALUES ('film_01_name', 'film_01_description', '3600', '2001-11-11',  1);

INSERT INTO films (film_name, description, duration, release_date, mpa_id)
VALUES ('film_02_name', 'film_02_description', '3700', '2002-11-11',  2);

INSERT INTO films (film_name, description, duration, release_date, mpa_id)
VALUES ('film_03_name', 'film_03_description', '3800', '2003-11-11',  3);