package com.michaelw.integration;

import com.michaelw.integration.filter.LastModifiedFileFilter;
import com.michaelw.integration.processor.FileProcessor;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@SpringBootApplication
public class FileReadDirectoryApplication {

	private static final String DIRECTORY = "/Users/michaelwang/project/integration";

    public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(FileReadDirectoryApplication.class, args);
	}



	@Bean
	public IntegrationFlow processFileFlow() {
		System.out.println(" -- processFileFlow ");
		return IntegrationFlows
				.from("fileInputChannel")
				.transform(fileToStringTransformer())
				.handle("fileProcessor", "process").get();
	}

    @Bean
    public MessageChannel fileInputChannel() {
        System.out.println(" -- fileInputChannel ");
		return new DirectChannel();
    }


	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
	public MessageSource<File> fileReadingMessageSource() {
		System.out.println(" -- fileReadingMessageSource ");

		CompositeFileListFilter<File> filters =new CompositeFileListFilter<>();
		filters.addFilter(new SimplePatternFileListFilter("*.txt"));
		//filters.addFilter(new SimplePatternFileListFilter("*.wmv"));
		filters.addFilter(new LastModifiedFileFilter());

		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setAutoCreateDirectory(true);
		source.setDirectory(new File(DIRECTORY));
		source.setFilter(filters);

		return source;
	}

	@Bean
	public FileToStringTransformer fileToStringTransformer() {

		System.out.println(" -- fileToStringTransformer ");
		return new FileToStringTransformer();
	}

	@Bean
	public FileProcessor fileProcessor() {
		System.out.println(" -- fileProcessor ");
		return new FileProcessor();
	}


}
