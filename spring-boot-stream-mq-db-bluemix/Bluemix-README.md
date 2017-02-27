# Project Description
A ftp server container is started with volume specified. When a file is ftped (with name: michaelw/password, ftp <IP> where IP is binded IP to ftp server container). This container will pickup the file and send to Message Queue (here RabbitMQ) for process.


# PART I: Deploy RabbitMQ to bluemix
## build image if not available
$ docker images | grep rabbitmq   
if not result: then run following   
$ docker run -d --hostname rabbitmq --name rabbitmq -p 9090:15672 rabbitmq:3-management   

## tag image into repository, use above images id to tag, cda8025c010b is the re
$ docker tag cda8025c010b registry.ng.bluemix.net/myclearflowns/rabbitmq
$ docker images

## push to bluemix
* Check first   
$ cf ic images |grep rabbitmq
* Push   
$ docker push registry.ng.bluemix.net/myclearflowns/rabbitmq
* Check again      
$ cf ic images |grep rabbitmq

## run in bluemix    
* Check first   
$ cf ic ps   
* Run   
$ cf ic run -d --hostname rabbitmq --name rabbitmq -p 9090:15672 registry.ng.bluemix.net/myclearflowns/rabbitmq
* Check again   
$ cf ic ps   
  registry.ng.bluemix.net/myclearflowns/rabbitmq:latest

# PART II: Deploy mysql to bluemix
## Install local docker if not installed:
$ docker run --name mysql-blue -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=demo -e MYSQL_USER=demo_user -e MYSQL_PASSWORD=demo_pass -d mysql:5.6 

$ docker ps |grep mysql

## Tag and push to bluemix
$ docker images mysql 
Get Id   
$ docker <ID above> registry.ng.bluemix.net/myclearflowns/mysql    
$ docker push registry.ng.bluemix.net/myclearflowns/mysql    
$ cf ic images

## Run mysql in bluemix
$ cf ic ps
$ cf ic run --name mysql-blue -e MYSQL_ROOT_PASSWORD=root -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=demo -e MYSQL_USER=demo_user -e MYSQL_PASSWORD=demo_pass -d registry.ng.bluemix.net/myclearflowns/mysql 
$ cf ic ps

# PART III: Deploy event/spring-boot-stream-mq-db
## build image if not available
$ docker images 
$ mvn clean package docker:build

$ docker tag event/spring-boot-stream-mq-db registry.ng.bluemix.net/myclearflowns/spring-boot-stream-mq-db
 
$ docker images

## push to bluemix
* Check first   
$ cf ic images
* Push   
$ docker push registry.ng.bluemix.net/myclearflowns/spring-boot-stream-mq-db
* Check again      
$ cf ic images

## run in bluemix    
* Check first   
$ cf ic ps
   
## Run with ftp volume and link to rabbitmq and mysql
$ cf ic run -p 8080:8080 --name spring-boot-stream-mq-db --volume my_volume:/mnt/nfs --link rabbitmq:rabbitmq --link mysql-blue:mysql -d registry.ng.bluemix.net/myclearflowns/spring-boot-stream-mq-db  

 
  * inspect container     
    $ cf ic inspect spring-boot-stream-mq-db   
      
      
  * ftp file: find IP then ftp to that IP  
    $ cf ic ip list   
    $ ftp 169.46.22.118  --> michaelw/password      
    put file through ftp
    
  * check logs
    $ cf ic logs spring-boot-stream-mq-db   
    
  * Login to mysql   
    $ cf ic exec -it mysql-blue /bin/bash
      :/# mysql -h localhost -u demo_user -p 
      password is: demo_pass
      mysql> show databases;
      mysql> use demo   
      mysql> show tables;  
      mysql> select * from ingest_data;   
    
   * View the file content with mysql retrieval
     $ cf ic ps   
     - Start spring-boot-docker-mysql container if not start   
     $ cf ic ip request    
     $ cf ic ip bind <IP> <Contaier ID>   
     http://169.46.20.24:8080/ingests
     
   * Check hadoop file system: username/password: michaelw/Bluemix123456789
    https://bi-hadoop-prod-4017.bi.services.us-south.bluemix.net:8443/gateway/default/hdfs/explorer.html#/user/michaelw/clearflow
    
## Run with ftp volume and link to rabbitmq 
$ cf ic run -p 8080:8080 --name spring-boot-stream-mq-db --volume my_volume:/mnt/nfs --link rabbitmq:rabbitmq -d registry.ng.bluemix.net/myclearflowns/spring-boot-stream-mq-db 

* inspect container 
$ cf ic inspect spring-boot-stream-mq-db   
 
## Run without ftp  
$ cf ic run -p 8080:8080 --name event-file-mq-bluemix --link rabbitmq:rabbitmq -d registry.ng.bluemix.net/myclearflowns/spring-boot-stream-mq-db 
* Check again   
$ cf ic ps  

## Check logs
$ cf ic logs spring-boot-stream-mq-db


# PART IV: Test
## 1. Testing with ftp, 
ftp 169.46.22.18 
where IP is the binded public bluemix IP
ftp login: michaelw/password
put event.txt

The file event.txt will write bluemix volume shared between ftp and event-file-mq-bluemix
 

## 2. Testing with file copy
$ cf ic exec -it event-file-mq-bluemix bash   
root@instance-001e6598:/# cd /usr/local/demo/input  
  
In local: cd /usr/local/demo/input where hello.txt is created   
$ cf ic cp hello.txt event-file-mq-bluemix:/mnt/nfs/mydata   
$ cf ic cp good_morning.txt event-file-mq-bluemix:/mnt/nfs/mydata    

# PART V: Integrated with spring-boot-docker-mysql   
## Read from mysql database table: ingest_data   
$ cd ../demo/spring-boot-docker-mysql-bluemix  
$ mvn clean package docker:build   
$ docker tag blue/spring-boot-docker-mysql registry.ng.bluemix.net/myclearflowns/spring-boot-docker-mysql   
$ docker push registry.ng.bluemix.net/myclearflowns/spring-boot-docker-mysql 
$ cf ic run --name blue-app-mysql --link mysql-blue:mysql -p 8080 -m 256 registry.ng.bluemix.net/myclearflowns/spring-boot-docker-mysql

Bind with public IP

http://169.46.20.24:8080/ingests

