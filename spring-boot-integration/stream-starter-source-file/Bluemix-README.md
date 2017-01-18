# PART I: Deploy RabbitMQ to bluemix
## build image if not available
$ docker images rabbitm   
if not result: then run following   
$ docker run -d --hostname rabbitmq --name rabbitmq -p 9090:15672 rabbitmq:3-management   

## tag image into repository, use above images id to tag, cda8025c010b is the re
$ docker tag cda8025c010b registry.ng.bluemix.net/myclearflowns/rabbitmq
$ docker images

## push to bluemix
* Check first   
$ cf ic images
* Push   
$ docker push registry.ng.bluemix.net/myclearflowns/rabbitmq
* Check again      
$ cf ic images

## run in bluemix    
* Check first   
$ cf ic ps   
* Run   
$ cf ic run -d --hostname rabbitmq --name rabbitmq -p 9090:15672 registry.ng.bluemix.net/myclearflowns/rabbitmq
* Check again   
$ cf ic ps   
  registry.ng.bluemix.net/myclearflowns/rabbitmq:latest

# PART II: Deploy stream-starter-source-file
## build image if not available
$ docker images 
$ mvn clean package docker:build

## tag image into repository, use above images id to tag, cda8025c010b is the re
$ docker tag a82da144d5ca registry.ng.bluemix.net/myclearflowns/stream-starter-source-file
$ docker images

## push to bluemix
* Check first   
$ cf ic images
* Push   
$ docker push registry.ng.bluemix.net/myclearflowns/stream-starter-source-file
* Check again      
$ cf ic images

## run in bluemix    
* Check first   
$ cf ic ps   
* Run   
$ cf ic run -p 8080:8080 --name event-file-mq-bluemix --link rabbitmq:rabbitmq -d registry.ng.bluemix.net/myclearflowns/stream-starter-source-file 
* Check again   
$ cf ic ps  

## Check logs
$ cf ic logs event-file-mq-bluemix


# PART III: Test
## Testing
$ cf ic exec -it event-file-mq-bluemix bash   
root@instance-001e6598:/# cd /usr/local/demo/input  
  
In local: cd /usr/local/demo/input where hello.txt is created   
$ cf ic cp hello.txt event-file-mq-bluemix:/usr/local/demo/input   
$ cf ic cp good_morning.txt event-file-mq-bluemix:/usr/local/demo/input    

```
?2017-01-18 21:18:07.991  INFO 1 --- [ask-scheduler-5] com.michaelw.source.FileHandler          :  -- FileHandler.process.Message hello.txt received. Content: hi three
good morning

good work

{2017-01-18 21:18:07.993  INFO 1 --- [ask-scheduler-5] c.m.processor.ProcessorModuleDefinition  : transfer payload=hi three
good morning

good work
 after the transformer
?2017-01-18 21:18:07.994  INFO 1 --- [ask-scheduler-5] com.michaelw.sink.SinkModuleDefinition   : Received: IngestData{value='hi three
good morning

good work
```

Well DONE

