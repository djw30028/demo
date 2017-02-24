package com.michaelw.sink;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

import com.michaelw.IngestData;
import com.michaelw.IngestDataRepository;

/**
 * Created by michaelwang on 12/22/16.
 */
@EnableBinding(Sink.class)
public class SinkModuleDefinition {

    private static Logger logger = LoggerFactory.getLogger(SinkModuleDefinition.class);

    @Autowired
	private IngestDataRepository repository;
    
    /**
     * Recieved from message posted from ProcessorModuleDefinition
     * @param payload
     */
    @ServiceActivator(inputChannel= Processor.OUTPUT)
    public void loggerSink(IngestData payload) {

        logger.info("Received: " + payload);
        
        //save to DB
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
        
    }

}
