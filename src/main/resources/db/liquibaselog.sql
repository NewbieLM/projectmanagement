--liquibase formatted sql

--changeset Maksym:1
CREATE SCHEMA IF NOT EXISTS company CHARACTER SET utf8;

DROP TABLES IF EXISTS company.user_skills, company.team_users, company.project_teams, company.customer_projects;
DROP TABLES IF EXISTS company.users, company.skills, company.teams, company.projects, company.customers; 
COMMIT;

CREATE TABLE company.users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL
);

	CREATE TABLE company.skills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    skill VARCHAR(30) NOT NULL
);

CREATE TABLE company.teams (
    id INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(30) NOT NULL
);

CREATE TABLE company.projects (
    id INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(30) NOT NULL,
    cost INT NOT NULL DEFAULT 0
);

CREATE TABLE company.customers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE company.customer_projects (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
	project_id INT,
    customer_budget INT,
   FOREIGN KEY (customer_id)
        REFERENCES company.customers (id)
        ON DELETE CASCADE,
    FOREIGN KEY (project_id)
        REFERENCES company.projects (id)
        ON DELETE CASCADE,
    UNIQUE KEY unique_projects_per_customer (customer_id, project_id)
);


CREATE TABLE company.project_teams (
    id INT PRIMARY KEY AUTO_INCREMENT,
	  project_id INT,
    team_id INT,
    FOREIGN KEY (project_id)
        REFERENCES company.projects (id)
        ON DELETE CASCADE,
    FOREIGN KEY (team_id)
        REFERENCES company.teams (id)
        ON DELETE CASCADE,
    UNIQUE KEY unique_teams_per_project (project_id, team_id)
);

CREATE TABLE company.team_users (
    id INT PRIMARY KEY AUTO_INCREMENT,
	  team_id INT,
    user_id INT,
    FOREIGN KEY (team_id)
        REFERENCES company.teams (id)
        ON DELETE CASCADE,
    FOREIGN KEY (user_id)
        REFERENCES company.users (id)
        ON DELETE CASCADE,
    UNIQUE KEY unique_users_per_team (team_id, user_id)
);

CREATE TABLE company.user_skills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    skill_id INT,
    FOREIGN KEY (user_id)
        REFERENCES company.users (id)
        ON DELETE CASCADE,
    FOREIGN KEY (skill_id)
        REFERENCES company.skills (id)
        ON DELETE RESTRICT,
    UNIQUE KEY unique_skills_per_user (user_id, skill_id)
);


--changeset Maksym:2
DELETE FROM company.user_skills;
DELETE FROM company.team_users;
DELETE FROM company.project_teams;
DELETE FROM company.customer_projects;
COMMIT;

DELETE FROM company.users;
DELETE FROM company.skills;
DELETE FROM company.teams;
DELETE FROM company.projects;
DELETE FROM company.customers;
COMMIT;

ALTER TABLE company.user_skills AUTO_INCREMENT = 1;
ALTER TABLE company.team_users AUTO_INCREMENT = 1;
ALTER TABLE company.project_teams AUTO_INCREMENT = 1;
ALTER TABLE company.customer_projects AUTO_INCREMENT = 1;
ALTER TABLE company.users AUTO_INCREMENT = 1;
ALTER TABLE company.skills AUTO_INCREMENT = 1;
ALTER TABLE company.teams AUTO_INCREMENT = 1;
ALTER TABLE company.projects AUTO_INCREMENT = 1;
ALTER TABLE company.customers AUTO_INCREMENT = 1;

INSERT INTO company.users (name)
VALUES ('Ashely'),
    ('Randall'),
    ('Barry'),
    ('Alea'),
    ('Alexandra'),
    ('Lucius'),
    ('Todd'),
    ('Cairo'),
    ('Rina'),
    ('Eagan');


INSERT INTO company.skills (skill)
VALUES ('JAVA'),
    ('C++'),
    ('SQL'),
    ('JAVASCRIPT'),
    ('HTML'),
    ('CSS');


INSERT INTO company.user_skills (user_id, skill_id)
VALUES (1, 1), (1, 3), (1, 4),
    (2, 4),
    (3, 5), (3, 6),
    (4, 3),
    (5, 1), (5,5), (5, 6),
    (6, 1),
    (7, 3),
    (8, 5), (8, 6),
    (9, 1),
    (10, 2), (10, 3);


INSERT INTO company.teams (description)
VALUES ('Team #1'),
    ('Team #2'),
    ('Team #3'),
    ('Team #4'),
    ('Team #5'),
    ('Team #6'),
    ('Team #7'),
    ('Team #8'),
    ('Team #9'),
    ('Team #10');

INSERT INTO company.team_users (team_id, user_id)
VALUES (1, 2), (1, 3), (1, 5),
    (2, 1), (2, 2),
    (3, 3), (3, 4), (3, 5),
    (4, 2), (4, 1),
    (5, 1), (5, 2), (5, 3), (5, 4), (5,5), (5, 9),
    (6, 3), (6, 4), (6, 10),
    (7, 1), (7, 2), (7, 3),
    (8, 4), (8, 5), (8, 6),
    (9, 7), (9, 8), (9, 9),
    (10, 10), (10, 8), (10, 9);


INSERT INTO company.projects (description)
VALUES ('Project #1'),
    ('Project #2'),
    ('Project #3'),
    ('Project #4'),
    ('Project #5'),
    ('Project #6'),
    ('Project #7'),
    ('Project #8'),
    ('Project #9'),
    ('Project #10');

INSERT INTO company.project_teams (project_id, team_id)
VALUES (1, 1), (1, 2),
    (2, 5), (2, 4),
    (3, 3), (3, 4), (3, 5),
    (4, 3), (4, 1),
    (5, 1), (5, 2), (5, 3),
    (6, 7), (6, 8),
    (7, 9), (7, 10), (7, 1),
    (8, 7), (8, 2),
    (9, 8), (9, 10),
    (10, 9), (10, 5);


INSERT INTO company.customers (name)
VALUES ('Customer #1'),
    ('Customer #2'),
    ('Customer #3'),
    ('Customer #4'),
    ('Customer #5');

INSERT INTO company.customer_projects (customer_id, project_id, customer_budget)
VALUES (1, 1, 10000), (1, 2, 20000), (1, 3, 30000),
    (2, 3, 10000), (2, 4, 20000), (2, 5, 30000),
    (3, 6, 40000), (3, 7, 50000), (3, 8, 60000),
    (4, 9, 10000), (4, 10, 40000),
    (5, 1, 10000), (5, 2, 30000), (5, 3, 50000), (5, 7, 80000), (5, 10, 50000);

UPDATE company.projects p
SET cost = (SELECT SUM(customer_budget)
            FROM company.customer_projects cp
            WHERE cp.project_id = p.id
            GROUP BY cp.project_id);

COMMIT;
