## Start redis
redis-server

## Start RabbitMQ
rabbitmq-server

## Monitor : guest/guest
http://localhost:15672/

##  Build
mav clean package

##  Run
java -jar target/spring-cloud-stream-source-0.0.1-SNAPSHOT.jar


## Observation
* http://localhost:15672 -> Exchanges
* testsource topic
* in the console <br>
 ===== hello world 1482269688429 <br>
 ===== hello world 1482269689430
