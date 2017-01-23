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

---
## Doc:

* WebHDFS   
http://archive.cloudera.com/cdh4/cdh/4/hadoop/hadoop-project-dist/hadoop-hdfs/WebHDFS.html

* Apache Knox gateway overview

Example:
https://github.com/IBM-Bluemix/BigInsights-on-Apache-Hadoop/blob/master/examples






===================
https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default

curl -v -s -i -k -u michaelw:Bluemix123456789 -X GET 'https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/?op=LISTSTATUS'

curl -v -s -i -k -u michaelw:Bluemix123456789 -X GET https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/user/michaelw?op=LISTSTATUS



## Create directory:
curl -s -i -k -u michaelw:Bluemix123456789 -X PUT 'https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/home/michaelw/test-1484859789?op=MKDIRS'

curl -s -i -k -u michaelw:Bluemix123456789 -X PUT 'https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/user/michaelw/clearflow?op=MKDIRS'

https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/hdfs/explorer.html#/user/michaelw  

## Delete directory
curl -s -i -k -u michaelw:Bluemix123456789 -X DELETE 'https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/user/michaelw/clearflow?op=DELETE&recursive=true'

https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/data/v1/webhdfs/v1/user/michaelw/test-1484860734

## Put file
curl -s -i -k -u michaelw:Bluemix123456789 -X PUT 'https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/user/michaelw/clearflow/mytest.txt?op=CREATE'

curl -s -i -k -u michaelw:Bluemix123456789 -X PUT 'https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/user/michaelw/clearflow/youtest2.txt?op=CREATE'

curl -s -i -k -u michaelw:Bluemix123456789  -T LICENSE -X PUT https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/data/v1/webhdfs/v1/user/michaelw/clearflow/mytest.txt?_=AAAACAAAABAAAADAkcQ2wFnS9TFXESCrn7MWanprDDv918G13MHtgYwkJAlrY7iRw6oPeOsDdGE3mVMOOCgZj-UNNRKYA22nysqUnxSLpbPAPCD0t8A8wBQ1aDZB-LIc1beT5gcWR6OaXUCU9VNzyQxhCVQyBkRLA3v_cncSvNPqaqESqtX-eTf4c880g8jkyb_uqMJtdLGAIDt28q7MEN9iF8sBtKyVI79Fdkj5UDxXmZQfVsy0MILjiWfSeak6iOjU_coXnaFnNFiiimpi1jLM6SWGB_15UspqaMO85GnGPU_1

curl -s -i -k -u michaelw:Bluemix123456789  -d "Good morning" -X PUT https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/data/v1/webhdfs/v1/user/michaelw/clearflow/youtest2.txt?_=AAAACAAAABAAAADAkcQ2wFnS9TFXESCrn7MWanprDDv918G13MHtgYwkJAlrY7iRw6oPeOsDdGE3mVMOOCgZj-UNNRKYA22nysqUnxSLpbPAPCD0t8A8wBQ1aDZB-LIc1beT5gcWR6OaXUCU9VNzyQxhCVQyBkRLA3v_cncSvNPqaqESqtX-eTf4c880g8jkyb_uqMJtdLGAIDt28q7MEN9iF8sBtKyVI79Fdkj5UDxXmZQfVsy0MILjiWfSeak6iOjU_coXnaFnNFiiimpi1jLM6SWGB_15UspqaMO85GnGPU_1


Note: LICENSE is local file and

https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/hdfs/explorer.html#/user/michaelw/clearflow

SoapUI:
Step 1: create hadoop file: youtest.txt with auth: michaelw:Bluemix123456789 https://bi-hadoop-prod-4165.bi.services.us-south.bluemix.net:8443/gateway/default/webhdfs/v1/user/michaelw/clearflow/youtest.txt?op=CREATE

michaelwang@ /Users/michaelwang/project/mypractice/BigInsights-on-Apache-Hadoop/examples/WebHdfsCurl
./gradlew myls
