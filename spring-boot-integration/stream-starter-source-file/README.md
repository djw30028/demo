## Document
http://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/

## Generate project
http://start-scs.cfapps.io/
* for Spring Cloud Stream Starter Applications
select File Source
       File Source Starter
groupd: com.michaelw
attifact: stream-starter-source-file

## Add binder dependency
```
 <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-stream-binder-rabbit</artifactId>
 </dependency>
```

## build
mvn clean package

## run
java -jar target/stream-starter-source-file-0.0.1-SNAPSHOT.jar

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
 </dependency>

