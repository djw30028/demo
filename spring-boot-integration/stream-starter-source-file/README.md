## Document
http://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/
https://blog.pivotal.io/big-data-pivotal/products/data-goes-cloud-native-with-the-new-spring-cloud-data-flow

## Description
Fine version of spring integration of file. When file is dropped, it will pick up by directory monitor and then process it

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

## start RabbitMQ if not 
$ cd /usr/local/Cellar/rabbitmq/3.6.4/sbin
$ sh rabbitmqctl status -> check status
$ sh rabbitmqctl stop
$ rabbitmq-server
http://localhost:15672/ guest/guest

## run
java -jar target/fileeventprocess.jar

## Test
drop file to /usr/local/demo/input
* Note: change permission if needed

## Observation
2017-01-18 11:49:56.995  INFO 35498 --- [ask-scheduler-2] o.s.i.file.FileReadingMessageSource      : Created message: [GenericMessage [payload=/usr/local/demo/input/good_morning.txt, headers={id=f59afef6-cbf8-2cd5-9e2b-eb2a19c66e80, timestamp=1484758196995}]]
2017-01-18 11:49:56.997  INFO 35498 --- [ask-scheduler-2] com.michaelw.source.FileHandler          :  -- FileHandler.process.Message good_morning.txt received. Content: hi three
good morning
good work

2017-01-18 11:49:56.999  INFO 35498 --- [ask-scheduler-2] c.m.processor.ProcessorModuleDefinition  : transfer payload=hi three
good morning
good work
 after the transformer
2017-01-18 11:50:00.516  INFO 35498 --- [ask-scheduler-3] o.s.i.file.FileReadingMessageSource      : Created message: [GenericMessage [payload=/usr/local/demo/input/good_morning.txt, headers={id=b678aa9b-4083-4ffd-7d37-624d299675a0, timestamp=1484758200516}]]
2017-01-18 11:50:00.518  INFO 35498 --- [ask-scheduler-3] com.michaelw.source.FileHandler          :  -- FileHandler.process.Message good_morning.txt received. Content: hi three
good morning
good work

2017-01-18 11:50:00.518  INFO 35498 --- [ask-scheduler-3] c.m.processor.ProcessorModuleDefinition  : transfer payload=hi three
good morning
good work
 after the transformer
2017-01-18 11:50:00.519  INFO 35498 --- [ask-scheduler-3] com.michaelw.sink.SinkModuleDefinition   : Received: IngestData{value='hi three
good morning
good work
 after the transformer'}
 
## Functionality
<pre>
  public static void main(String[] args) {
        new AggregateApplicationBuilder(StreamStarterSourceFileApplication.class, args)
                .from(SourceApplication.class)
                .via(ProcessorApplication.class)
                .run();
   }
    
1. Spring Integrate function
  1.1 File is polling under /usr/local/demo/input, defined in FileSourceProperties.java
      @EnableBinding(Source.class)
      @EnableConfigurationProperties({FileSourceProperties.class})
      public class FileSourceConfiguration {
        @Bean
        public IntegrationFlow fileSourceFlow() {
          logger.info(" -- fileSourceFlow ");
          return IntegrationFlows
                .from("fileInboundChannel")
                .transform(fileToStringTransformer())
                //.handle("fileHandler", "handleFile").get();
                .handle("fileHandler", "handle")
                .channel(ApplicationConfiguration.INBOUND_CHANNEL) //forward to MQ with INBOUND_CHANNEL
                .get();
        }
  1.2 transform -> public FileToStringTransformer fileToStringTransformer()
  1.3 handler --> FileHandler.java 
  1.4 forward to MQ: processor --> line 43: FileSourceConfiguration .channel(ApplicationConfiguration.INBOUND_CHANNEL)
  
2. Processor
  2.1 get payload from MQ -> ProcessorModuleDefinition.transform through ApplicationConfiguration.INBOUND_CHANNEL
  2.2 convert to IngestData
  2.3 forward to MQ: sink -> MQ: Processor.OUTPUT
  
3. Sink
  3.1 get payload from MQ -> SinkModuleDefinition.loggerSink through Processor.OUTPUT 
</pre>