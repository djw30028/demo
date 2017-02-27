 StreamStarterSourceFileApplication.java
 new AggregateApplicationBuilder(StreamStarterSourceFileApplication.class, args)
                .from(SourceApplication.class)
                .via(ProcessorApplication.class)
                .run();
                
Step 1: FileSouceConfigration
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
    
    1.1 fileInboundChannel will pick up *txt file with @Poller(fixedDelay = "100000")
    1.2 pass to fileToStringTransformer
    1.3 The string will pass to local file Handler
    1.4 Put MQ with name: ApplicationConfiguration.INBOUND_CHANNEL
  
 Step 2: ProcessorModuleDefinition.java 
  2.1 pick up message from Queue ApplicationConfiguration.INBOUND_CHANNEL
  @Transformer
  2.2 process message and assign to IngestData
  2.3 send IngestData to message queue with name of: Processor.OUTPUT
 
 Step 3: SinkApplication
  3.1 SinkModuleDefinition will pick up the message from Process.OUTPUT
   @ServiceActivator(inputChannel= Processor.OUTPUT)
    public void loggerSink(IngestData payload) {

        logger.info("Received: " + payload);
    }
 