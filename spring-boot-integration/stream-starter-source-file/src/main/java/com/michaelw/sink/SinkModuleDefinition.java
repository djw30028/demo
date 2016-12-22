package com.michaelw.sink;

import com.michaelw.IngestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * Created by michaelwang on 12/22/16.
 */
@EnableBinding(Sink.class)
public class SinkModuleDefinition {

    private static Logger logger = LoggerFactory.getLogger(SinkModuleDefinition.class);

    @ServiceActivator(inputChannel= Processor.OUTPUT)
    public void loggerSink(IngestData payload) {

        logger.info("Received: " + payload);
    }

}
