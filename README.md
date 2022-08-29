#### Database diagram
![DB Diagram](/diagram/filmorate_diagram_Osipov.png)

#### Basic request examples:

- Get film with id 5
```` SQL
SELECT *
FROM films
WHERE film_id = 5;

- Get user with id 2
```` SQL
SELECT *
FROM users
WHERE user_id = 2;

- Get 10 most popular films
```` SQL
SELECT f.name AS most_popular_film_names
FROM films AS f
INNER JOIN film_likes AS fl ON fl.film_id=f.film_id
GROUP BY most_popular_film_names
ORDER BY COUNT(fl.user_id) DESC
LIMIT 10;

- Get friends of user with id 3
```` SQL
SELECT *
FROM friendship
WHERE user_id = 3 
AND state_of_friendship = true;

- Get all users
```` SQL
SELECT *
FROM users;

- Get all films
```` SQL
SELECT *
FROM films;

- Get common friends of two users
```` SQL
SELECT *
FROM users AS u
WHERE u.user_id IN (
(SELECT friend_id AS user_id FROM friendship WHERE user_id = 1 AND state_of_friendship IS TRUE)
UNION
(SELECT user_id AS user_id FROM friendship WHERE friend_id = 1 AND state_of_friendship IS TRUE)
)
AND u.user_id IN (   
(SELECT friend_id AS user_id FROM friendship WHERE user_id = 2 AND state_of_friendship IS TRUE)
UNION
(SELECT user_id AS user_id FROM friendship WHERE friend_id = 2 AND state_of_friendship IS TRUE) 
);
