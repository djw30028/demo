package com.michaelw;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.app.file.FileConsumerProperties;
import org.springframework.cloud.stream.app.file.FileUtils;
import org.springframework.cloud.stream.app.trigger.TriggerConfiguration;
import org.springframework.cloud.stream.app.trigger.TriggerProperties;
import org.springframework.cloud.stream.app.trigger.TriggerPropertiesMaxMessagesDefaultUnlimited;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.file.FileInboundChannelAdapterSpec;
import org.springframework.integration.dsl.file.Files;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.util.StringUtils;

/**
 * Created by michaelwang on 12/20/16.
 */
@EnableBinding(Source.class)
@Import(TriggerConfiguration.class)
@EnableConfigurationProperties({FileSourceProperties.class, FileConsumerProperties.class,
        TriggerPropertiesMaxMessagesDefaultUnlimited.class})
public class FileSourceConfiguration {

    @Autowired
    private FileSourceProperties properties;

    @Autowired
    private FileConsumerProperties fileConsumerProperties;

    @Autowired
    @Qualifier("defaultPoller")
    PollerMetadata defaultPoller;

    @Autowired
    Source source;

    @Bean
    public IntegrationFlow fileSourceFlow() {
        FileInboundChannelAdapterSpec messageSourceSpec = Files.inboundAdapter(new File(this.properties.getDirectory()));

        if (StringUtils.hasText(this.properties.getFilenamePattern())) {
            messageSourceSpec.patternFilter(this.properties.getFilenamePattern());
        } else if (this.properties.getFilenameRegex() != null) {
            messageSourceSpec.regexFilter(this.properties.getFilenameRegex().pattern());
        }

        if (this.properties.isPreventDuplicates()) {
            messageSourceSpec.preventDuplicates();
        }

        IntegrationFlowBuilder flowBuilder = IntegrationFlows
                .from(messageSourceSpec,
                        new Consumer<SourcePollingChannelAdapterSpec>() {

                            @Override
                            public void accept(SourcePollingChannelAdapterSpec sourcePollingChannelAdapterSpec) {
                                sourcePollingChannelAdapterSpec
                                        .poller(defaultPoller);
                            }

                        });
        return FileUtils.enhanceFlowForReadingMode(flowBuilder, this.fileConsumerProperties)
                .channel(source.output())
                .get();
    }

}