# Projects Management application

| Technology | Badge |
|:-----------:|:-----:|
| Travis CI | [![Build Status](https://travis-ci.com/NewbieLM/projectmanagement.svg?token=sFznC5fXt2hVf8Zjms39&branch=master)](https://travis-ci.com/NewbieLM/projectmanagement) |

Console application that simulates work of projects management system. 

This app provide CRUD operations with such entities as Customer, Project, Team, User, Skill. All relationships between entities are many to many. 

## Technology Stack
* **Programming Language:** Java
* **Build System:** Maven
* **Database:** MySQL
* **Continuous Integration:** Travis-CI 
* **Database schema migration:** Liquibase


## Getting Started
To run this application you must:
1. download copy of the project to your local machine;
2. import project to IDE;
3. reimport maven projects;
4. update mysql.properties and liquibase.properties files acording to your database settings;
5. run init.sql and populate.sql;
6. run main method that located in Main.java. 