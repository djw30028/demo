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
		
		//3. get file
		//https://bi-hadoop-prod-4017.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/user/michaelw/clearleap/hello.txt?op=GETFILESTATUS
		int code = service.verifyFile("myhello.txt");
		System.out.println(" code = " + code);
		
		//4. delete file
		service.deleteFile("myhello.txt");
		
		//4. copy local file
		String localFile = "/Users/michaelwang/temp";
		String fileName = "myhello.txt";
		service.copyLocalFileToHadoop(localFile, fileName);
		
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
