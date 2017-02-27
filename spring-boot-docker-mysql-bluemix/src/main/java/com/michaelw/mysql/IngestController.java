package com.michaelw.mysql;

import java.util.Map;

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
	public String allIngest(Map<String, Object> model) {
		 //retrieve all ingest
        Iterable<IngestData> allIngested = ingestRepository.findAll();
        
        StringBuilder sb = new StringBuilder();
        if (allIngested == null) {
        	   logger.info(" No data in database ingest_data ");
           sb.append("No data in database ingest_data ");
        }
        else {
        	   int i=0;
           for (IngestData in : allIngested) {
           	   logger.info(" data from ingest_data table: i="+i + ", " + in.toString() + "<br>");
            	   sb.append(" data from ingest_data table: i="+i++ + ", " + in.toString() + "<br>");
           }
        }
       
        model.put("ingestAll", sb.toString());
        model.put("allIngested", allIngested);
        
        //TODO: jsp
        //return "show_ingest";
        
        return sb.toString();
        
	}
}
