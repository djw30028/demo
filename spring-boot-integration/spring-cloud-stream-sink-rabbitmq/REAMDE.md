## Start redis
redis-server

## Start RabbitMQ
rabbitmq-server

## Monitor : guest/guest
http://localhost:15672/

##  Build
mav clean package

##  Run
java -jar target/spring-cloud-stream-sink-rabbitmq-0.0.1-SNAPSHOT.jar



## Observation
hello world 1482272874551 will display in console passed from spring-cloud-stream-source-rabbitmq
where, MQ destination: testsource defiend in application.yml
