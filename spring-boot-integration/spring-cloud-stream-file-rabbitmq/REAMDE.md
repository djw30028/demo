# TO BE DELETE, refer stream-starter-source-file

##  Build
mav clean package

##  Run
java -jar target/spring-cloud-stream-file-rabbitmq-0.0.1-SNAPSHOT.jar

## Test
drop file to /usr/local/demo/input
* Note: change permission if needed

## Observation
--- [ask-scheduler-2] o.s.i.file.FileReadingMessageSource      : Created message: [GenericMessage [payload=/Users/michaelwang/project/input/pom.txt, headers={id=297fb1d8-2cdd-e38d-f755-ba97b3b7a2cd, timestamp=1482290151857}]]

## TODO:
need to find the reason
 <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
    <version>1.0.3.RELEASE</version>
 </dependency>

http://start-scs.cfapps.io/