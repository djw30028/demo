package com.michaelw.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import java.io.File;

public class FileHandler {
    private static Logger logger = LoggerFactory.getLogger(FileHandler.class);

    private static final String HEADER_FILE_NAME = "file_name";
    private static final String MSG = "%s received. Content: %s";

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
