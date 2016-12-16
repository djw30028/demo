# Docker
Docker with Spring boot

## Install docker in macbook
* Follow https://docs.docker.com/docker-for-mac/ to install Docker
* Verify installation:<br>
   1. docker --version <br>
   2. docker-compose --version <br>
   3. docker-machine --version <br>
   4. docker ps
* Install examples:
  ```
  docker run hello-world
  ```
  docker ps -a <br>
  docker images

  ```
  docker run -d -p 80:80 --name webserver nginx
  ```
  Test with: http://localhost/ <br>
  Check: `docker ps`

* Start/Stop/Remove contains and images
  ```
  docker stop webserver
  docker start webserver
  docker rm -f webserver  --> remove container
  docker rmi nginx
  ```

# Spring boot standalone starting from fat jar
* mvn clean package
* java -jar target/spring-boot-swagger-docker-0.0.1-SNAPSHOT.jar
* http://localhost:8080/swagger-ui.html

# Add to Docker container without dockerhub account
* Build docker image
```
 mvn clean package docker:build
```
* Deploy image locally
```
  docker run -p 8080:8080 -t michael/spring-boot-swagger-docker
```

```
docker stop 55829eb2f490
docker rm 55829eb2f490
where docker rm 55829eb2f490 is the CONTAINER ID of docker ps
```
