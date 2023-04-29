DROP DATABASE IF EXISTS User_Db;
CREATE DATABASE User_Db;
USE User_Db;

DROP TABLE IF EXISTS user CASCADE;

CREATE TABLE user
(
    id_user  INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE,
    email    VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    role     VARCHAR(7) CHECK (role = 'manager' or role = 'client' or role = 'admin') default 'client',
    ban      INT                                                                      default 0
);