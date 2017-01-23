package com.michaelw.service;

public class BigsightTest {
	static BluemixBigInsightService service = new BluemixBigInsightService();
	
	/**
	 * https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/hdfs/explorer.html#/
	 * @throws Throwable
	 */
	private void process() throws Throwable {
		//1. view directory
		//service.getDirectory();
		
		//2. create new directory
		//service.creatDirectory();
		
		//3. copy file
		String localFile = "/Users/michaelwang/temp";
		service.copyLocalFileToHadoop(localFile);
		
		
	}
	public static void main(String[] args) {
		try {
			new BigsightTest().process();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
