package com.michaelw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;

import java.util.Date;

/**
 * Created by michaelwang on 12/20/16.
 */
@EnableBinding(Source.class)
public class GreetingSource {
    private static Logger logger = LoggerFactory.getLogger(GreetingSource.class);


    //default setting, working correct, use timerMessageSource instead to practice custom setting
    //@InboundChannelAdapter(Source.OUTPUT)
    public String greet() {
        logger.info(" ===== " + "hello world " + System.currentTimeMillis());
        return "hello world " + System.currentTimeMillis();
    }


    //@Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1"))
    public MessageSource<TimeInfo> timerMessageSource() {
        logger.info(" ===== " + "timerMessageSource " + System.currentTimeMillis());
        return () -> MessageBuilder.withPayload(new TimeInfo(new Date().getTime()+"","Label")).build();
    }

    public static class TimeInfo{

        private String time;
        private String label;

        public TimeInfo(String time, String label) {
            super();
            this.time = time;
            this.label = label;
        }

        public String getTime() {
            return time;
        }

        public String getLabel() {
            return label;
        }

    }
}
