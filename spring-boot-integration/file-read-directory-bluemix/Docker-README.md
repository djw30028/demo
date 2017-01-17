# FILE
Spring Integration File support for local docker

## Step 1, create Docker file

## Step 2, build images
```
$ mvn clean package docker:build

## Step 3, check images
$ docker images

## Step 4: Start 
$ cd /src/main/docker
$ docker-compose up

## Step 5: access docker
$ docker ps 
$ docker logs <id>  
$ docker exec -it <id> bash


## Step 6: test copy
in local host:
$ cd /Users/michaelwang/project/integration 
$ docker cp file1.txt 77cd22433e44:/Users/michaelwang/project/integration

## Step 7: verify
$ cf ic 77cd22433e44 logs

$ cf ic logs <container-name>

Observation in log:
 spring-boot-file-monitor_1  | 2017-01-13 23:56:08.148  INFO 1 --- [ask-scheduler-1] o.s.i.file.FileReadingMessageSource      : Created message: [GenericMessage [payload=/Users/michaelwang/project/integration/file1.txt, headers={id=9e188458-e100-498d-b810-4cf103df4e32, timestamp=1484351768147}]]
spring-boot-file-monitor_1  | file1.txt received. Content: hi hello
spring-boot-file-monitor_1  | see you
 
Future work: How to ftp file inside docker container
