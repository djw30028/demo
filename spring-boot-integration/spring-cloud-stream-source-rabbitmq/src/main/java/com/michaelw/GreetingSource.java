package com.michaelw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;

/**
 * Created by michaelwang on 12/20/16.
 */
@EnableBinding(Source.class)
public class GreetingSource {
    private static Logger logger = LoggerFactory.getLogger(GreetingSource.class);

    @InboundChannelAdapter(Source.OUTPUT)
    public String greet() {
        logger.info(" ===== " + "hello world " + System.currentTimeMillis());
        return "hello world " + System.currentTimeMillis();
    }
}
