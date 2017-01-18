package com.michaelw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;

/**
 * Created by michaelwang on 12/22/16.
 */
@Configuration
public class ApplicationConfiguration {
    public static final String INBOUND_CHANNEL = "inbound-channel";


    @Bean(name = INBOUND_CHANNEL)
    public MessageChannel inboundFilePollingChannel() {
        return MessageChannels.direct().get();
    }

}
