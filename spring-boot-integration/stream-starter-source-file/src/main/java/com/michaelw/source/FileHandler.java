package com.michaelw.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.io.File;

public class FileHandler {
    private static Logger logger = LoggerFactory.getLogger(FileHandler.class);


    private static final String HEADER_FILE_NAME = "file_name";
    private static final String MSG = "%s received. Content: %s";
    private String format = "yyyy-MM-dd HH:mm:ss";
    private  Message<String> internalMsg;

    /**
     * Works without transform
     * return IntegrationFlows
        .from("fileInboundChannel")
        //.transform(fileToStringTransformer())
        .handle("fileHandler", "handle").get();
     * @param msg
     */
    public void handleFile(Message<File> msg) {
        String fileName = (String) msg.getHeaders().get(HEADER_FILE_NAME);
        //String content = msg.getPayload();

        System.out.println(" -- FileHandler.process.File " + String.format(MSG, fileName));
    }

    /**
     *
     * Defined in FileSourceConfiguration.fileSourceFlow
     * @param msg
     */
    public Message<?> handle(Message<?> msg) {
        String fileName = (String) msg.getHeaders().get(HEADER_FILE_NAME);
        String content = msg.getPayload().toString();

        logger.info(" -- FileHandler.process.Message " + String.format(MSG, fileName, content));


        return msg;
    }
}
