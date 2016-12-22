package com.michaelw;

import com.michaelw.processor.ProcessorApplication;
import com.michaelw.source.SourceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.aggregate.AggregateApplicationBuilder;

@SpringBootApplication
public class StreamStarterSourceFileApplication {

    /**
     * public static void main(String[] args) {
     * <p>
     * SpringApplication.run(
     * StreamStarterSourceFileApplication.class, args);
     * }
     */
    public static void main(String[] args) {
        new AggregateApplicationBuilder(StreamStarterSourceFileApplication.class, args)
                .from(SourceApplication.class)
                .via(ProcessorApplication.class)
                .run();
    }
}
