package com.michaelw.source;

import com.michaelw.config.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;

import java.io.File;

/**
 * Created by michaelwang on 12/20/16.
 */

@EnableBinding(Source.class)
@EnableConfigurationProperties({FileSourceProperties.class})
public class FileSourceConfiguration {
    private static Logger logger = LoggerFactory.getLogger(FileSourceConfiguration.class);

    @Autowired
    private FileSourceProperties properties;

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

    @Bean
    public FileToStringTransformer fileToStringTransformer() {

        logger.info(" -- fileToStringTransformer ");
        return new FileToStringTransformer();
    }

    @Bean (name = "fileHandler")
    public FileHandler fileHandlerEntry() {
        logger.info(" -- fileHandler ");
        return new FileHandler();
    }

    @Bean
    @InboundChannelAdapter(value = "fileInboundChannel", poller = @Poller(fixedDelay = "100000"))
    public MessageSource<File> fileReadingMessageSource() {

        CompositeFileListFilter<File> filters =new CompositeFileListFilter<>();

        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setAutoCreateDirectory(true);
        logger.info(" -- Directory monitor: " + this.properties.getDirectory());
        source.setDirectory(new File(this.properties.getDirectory()));
        source.setFilter(filters);

        return source;
    }
}