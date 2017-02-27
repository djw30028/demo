package com.michaelw.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * View Hadoop direcoty/filse
 * https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/hdfs/explorer.html#/
 * 
 * @author michaelwang
 *
 */
public class BluemixBigInsightService {
	private final static String X_Api_Key = "X-Api-Key";
	private final static String X_Query_Key = "X-Query-Key";
	private final static String Q_Query_Value = "OhiS1eSKDbsYlSfDS3X5AJKHGNv0PvWV";

	private final static String HOST = "https://bi-hadoop-prod-4017.bi.services.us-south.bluemix.net:8443";
	private final static String USER_NAME = "michaelw";
	private final static String PASS_WORD = "Bluemix123456789";
	
	private CloseableHttpClient getAuthCloseableHttpClient() {
		return getAuthCloseableHttpClient(false);
	}

	private CloseableHttpClient getAuthCloseableHttpClient(boolean disableRedirect) {
		// 1. Set credentials
		CredentialsProvider provider = new BasicCredentialsProvider();
		Credentials credentials = new UsernamePasswordCredentials(USER_NAME, PASS_WORD);
		provider.setCredentials(AuthScope.ANY, credentials);

		// 2. Bind credentialsProvider to httpClient
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setDefaultCredentialsProvider(provider);
		
		if (disableRedirect) {
			httpClientBuilder.setRedirectStrategy(new DefaultRedirectStrategy() {
				 @Override
				    public boolean isRedirected(
				            final HttpRequest request,
				            final HttpResponse response,
				            final HttpContext context) throws ProtocolException {
				        
					 System.out.println(" \n\n **** setRedirectStrategy ");
				        final int statusCode = response.getStatusLine().getStatusCode();
				        final String method = request.getRequestLine().getMethod();
				        final Header locationHeader = response.getFirstHeader("location");
				        switch (statusCode) {
				        case HttpStatus.SC_MOVED_TEMPORARILY:
				            return isRedirectable(method) && locationHeader != null;
				        case HttpStatus.SC_MOVED_PERMANENTLY:
				        //case HttpStatus.SC_TEMPORARY_REDIRECT:
				        //    return isRedirectable(method);
				        case HttpStatus.SC_SEE_OTHER:
				            return true;
				        default:
				            return false;
				        } //end of switch
				    }
			});

			//httpClientBuilder.disableRedirectHandling();
		}
		CloseableHttpClient httpClient = httpClientBuilder.build();

		return httpClient;
	}

	public Synthetic getDirectory() throws Throwable {

		String restUrl = "/gateway/default/webhdfs/v1/?op=LISTSTATUS";
		restUrl = HOST + String.format(restUrl);
		System.out.println(" json restUrl: " + restUrl);

		// 1. Set credentials
		CredentialsProvider provider = new BasicCredentialsProvider();
		Credentials credentials = new UsernamePasswordCredentials(USER_NAME, PASS_WORD);
		provider.setCredentials(AuthScope.ANY, credentials);

		// 2. Bind credentialsProvider to httpClient
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setDefaultCredentialsProvider(provider);
		CloseableHttpClient httpClient = httpClientBuilder.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

		// 3. create restTemplate
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(factory);

		// 4. restTemplate execute
		String url = restUrl;
		String json = restTemplate.getForObject(url, String.class);

		// HttpGet request = new HttpGet(restUrl);

		// HttpResponse response = client.execute(request);
		// String json = EntityUtils.toString(response.getEntity());
		System.out.println(" json response: " + json);
		ObjectMapper mapper = new ObjectMapper();

		return mapper.readValue(json, Synthetic.class);
	}

	public void creatDirectory() throws Throwable {
		String restUrl = "/gateway/default/webhdfs/v1/user/michaelw/clearleap?op=MKDIRS";
		restUrl = HOST + String.format(restUrl);
		System.out.println(" json restUrl: " + restUrl);

		CloseableHttpClient client = getAuthCloseableHttpClient();

		HttpPut request = new HttpPut(restUrl);
		HttpResponse response = client.execute(request);
		System.out.println("status=" + response.getStatusLine().getStatusCode());

		// check
		// https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/hdfs/explorer.html#/user/michaelw
	}

	public int verifyFile(String fileName) throws Throwable {
		//http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=GETFILESTATUS"
		///user/michaelw/clearflow/hello.txt
		String restUrl = "/gateway/default/webhdfs/v1/user/michaelw/clearflow/%s?op=GETFILESTATUS";
		restUrl = HOST + String.format(restUrl, fileName);
		System.out.println(" json restUrl: " + restUrl);
		
		CloseableHttpClient client = getAuthCloseableHttpClient();
		HttpGet request = new HttpGet(restUrl);
		HttpResponse response = client.execute(request);
		System.out.println("status=" + response.getStatusLine().getStatusCode());
		return response.getStatusLine().getStatusCode();
	}
	
	public int deleteFile(String fileName) throws Throwable {
		String restUrl = "/gateway/default/webhdfs/v1/user/michaelw/clearflow/%s?op=DELETE";
		restUrl = HOST + String.format(restUrl, fileName);
		System.out.println(" json restUrl: " + restUrl);
		CloseableHttpClient client = getAuthCloseableHttpClient();
		HttpDelete request = new HttpDelete(restUrl);
		HttpResponse response = client.execute(request);
		System.out.println("status=" + response.getStatusLine().getStatusCode());
		return response.getStatusLine().getStatusCode();
	}
	/**
	 * https://hadoop.apache.org/docs/r1.0.4/webhdfs.html#CREATE
	 * @throws Throwable
	 */
	public void copyLocalFileToHadoop(String source, String fileName) throws Throwable {
		 String path = source + "/" + fileName;
		 String data = "";
		 
		//1. Input stream for the file in local file system to be written to HDFS
        InputStream in = new BufferedInputStream(new FileInputStream(path));
        data = IOUtils.toString(in);
        
        System.out.println(" data =" + data);
		//String fileName = "testinput.txt";
       /*
        ClassPathResource cpr = new ClassPathResource(path);
      
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        String restUrl = "/gateway/default/webhdfs/v1/user/michaelw/clearflow/" + fileName + "?op=CREATE";
		restUrl = HOST + String.format(restUrl);
		System.out.println(" json restUrl: " + restUrl);

		CloseableHttpClient client = getAuthCloseableHttpClient(true);

		// 1. get location url before redirect: 307
		HttpPut request = new HttpPut(restUrl);
		HttpResponse response = client.execute(request);
		
		System.out.println("status [307]=" + response.getStatusLine().getStatusCode());
		final Header locationHeader = response.getFirstHeader("location");
		final String location = locationHeader.getValue();
		System.out.println(" localtion = " + location);
		
		String json = EntityUtils.toString(response.getEntity());
		System.out.println("result = " + json);

		//2. put data from input file to Hadoop
		CloseableHttpClient clientLoc = getAuthCloseableHttpClient();
		HttpPut requestLoc = new HttpPut(location);
		StringEntity stringEntity = new StringEntity(data, ContentType.TEXT_PLAIN);
		requestLoc.setEntity(stringEntity);
		
		HttpResponse responseLoc = clientLoc.execute(requestLoc);
		
		System.out.println("statusLoc [201]=" + responseLoc.getStatusLine().getStatusCode());
		
		//3. Well DONE -> check from console:
		//https://bi-hadoop-prod-4017.bi.services.us-south.bluemix.net:8443/gateway/default/hdfs/explorer.html#/user/michaelw/clearflow
		
		
	}

}
