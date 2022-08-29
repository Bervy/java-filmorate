Database diagram
![DB Diagram](/diagram/filmorate_diagram_Osipov.png)

Basic request examples:

1) Get film with id 5

SELECT *

FROM films

WHERE film_id = 5;

2) Get user with id 2

SELECT *

FROM users

WHERE user_id = 2;

3) Get 10 most popular films

SELECT f.name AS most_popular_film_names

FROM films AS f

INNER JOIN film_likes AS fl ON fl.film_id=f.film_id

GROUP BY most_popular_film_names

ORDER BY COUNT(fl.user_id)

LIMIT 10;
