package com.michaelw.processor;

import com.michaelw.IngestData;
import com.michaelw.config.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

/**
 * Created by michaelwang on 12/22/16.
 */
@EnableBinding(Processor.class)
public class ProcessorModuleDefinition {

    private static Logger logger = LoggerFactory.getLogger(ProcessorModuleDefinition.class);
    private static final String HEADER_FILE_NAME = "file_name";

    @Transformer(inputChannel = ApplicationConfiguration.INBOUND_CHANNEL, outputChannel = Processor.OUTPUT)
    public IngestData transform(final Message<?> inbound) {
        IngestData ingestData = new IngestData();

        String fileName = (String) inbound.getHeaders().get(HEADER_FILE_NAME);
        logger.info("transfer fileName=" + fileName);
        ingestData.setFilePath(fileName);
        
        String payload = inbound.getPayload().toString();
        payload = payload + " after the transformer";

        logger.info("transfer payload=" + payload);
        ingestData.setValue(payload);

        return ingestData;
    }
}
