# I. Deploy Mysql from local to bluemix
## 1. Tag mysql image
1.1 find images ID for mysql   
  $ docker images mysql   
  
| REPOSITORY   |    TAG     |           IMAGE ID      |   
| ------------- |:-------------:| -----:|    
|mysql          |     5.6            |     e1406e1f7c42  |e1406e1f7c42    |

1.2 Use above images id to tag 
   $ docker tag e1406e1f7c42 registry.ng.bluemix.net/myclearflowns/mysql

1.3 Check again
   $ docker images

|REPOSITORY                                                  |  TAG               |  IMAGE ID   |         
| ------------- |:-------------:| -----:|   
| mysql        |                                                 5.6             |    e1406e1f7c42  |         
| registry.ng.bluemix.net/myclearflowns/mysql   |                latest          |    e1406e1f7c42  |     

## 2. Push mysql images to register
``$ docker push registry.ng.bluemix.net/myclearflowns/mysql ``  
``$ docker images  ``

## 3. Push to bluemix
3.1 pre check
  ``$ cf ic ps``   
3.2 push to bluemix and run   
  ``$ cf ic run --name mysql-blue -e MYSQL_ROOT_PASSWORD=root -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=demo -e MYSQL_USER=demo_user -e MYSQL_PASSWORD=demo_pass -d registry.ng.bluemix.net/myclearflowns/mysql`` 

For free account, get following error:   
`Quota exceeded for ram: Requested 16384, but already used 1856 of 2048 ram`   
Login to bluemix.com and create new container and select myclearflows/mysql:lastest and select 512m container. 



# II. Deploy blue/spring-boot-docker-mysql
## 1. Build images
  ``$ mvn clean package docker:build``   
  
## 2. Tag Push to register
  ``  
  $ docker tag blue/spring-boot-docker-mysql registry.ng.bluemix.net/myclearflowns/spring-boot-docker-mysql   
  $ docker push registry.ng.bluemix.net/myclearflowns/spring-boot-docker-mysql 
  ``
  
## 3. Push to bluemix
  $ cf ic run --name blue-app-mysql --link mysql-blue:mysql -p 8080 -m 256 registry.ng.bluemix.net/myclearflowns/spring-boot-docker-mysql 
 
## 4. test
Bind IP: in bluemix console, bind public IP to spring-boot-docker-mysql
http://169.46.19.62:8080/

get list stored by ftp
http://169.46.19.62:8080/ingests

#III Trouble shooting mysql container is not start
4.1 $ cf ic ps -a
4.2 $ cf ic logs 3ca86cc8-24d   
Get following error:    
error: database is uninitialized and password option is not specified 
  You need to specify one of MYSQL_ROOT_PASSWORD, MYSQL_ALLOW_EMPTY_PASSWORD and MYSQL_RANDOM_ROOT_PASSWORD

4.3 stop/delete container   
$ cf ic stop 3ca86cc8-24d   
$ cf ic rm 3ca86cc8-24d   
$ cf ic ps -a   

4.4 delete images, where e1406e1f7c42 is image id
$ cf ic images
$ cf ic rmi e1406e1f7c42

4.5 re-push mysql   
$ docker push registry.ng.bluemix.net/myclearflowns/mysql   
$ cf ic run --name mysql-blue -e MYSQL_ROOT_PASSWORD=root -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=demo -e MYSQL_USER=demo_user -e MYSQL_PASSWORD=demo_pass -d registry.ng.bluemix.net/myclearflowns/mysql   
$ cf ic ps -a 

4.6 re-push spring-boot-docker-mysql 
$ docker tag blue/spring-boot-docker-mysql registry.ng.bluemix.net/myclearflowns/spring-boot-docker-mysql  
$ docker push registry.ng.bluemix.net/myclearflowns/spring-boot-docker-mysql  
$ cf ic run --name blue-app-mysql --link mysql-blue:mysql -p 8080 -m 256 registry.ng.bluemix.net/myclearflowns/spring-boot-docker-mysql 
$ cf ic ps -a 

***

4.7 Other console commands:    
```
https://console.ng.bluemix.net/docs/containers/container_troubleshoot.html#container_troubleshoot
```
 - login   
   *$ cf login*   
   *$ cf ic login*  
    
   
 - List your containers and review the current status.List your containers and review the current status.
   -- Single container  $ cf ic ps -a     
   -- Container group:  $ cf ic group list
    
    
 - view log: $ cf ic logs CONTAINER   
 - start container: cf ic start CONTAINER

---

 - inspect:   
     $ cf ic inspect CONTAINER    
     $ cf ic group inspect GROUP   
     
---     

 - Log into the running container:   
   1 Find images   
     $ cf ic images   
   2 Start container if not: 
    $ cf ic start CONTAINER   
   3 Find container ID   
    $ cf ic ps   
   4 Log into your running container.    
    $ cf ic exec -it CONTAINER bash   
  
---
  - Login to mysql    
    $ cf ic ps  
    $ cf ic exec -it df95d89d-063 bash     
    root@instance-00135c2a: mysql -h localhost -u demo_user -p   
    mysql> show databases;   
    mysql> use demo   
    mysql> show tables;   
    mysql> select * from employee;
   
---  
    
  - log file  
     1 tail log file   
       $ cf ic logs <container-name>   
     2 recent    
       $ cf ic logs <container-name> -recent   
 
---
  - Copy file from local/remote container   
    1 find container name
      $ cf ic ps   
      got result:  
       35e0449b-b22        registry.ng.bluemix.net/myclearflowns/spring-boot-docker-mysql:latest   ""                  4 days ago          Running 4 days ago   169.46.21.231:8080->8080/tcp   blue-app-mysql    
      
    
    2 Copy to name of  blue-app-mysql    
    $ cf ic cp ./my_cp.txt blue-app-mysql:/usr/local/clearleap
      
