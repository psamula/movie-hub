# Movie-Hub
## Table of contents
  * [General info](#general-info)
  * [Technologies Used](#technologies-used)
  * [Installation and Setup](#installation-and-setup)
  * [Usage](#usage)
  * [Example usage scenario](#example-usage-scenario)
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
Once the application is running, you can access it by navigating to [localhost:8080](http://localhost:8080) (however, I recommend using [localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/) for more convenient testing and access to documentation  

![moviehubswagger](https://user-images.githubusercontent.com/92303720/213501490-3ae1e9e6-be11-4e9c-9c23-3d56155fe7c5.png)

## Example usage scenario
### Create new user and log in
1. In order to use this application you need to register first. Navigate to registration-controller and post credentials to register new account.
2. After registration you should log yourself in. To do that, navigate to login-controller and log-in with your credentials.
3. Following the receiving of status code 200, copy the JWT token and authorize yourself. From this moment on, you'll be able to access all of the secured endpoints freely.
### Use the functionalities
4. Supposing you're planning to watch some movie this evening. First, head to external-api-movie-controller and GET recently trending movies list. Now you've got to chose an appropriate movie to your liking!
5. You can also display full movie details by inputting imdbId of chosen film to the "Display movie details" endpoint.
### Time to rate 
6. After watching the movie you can head over to movie-controller and rate your experiences with the movie. 
7. Supposing you especially liked one particular character played by an actor. Go ahead to cast-member-controller and rate the actor's performance! You can do the same thing with directors, writers etc.
8. You can access all your ratings any time you want by accessing proper endpoints "display movie ratings" in movie-controller or "display character ratings", "display staff member ratings" in cast-member-controller. The contents of these endpoints are assigned to each user separately.
### Other
9. Note that you can also freely display all movies, actors and staff members without rating. To do that, only thing you need is their imdb id available on [IMDb](http://imdb.com) 
10. If you want to rate actors or staff members without rating the movies you can do that effortlessly by accessing rating endpoints and specifying movie imdb ID, cast member imdb ID and their department in that film while leaving character ID or staff member ID input placeholders completely empty.

## Requirements
To run this app you'll need:
- JDK 17
- Docker
