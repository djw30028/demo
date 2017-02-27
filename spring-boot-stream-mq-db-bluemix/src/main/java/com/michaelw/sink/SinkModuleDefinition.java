package com.michaelw.sink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

import com.michaelw.IngestData;
import com.michaelw.IngestDataRepository;
import com.michaelw.service.HadoopService;

/**
 * Created by michaelwang on 12/22/16.
 * 
 * Hadoop check point:
 * https://bi-hadoop-prod-4017.bi.services.us-south.bluemix.net:8443/gateway/default/hdfs/explorer.html#/user/michaelw/clearflow
 */
@EnableBinding(Sink.class)
public class SinkModuleDefinition {

    private static Logger logger = LoggerFactory.getLogger(SinkModuleDefinition.class);

    @Autowired
	private IngestDataRepository repository;
    
    @Autowired
    private HadoopService hadoopService;
    
    /**
     * Recieved from message posted from ProcessorModuleDefinition
     * @param payload
     */
    @ServiceActivator(inputChannel= Processor.OUTPUT)
    public void loggerSink(IngestData payload) {

        logger.info("Received: " + payload);
        
        //1. save to DB
        repository.save(payload);
        
        //retrieve all ingest
        Iterable<IngestData> allIngested = repository.findAll();
        
        if (allIngested == null) {
        	   logger.info(" No data in database ingest_data ");
           return;
        }
        
        int i=0;
        for (IngestData in : allIngested) {
        	   logger.info(" data from ingest_data table: i="+i++ + ", " + in.toString() + "\n");
        }
        
        //2. save to hadoop
        try {
        	   logger.info(" ready to call hadoop ");
        	   hadoopService.saveContent(payload.getFilePath(), payload.getValue());
        }
        catch (Throwable e) {
        	  e.printStackTrace();
        }
         
    }

}
