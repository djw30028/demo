package com.michaelw.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by michaelwang on 12/12/16.
 * compile: 
 * mvn clean package
 * deploy:
 * cf push myspringboot -p target/spring-boot-simple-0.0.1-SNAPSHOT.jar
 * run: 
 * https://myspringboot.mybluemix.net/
 * result:
 * XX Greetings from Spring Boot! - timeStr=16:39:10
 * 
 */
@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
    		//connecting postgreSQL
    	String timeStr = "UA";
    	 try{
    	      String[] serviceInfo = getServiceInfo();
    	      
    	      Class.forName("org.postgresql.Driver").newInstance();
    	      Connection conn = DriverManager.getConnection( serviceInfo[0], serviceInfo[1], serviceInfo[2] );
    	      Statement st = conn.createStatement();
    	      String sql = "SELECT NOW() AS pgTime ";
    	      ResultSet rs = st.executeQuery(sql);
    	      rs.next();
    	      //System.out.println( "PG Time: " + rs.getTime(1) ); 
    	      timeStr = rs.getTime(1).toString();
    	      rs.close();
    	      st.close();
    	      conn.close();
    	      System.out.println( "Success!!" ); 
    	    }catch(Exception e){
    	      System.out.println( "Failed: " + e.getMessage() ); 
    	      e.printStackTrace();
    	    }
        return "XX Greetings from Spring Boot! - timeStr=" + timeStr;
    }

    /*
     {
    "compose-for-postgresql": [
        {
            "credentials": {
                "db_type": "postgresql",
                "name": "bmix_dal_yp_3e771858_e4b0_40b0_9c57_a0378f447251",
                "uri_cli": "psql \"sslmode=require host=sl-us-dal-9-portal.0.dblayer.com port=18084 dbname=compose user=admin\"",
                "ca_certificate_base64": "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSURnVENDQW1tZ0F3SUJBZ0lFV0didmVEQU5CZ2txaGtpRzl3MEJBUTBGQURCQ01VQXdQZ1lEVlFRREREZDMKWldrdWQyRnVaeklyWW0xcGVFQnBZbTB1WTI5dExXVTVOekJqTkdZek5XTTNOMlZqT0dObE5tUmpaRFJrTVRKbQpZVE0xTXpjeU1CNFhEVEUyTVRJek1ESXpNell5TkZvWERUTTJNVEl6TURJek1EQXdNRm93UWpGQU1ENEdBMVVFCkF3dzNkMlZwTG5kaGJtY3lLMkp0YVhoQWFXSnRMbU52YlMxbE9UY3dZelJtTXpWak56ZGxZemhqWlRaa1kyUTAKWkRFeVptRXpOVE0zTWpDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBTHhIOUpwaQpGTTJCWVBhZkRwb0RaNUZhZWNVRitQR2J0UUgzZ1ZzT2dRYzlDNTJTd1FHMW5JQjNVSDBwV2Iyb2hhRmNsQTR6CjMvRmVVL25SUncxSVhvc2NVRjVpcmljVnJ3N0lIa2hpdXBSaWlFRDdnM0tTaENDelFrckN3amRETktNVEFWZjQKU3kvdjdVMlJtYjFPSW5iSlpjajJGdmtYdVdBc0lLaS9kZnhYKzV3N2JOS29GbURBM1djZ2JlMWpiN0ZSaThwZwo1WXNaSzZsay83NlA0bGNtYStnWVFjVTM0cER5UlV2dHlNalJvdzNlZzJrdkRSOXZhYlpJaVNLbndEdEUrQTVLCllsUEFWMFBySTc2VDJteGp3c3BHSGY3UUxxQWZIRHJJRk5JOVdHcHRLQUpIbGFGRGxnQzFFWWFkWUM2cUZESU0KZHIwbk5MNWNHYjhEZWJNQ0F3RUFBYU4vTUgwd0hRWURWUjBPQkJZRUZHM1lyQVNHVFUrdVRuU1NlTzNSNlo1awo0SVpDTUE0R0ExVWREd0VCL3dRRUF3SUNCREFkQmdOVkhTVUVGakFVQmdnckJnRUZCUWNEQVFZSUt3WUJCUVVICkF3SXdEQVlEVlIwVEJBVXdBd0VCL3pBZkJnTlZIU01FR0RBV2dCUnQyS3dFaGsxUHJrNTBrbmp0MGVtZVpPQ0cKUWpBTkJna3Foa2lHOXcwQkFRMEZBQU9DQVFFQXBMWE5yaTN3MUg2U0VWQkJsbHh1bm9VWnlpUTRxQXh5d0tHeQppTU1nRjVKR2crZURGSk5DOEYrVDVPdTdhd0lnSDYzMUF1VHdObVRBbVY4ZWlkOWpNTndZTlNVTFhYaUs2SnpNCmVUaW1pcTRVdnQ1SmtDc1N5eVkrb2kzeTVESk10K2xXckFmbjJWNjZkVVUxOElodk42ZS9QZmdWeHA2WDlOR20KN3VzNjBNZkhncDlyVk5uaVVLU3ZiQVFpK3JURWFYYW16dTE2Q1RUUzdPNitwRkxtQmxuRFUvaysvNmpPaUdZMwpaWExFTStLYVBRSkpjbXJlZ1hBaGNaUklBS3ZOSTVuQm1qbDFPWFRmQUtLbFdGNENvZDMvKzVObVZFREtHck1TCjJoellJZi9VRFJFQnAwODRHUWdtdFdJaklnV1A3RkVtaWRTWlpwQThnU3hoaWlML3BBPT0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQo=",
                "deployment_id": "5866ef74ef05c000130000cf",
                "uri": "postgres://admin:CLOCHEBPQANRJXHV@sl-us-dal-9-portal.0.dblayer.com:18084/compose"
            },
            "syslog_drain_url": null,
            "label": "compose-for-postgresql",
            "provider": null,
            "plan": "Standard",
            "name": "postgresql01",
            "tags": [
                "big_data",
                "data_management",
                "ibm_created"
            ]
        }
    ]
}
m key = label, value=compose-for-postgresql
m key = name, value=postgresql01
m key = tags, value=[big_data, data_management, ibm_created]
m key = plan, value=Standard
m key = credentials, value={db_type=postgresql, 

     */
    public String[] getServiceInfo() throws Exception {
    	    System.out.println("\n ---- xxxx getServiceInfo -----");
        
    	    CloudEnvironment environment = new CloudEnvironment();
    	    List<Map<String,Object>> listmap = environment.getServices();
    	    for (Map<String, Object> lmap : listmap) {
    	    	    for (Map.Entry<String, Object> m : lmap.entrySet()) {
    	    	    	     System.out.println(" m key = " + m.getKey() + ", value=" + m.getValue().toString());
    	    	    }
    	    }
    	    List<Map<String, Object>> envMap = environment.getServiceDataByLabels("compose-for-postgresql");
    	    System.out.println(" k size = "+ envMap.size());
    	    
    	    for (Map<String, Object> lmap : envMap) {
	    	    for (Map.Entry<String, Object> m : lmap.entrySet()) {
	    	    	     System.out.println(" e key = " + m.getKey() + ", value=" + m.getValue().toString());
	    	    }
	    }
    	    /*
        if ( environment.getServiceDataByLabels("compose-for-postgresql").size() == 0 ) {
            throw new Exception( "No PostgreSQL service is bund to this app!!" );
        }

        String[] info = new String[3];
        Map credential = (Map)((Map)environment.getServiceDataByLabels("compose-for-postgresql").get(0)).get( "credentials" );
        */
    	    String[] info = new String[3];
    	    Map<String, Object> credential = (Map<String, Object>)environment.getServices().get(0).get("credentials");
        String host = (String)credential.get( "host" );
        Integer port = (Integer)credential.get( "port" );
        String db = (String)credential.get( "name" );
        String username = (String)credential.get( "username" );
        String password = (String)credential.get( "password" );

        info[0] = "jdbc:postgresql://" + host + ":" + port + "/" + db;
        info[0] = "jdbc:postgresql://sl-us-dal-9-portal.0.dblayer.com:18084/compose";
        	
        info[1] = "admin"; //username;
        info[2] = "CLOCHEBPQANRJXHV"; //password;
        System.out.println(" i info[0]=" + info[0]);
        System.out.println(" i info[1]=" + info[1]);
        System.out.println(" i info[2]=" + info[2]);
        return info;
      }
}