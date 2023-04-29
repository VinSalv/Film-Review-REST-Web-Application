DROP DATABASE IF EXISTS Film_Db;
CREATE DATABASE Film_Db;
USE Film_Db;

DROP TABLE IF EXISTS feedback CASCADE;
DROP TABLE IF EXISTS film CASCADE;
DROP TABLE IF EXISTS actor CASCADE;
DROP TABLE IF EXISTS cast CASCADE;

CREATE TABLE film
(
    id_film INT AUTO_INCREMENT PRIMARY KEY,
    title   VARCHAR(1000),
    genre   VARCHAR(50),
    plot    VARCHAR(5000),
    trailer VARCHAR(500),
    poster  LONGBLOB
);

CREATE TABLE feedback
(
    id_film INT NOT NULL,
    id_user INT NOT NULL,
    score   FLOAT CHECK (score >= 0 and score <= 5),
    comment VARCHAR(5000),
    date    DATE,
    PRIMARY KEY (id_film, id_user),
    CONSTRAINT FK_film_feedback FOREIGN KEY (id_film) REFERENCES FILM (id_film) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE actor
(
    id_actor INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100),
    surname  VARCHAR(100)
);

CREATE TABLE cast
(
    id_film  INT NOT NULL,
    id_actor INT NOT NULL,
    PRIMARY KEY (id_film, id_actor),
    CONSTRAINT FK_film_cast FOREIGN KEY (id_film) REFERENCES film (id_film) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_actor_feedback FOREIGN KEY (id_actor) REFERENCES actor (id_actor) ON UPDATE CASCADE ON DELETE CASCADE
);