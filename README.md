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

ORDER BY COUNT(fl.user_id) DESC

LIMIT 10;

4) Get friends of user with id 3

SELECT *
FROM friendship
WHERE user_id = 3 
AND state_of_friendship = true

5) Get all users

SELECT *

FROM users;

6) Get all films

SELECT *

FROM films;

7) Get common friends of two users
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