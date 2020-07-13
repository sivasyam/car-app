As per the given challenge document

#####Configured Swagger UI
#####Created endpoint to search cars
#####Created endpoint to search polygons
#####Created endpoint to search individual car
#####Created endpoint to search individual polygon
#####Created Junit and integration test cases
#####Create git log history
#### Below are the Endpoints

```
    To access swagger documentation 
    http://localhost:8989/swagger-ui.html
```
```
Endpoint to search cars

GET    /api/v1/car
```
```
Endpoint to search polygons

GET    /api/v1/polygon

```
```
Endpoint to search individual car

GET    /api/v1/car/{vin}

```

```
Endpoint to search individual polygonid

GET    /api/v1/polygon/{polygonId}

```

```
Endpoint to search for cars/polygons based on vin/polygoid

GET    /api/v1/search?vin={vin} or /api/v1/search?polygonId={polygonId}

```
####Technologies used
JAVA 8, Spring-boot, Maven, docker, Swagger, cache

####Operating system tested:
Mac OS

####To Start the application
```
Download the project from the drive and unzip the folder
Open terminal and go to the project location example: cd ~/Downloads/share-now
Run the script file docker-run.sh, example: bash docker-run.sh
```

#####Executed tests and results
```
Exected Integration test related to cars and polygons
```

#####Decisions you had to take and why
```
1. For performace, after server statup storing the cars(with polygonId) and polygons(with vins set) list in cache
2. If we search with vin then returning vehicle object with polygoid
3. If we search wtih polygonId returning polygon object with set vins present in that polygon
4. Storing result in cache using spring-boot-cache, TODO: Needs to improve and configuring  redis
5. Created integration and unit test cases
6. Refreshing cache for every 300000 ms (con be configurable from application.yml file)
7. Configured Stuttgart vehicles Endpoint in application.yml, before calling this EP checking for the status whether the service up or not
8. Downloaded polygons.json from the given github url and placed under resources folder
9. Created swagger API documentation which can be accessble from the following link : http://localhost:8989/swagger-ui.html
10. Created docker compose file and docker file, to run the application created dcoker-run.sh file
11. Git log histery is uploaded to git_log.txt file under project root folder  
```
