# FILE
Spring Integration File support for local docker

## Step 1, create Docker file

## Step 2, build images
```
$ mvn clean package docker:build

## Step 3, check images
$ docker images

## Step 4: Start 
$ cd ../src/main/docker
$ docker-compose up

## Step 5: access docker
$ docker ps 
$ docker logs <id>  
$ docker exec -it <id> bash


## Step 6: test copy
in local host:
$ cd to any directory having txt file

$ docker cp file1.txt 276f72be776f:/mnt/nfs/mydata

## Step 7: verify
$ docker logs <container-name>

Observation in log:
 spring-boot-file-monitor_1  | 2017-01-13 23:56:08.148  INFO 1 --- [ask-scheduler-1] o.s.i.file.FileReadingMessageSource      : Created message: [GenericMessage [payload=/Users/michaelwang/project/integration/file1.txt, headers={id=9e188458-e100-498d-b810-4cf103df4e32, timestamp=1484351768147}]]
spring-boot-file-monitor_1  | file1.txt received. Content: hi hello
spring-boot-file-monitor_1  | see you
 
---
# Apply volume from docker-pure-ftpd-bluemix, where ftp-data-root is defined.
## Appraoch 1: hard code binding without volume
$ mvn clean package docker:build
$ docker run -d --name my-file-event-1 -v /Users/michaelwang/ftp/michael:/Users/michaelwang/project/integration blue/file-read-directory
$ docker exec -it my-file-event-1 bash
$ docker logs my-file-event-1
when ftp file to 
ftp localhost 21  
  put <file> 
  
  The file is pickuped and processed. Well done.
$ docker cp file1.txt 46938123f3a2:/Users/michaelwang/project/integration

Observation: the binding only works for the directory under /User/michaelwang

## Approach 2:
$ docker volume ls
** Note: /Users/michaelwang/project/integration inside contrain is the directory to be monitored.

$ mvn clean package docker:build
$ docker run -d --name my-file-event -v ftp-data-root:/Users/michaelwang/project/integration blue/file-read-directory
$ docker ps --> 
  2da56718d8d9        blue/file-read-directory 
$ docker inspect my-file-event   
> "Mounts": [   
>          {   
>              "Name": "ftp-data-root",   
>              "Source": "/var/lib/docker/volumes/ftp-data-root/_data",   
>              "Destination": "/Users/michaelwang/project/integration",   

$ docker exec -it 2da56718d8d9 bash   
$ docker exec -it my-file-event bash   
create directory inside container: /User/michaelwang/project/integration   
$ docker logs 2da56718d8d9   
$ docker logs my-file-event   

$ docker cp file1.txt 2da56718d8d9:/Users/michaelwang/project/integration
  2017-01-17 15:58:32.672  INFO 1 --- [ask-scheduler-9] o.s.i.file.FileReadingMessageSource      : Created   message: [GenericMessage [payload=/Users/michaelwang/project/integration/file1.txt, headers={id=b62c5413-35de-cce7-5507-651dd6c6c3ce, timestamp=1484668712671}]]
file1.txt received. Content: hi hello
see you

 






