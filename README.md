# Movie Microservice REST API
This is a microservice that provides a REST API that gathers data from a couple of SQLite databases (movies.db and ratings.db). 

The project is a maven project.

## Dependencies
	-Maven (to build)
	-Java 11

## Install

    mvn package

## Run the app

    java -jar movie-microservice-0.0.1.jar
    or
    
    To specify a custom application.properties file (to be able to specify a different database location) use:
    
    java -jar movie-microservice-0.0.1.jar --spring.config.location=<path_to_application_properties>

    A sample application.properties file can be found under /target/classes/ after building with maven.

# REST API Description

The REST API is described below.

## Get list of movies

### Request

**Method** : `GET /movie/all?page=<number>`

**URL** : `http://localhost:8080/movie/all?page=<number>`

**Parameters** :

	page (optional)
		default value = 0
		constraint = Must be a non-negative number.

**Description** : `Endpoint to list all movies (paginated). If page number is not specified the first page will be returned. Returns 50 movies per page.`

### Success Response

**Code** : `200 OK`
**Content Sample** : 

    IMDB ID, Title, Genres, Release Date, Budget
	tt0094675,Ariel,[{"id": 18, "name": "Drama"}, {"id": 80, "name": "Crime"}],1988-10-21,0
	tt0092149,Shadows in Paradise,[{"id": 18, "name": "Drama"}, {"id": 35, "name": "Comedy"}],1986-10-16,0
	tt0113101,Four Rooms,[{"id": 80, "name": "Crime"}, {"id": 35, "name": "Comedy"}],1995-12-09,4000000
	tt0107286,Judgment Night,[{"id": 28, "name": "Action"}, {"id": 53, "name": "Thriller"}, {"id": 80, "name": "Crime"}],1993-10-15,0
	tt0076759,Star Wars,[{"id": 12, "name": "Adventure"}, {"id": 28, "name": "Action"}, {"id": 878, "name": "Science Fiction"}],1977-05-25,11000000
	tt0266543,Finding Nemo,[{"id": 16, "name": "Animation"}, {"id": 10751, "name": "Family"}],2003-05-30,94000000
	...

### Response Negative Page Number
**Code** : '400 Bad Request'
**Content Sample** :

	{"message":"Constraint violation error.","details":["getAllMovies.pageNumber: Page number must be greater than 0."]}

### Response Non-Integer Page Number
**Code** : '400 Bad Request'
**Content Sample** :

	{"message":"Method argument does not match.","details":["Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; nested exception is java.lang.NumberFormatException: For input string: \"string\""]}

## Get movie details

**Method** : `GET /movie/details?id=<number>` or `GET /movie/details?title=<String>`

**URL** : `http://localhost:8080/details?id=<number>` or `http://localhost:8080/details?title=<String>`

**Parameters** :

	id (required if title not specified)
		default value = N/A
		constraint = Must be a non-negative number.

	title (required if id not specified)
		default value = N/A
		constraint = N/A

**Description** : `Endpoint to get the details of the movie specified either by id or title.`

### Success Response

**Code** : `200 OK`
**Content Sample** : 

	Details For Movie
	IMDB ID: tt0094675
	Title: Ariel
	Description: Taisto Kasurinen is a Finnish coal miner whose father has just committed suicide and who is framed for a crime he did not commit. In jail, he starts to dream about leaving the country and starting a new life. He escapes from prison but things don't go as planned...
	Release Date: 1988-10-21
	Budget: 0
	Runtime: 69.0
	Ratings average: 3.4018691588785046
	Genres: [{"id": 18, "name": "Drama"}, {"id": 80, "name": "Crime"}]
	Original Language: null
	Production companies: [{"name": "Villealfa Filmproduction Oy", "id": 2303}, {"name": "Finnish Film Foundation", "id": 2396}]

### Movie Not Found Response
**Code** : `404 Not Found`
**Content Sample** :
	
	{"message":"Record was not found.","details":["Record not found for movie id: 0"]}
