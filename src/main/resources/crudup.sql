DROP DATABASE IF EXISTS crudup;
CREATE DATABASE IF NOT EXISTS crudup;
USE crudup;
DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles
(
    id   BIGINT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO roles (name)
VALUES ('ADMIN');
INSERT INTO roles (name)
VALUES ('USER');

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT AUTO_INCREMENT,
    email    VARCHAR(50)  NOT NULL UNIQUE,
    nickname VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role_id  BIGINT       NOT NULL DEFAULT 2,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO users (email, nickname, password, role_id)
VALUES ('maingroon@gmail.com', 'casten', 'password', 1);
INSERT INTO users (email, nickname, password)
VALUES ('test1@gmail.com', 'user1', 'password');
INSERT INTO users (email, nickname, password)
VALUES ('test2@gmail.com', 'user2', 'password');

DROP TABLE IF EXISTS projects;
CREATE TABLE IF NOT EXISTS projects
(
    id          BIGINT AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    PRIMARY KEY (id)
);

INSERT INTO projects (name, description)
VALUES ('Project1', 'description1');
INSERT INTO projects (name, description)
VALUES ('Project2', 'description2');
INSERT INTO projects (name, description)
VALUES ('Project3', 'description3');
INSERT INTO projects (name, description)
VALUES ('Project4', 'description4');
INSERT INTO projects (name, description)
VALUES ('Project5', 'description5');

DROP TABLE IF EXISTS user_has_project;
CREATE TABLE IF NOT EXISTS user_has_project
(
    user_id    BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO user_has_project (user_id, project_id)
VALUES (1, 1);
INSERT INTO user_has_project (user_id, project_id)
VALUES (1, 2);
INSERT INTO user_has_project (user_id, project_id)
VALUES (1, 3);
INSERT INTO user_has_project (user_id, project_id)
VALUES (2, 3);
INSERT INTO user_has_project (user_id, project_id)
VALUES (2, 4);
INSERT INTO user_has_project (user_id, project_id)
VALUES (2, 5);
INSERT INTO user_has_project (user_id, project_id)
VALUES (3, 2);
INSERT INTO user_has_project (user_id, project_id)
VALUES (3, 4);

DROP TABLE IF EXISTS priorities;
CREATE TABLE IF NOT EXISTS priorities
(
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO priorities (name)
VALUES ('LOW');
INSERT INTO priorities (name)
VALUES ('MEDIUM');
INSERT INTO priorities (name)
VALUES ('HIGH');

DROP TABLE IF EXISTS tasks;
CREATE TABLE IF NOT EXISTS tasks
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(150) NOT NULL,
    completed   BOOLEAN      NOT NULL DEFAULT FALSE,
    priority_id BIGINT       NOT NULL DEFAULT 1,
    project_id  BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (priority_id) REFERENCES priorities (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Купить молоко', FALSE, 1, 1);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Купить коннекторы RJ-45', FALSE, 2, 1);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Зайти в фотомастурскую и забрать фото', FALSE, 3, 1);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Вернуть конспект по ОБД', TRUE, 1, 1);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Зайти к Серёге завтра', FALSE, 2, 1);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 1 к проекту 2', TRUE, 3, 2);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 2 к проекту 2', FALSE, 2, 2);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 3 к проекту 2', FALSE, 1, 2);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 4 к проекту 2', FALSE, 3, 2);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 5 к проекту 2', TRUE, 2, 2);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 1 к проекту 3', FALSE, 1, 3);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 2 к проекту 3', FALSE, 3, 3);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 3 к проекту 3', FALSE, 2, 3);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 4 к проекту 3', TRUE, 1, 3);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 5 к проекту 3', FALSE, 3, 3);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 1 к проекту 4', FALSE, 2, 4);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 2 к проекту 4', FALSE, 1, 4);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 3 к проекту 4', FALSE, 3, 4);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 4 к проекту 4', TRUE, 2, 4);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 5 к проекту 4', FALSE, 1, 4);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 1 к проекту 5', FALSE, 3, 5);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 2 к проекту 5', FALSE, 2, 5);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 3 к проекту 5', FALSE, 1, 5);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 4 к проекту 5', FALSE, 3, 5);
INSERT INTO tasks (name, completed, priority_id, project_id)
VALUES ('Задание 5 к проекту 5', TRUE, 1, 5);