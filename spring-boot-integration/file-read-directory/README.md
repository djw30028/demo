# FILE
Spring Integration File support

 ```
 mvn clean package
 java -jar target/file-read-directory-0.0.1-SNAPSHOT.jar

 ```

# Test
drop or modify txt file under /Users/michaelwang/project/integration
where directory is defined in FileReadDirectoryApplication.java

The console will display something like:
2016-12-20 20:50:47.670  INFO 12683 --- [ask-scheduler-1] o.s.i.file.FileReadingMessageSource      : Created message: [GenericMessage [payload=/Users/michaelwang/project/integration/file1.txt, headers={id=0413ea86-7592-3f28-cd83-f31b3706be0f, timestamp=1482285047669}]]
2016-12-20 20:50:47.677  INFO 12683 --- [           main] o.s.i.endpoint.EventDrivenConsumer       : started org.springframework.integration.config.ConsumerEndpointFactoryBean#1
file1.txt received. Content: hi hello
see you


# Future work
* process vedio file, currently only process txt file
* after process, remove the file to archive

