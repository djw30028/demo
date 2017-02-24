package com.michaelw.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 
 
@RestController
public class IngestController {
	private static Logger logger = LoggerFactory.getLogger(IngestController.class);

	 @Autowired
	private IngestDataRepository ingestRepository;
	 
	@RequestMapping("/ingests")
	public String allIngest() {
		 //retrieve all ingest
        Iterable<IngestData> allIngested = ingestRepository.findAll();
        
        if (allIngested == null) {
        	   logger.info(" No data in database ingest_data ");
           return "No data in database ingest_data ";
        }
        
        StringBuilder sb = new StringBuilder();
        int i=0;
        for (IngestData in : allIngested) {
        	   logger.info(" data from ingest_data table: i="+i + ", " + in.toString() + "\n");
        	   sb.append(" data from ingest_data table: i="+i++ + ", " + in.toString() + "\n");
        }
        
        return sb.toString();
        
	}
}
