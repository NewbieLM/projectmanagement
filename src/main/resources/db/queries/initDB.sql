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


COMMIT;