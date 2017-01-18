
# Part I: Pull and Run RabbitMQ
In hub.docker.com, search for rabbitMQ, pick the official one
### simple
$ docker run -d --hostname my-rabbit --name some-rabbit rabbitmq:3

### DO NOT USE THIS with manager: http://localhost:15672 --> localhost is not working
$ docker run -d --hostname rabbitmq --name rabbitmq rabbitmq:3-management

## Use this: with manager http://localhost:9090  guest/guest
$ docker run -d --hostname rabbitmq --name rabbitmq -p 9090:15672 rabbitmq:3-management

## Check images
$ docker ps   
0f298e30f489        rabbitmq:3-management  some-rabbit   
$ docker inspect some-rabbit   
$ docker logs some-rabbit   
$ docker exec -it some-rabbit bash   

# Part II: build/deploy file event project
## Build docker images
$ mvn clean package docker:build

## check docker repository
$ docker images   
  event/stream-starter-source-file 

## Modify application.yml
   
spring:         
  rabbitmq:   
    host: rabbitmq   
      
## Link RabbitMQ and Run 
Syntax: --link <name or id>:alias -> alias is an alias for the link name   
$ docker run -p 8080:8080 --name event-file-mq --link rabbitmq:rabbitmq -d event/stream-starter-source-file 

## Check logs
$ docker logs event-file-mq

## Testing
$ docker exec -it event-file-mq bash
root@abf9094e5428:/# cd /usr/local/demo/input    
In local: cd /usr/local/demo/input where hello.txt is created.  
$ docker cp hello.txt abf9094e5428:/usr/local/demo/input   

$ docker logs event-file-mq
2017-01-18 20:14:37.792  INFO 1 --- [ask-scheduler-8] o.s.i.file.FileReadingMessageSource      : Created message: [GenericMessage [payload=/usr/local/demo/input/hello.txt, headers={id=b9134932-bee7-c9a5-ad5c-48974e06a1c4, timestamp=1484770477791}]]
2017-01-18 20:14:37.806  INFO 1 --- [ask-scheduler-8] com.michaelw.source.FileHandler          :  -- FileHandler.process.Message hello.txt received. Content: hi three
good morning
good work

2017-01-18 20:14:37.810  INFO 1 --- [ask-scheduler-8] c.m.processor.ProcessorModuleDefinition  : transfer payload=hi three
good morning
good work
 after the transformer
2017-01-18 20:14:37.811  INFO 1 --- [ask-scheduler-8] com.michaelw.sink.SinkModuleDefinition   : Received: IngestData{value='hi three
good morning
good work
 after the transformer'}

Well Done

