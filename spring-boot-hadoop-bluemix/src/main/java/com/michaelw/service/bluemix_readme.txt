https://hadoop.apache.org/docs/r1.0.4/webhdfs.html

Create new Cluster:  myclearflowcluster    
Name: myclearflowcluster     
UserName: michaelw
Password: Bluemix123456789

---
## Env
SSH Host:   bi-hadoop-prod-4165.bi.services.us-south.bluemix.net   
Hive URL: jdbc:hive2://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:10000/;ssl=true;   
Web HDFS URL: https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/   

Dashboard:  ambariUrl    
* Entry point
https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:9443/#/main/dashboard/metrics    
* Directory
https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/hdfs/explorer.html#/user/michaelw   

## SSH access
$ ssh  michaelw@bi-hadoop-prod-4165.bi.services.us-south.bluemix.net
Bluemix123456789

## DOC
https://github.com/IBM-Bluemix/BigInsights-on-Apache-Hadoop/blob/master/examples

===================================================
== org.apache.hadoop.hdfs.web.resources.GetOpParam.Op.DELETE
//1. list root directory:
curl -v -s -i -k -u michaelw:Bluemix123456789 -X GET https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/?op=LISTSTATUS
curl -v -s -i -k -u michaelw:Bluemix123456789 -X GET https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1//user/michaelw?op=LISTSTATUS

//2. create directory
curl -s -i -k -u michaelw:Bluemix123456789 -X PUT 'https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/home/michaelw/test?op=MKDIRS'

//3. Delete directory
curl -s -i -k -u michaelw:Bluemix123456789 -X DELETE 'https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/user/michaelw/clearflow?op=DELETE&recursive=true'

//4. Upload local file to Hadoop-> local file: LICENSE, hadoop file: user/michaelw/clearflow/mytest.txt
curl -s -i -k -u michaelw:Bluemix123456789 -X PUT 'https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/user/michaelw/clearflow/mytest1.txt?op=CREATE'

curl -s -i -k -u michaelw:Bluemix123456789  -T LICENSE -X PUT https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/data/v1/webhdfs/v1/user/michaelw/clearflow/mytest.txt?_=AAAACAAAABAAAADAkcQ2wFnS9TFXESCrn7MWanprDDv918G13MHtgYwkJAlrY7iRw6oPeOsDdGE3mVMOOCgZj-UNNRKYA22nysqUnxSLpbPAPCD0t8A8wBQ1aDZB-LIc1beT5gcWR6OaXUCU9VNzyQxhCVQyBkRLA3v_cncSvNPqaqESqtX-eTf4c880g8jkyb_uqMJtdLGAIDt28q7MEN9iF8sBtKyVI79Fdkj5UDxXmZQfVsy0MILjiWfSeak6iOjU_coXnaFnNFiiimpi1jLM6SWGB_15UspqaMO85GnGPU_1

Note: LICENSE is local file and 

https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/hdfs/explorer.html#/user/michaelw/clearflow
