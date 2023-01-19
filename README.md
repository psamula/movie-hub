# Movie-Hub
## Table of contents
  * [General info](#general-info)
  * [Technologies Used](#technologies-used)
  * [Installation and Setup](#installation-and-setup)
  * [Usage](#usage)
  * [Requirements](#requirements)

## General info
"Movie-Hub" is a RESTful Java Spring application incorporating such
functionalities as: browsing and retrieving movies from external IMDb API,
facilitating its users to post ratings of each movie, character, and cast
member. Furthermore, Movie-Hub includes stateless user authentication via
JWT.  
It's fully integration-tested and dockerized.

## Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA / Hibernate
- Spring Security
- PostgreSQL
- Liquibase
- Ehcache
- AssertJ
- Mockito
- Lombok
- Swagger2
- Docker

## Installation and Setup
1. Clone the repository: ```git clone https://github.com/psamula/movie-hub```
2. Navigate to the project's root directory
3. Build the project's jar file: ```mvnw clean install```
4. Then build docker image using the built .jar: ```docker-compose build```
5. Run the whole setup of Movie-Hub + PostgreSQL using ```docker-compose up```

## Usage
Once the application is running, you can access it by navigating to `localhost:8080 (however, I recommend using localhost:8080/swagger-ui/ for more convenient testing and access to documentation

## Requirements
To run this app you'll need:
- JDK 17
- Docker
