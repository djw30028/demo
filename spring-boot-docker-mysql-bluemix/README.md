# spring-boot-docker-mysql
Demo Spring Boot application running inside docker container linked with MySQL container.
 
# Section one: pull mysql to container
## Run MySQL 5.6 in Docker container if not available, pull mysql:5.6:

```
docker run --name mysql-blue -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=demo -e MYSQL_USER=demo_user -e MYSQL_PASSWORD=demo_pass -d mysql:5.6   
```

## Check mysql running
```
$ docker exec -it mysql-blue bash   
root@346a0d6f9b89:/# mysql -u demo_user -p    
password: demo_pass   
mysql> show databases;   
mysql> use demo;   
mysql> show tables;   

```

## Check the log to make sure the server is running OK:
```
$ docker ps   
$ docker logs mysql-blue   
```
Result   
[Note] mysqld: ready for connections.  
Version: '5.6.35'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server (GPL)


# Section two: build docker 
# Build docker image   
```
mvn clean package docker:build
```

## Run demo application in Docker container and link to demo-mysql:

```
docker run -p 8080:8080 --name blue-app-mysql --link mysql-blue:mysql -d blue/spring-boot-docker-mysql
```

## check the log by
```
$ docker logs blue-app-mysql   
$ docker exec -it blue-app-mysql bash
```
## Test
Open http://localhost:8080 in browser.  

wget http://localhost:8080
curl http://localhost:8080

# Clean up the docker constainer and image
## delete the docker container
```
$ docker ps -a |grep blue/spring-boot-docker-mysql   
$ docker stop [above id]   
$ docker rm [above id]   
```

## delete the docker image
```
$ docker images | grep michael/spring-boot-docker-mysql   
$ docker rmi [--force] [above image id]
```
 
* List all containers: $ docker ps
* Stop all container:
 $ docker stop $(docker ps -a -q)
* Delete all container:
 $ docker rm $(docker ps -a -q)

* List all images:
  $ docker images [-a]
* Delete all images: $ docker rmi $(docker images -q)
 

