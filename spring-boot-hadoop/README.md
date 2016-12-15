# Simple example of Hadoop

To run this project
```bash
mvn spring-boot:run
```


http://localhost:9090/v2/api-docs
http://localhost:9090/swagger-ui.html

```
Installation
```

# Hadoop setup in .bash_profile [install with brew]
export HADOOP_HOME=/usr/local/Cellar/hadoop/2.7.3<br>
alias hstart="/usr/local/Cellar/hadoop/2.7.3/sbin/start-dfs.sh;/usr/local/Cellar/hadoop/2.7.3/sbin/start-yarn.sh"<br/>
alias hstop="/usr/local/Cellar/hadoop/2.7.3/sbin/stop-yarn.sh;/usr/local/Cellar/hadoop/2.7.3/sbin/stop-dfs.sh"</br>

export PATH=$PATH:$HADOOP_HOME/bin

# setup hadoop hdfs node (directory), $HADOOP_HOME/libexec/etc/hadoop
hdfs-site.xml
```
<configuration>
  <property>
    <name>dfs.replication</name>
    <value>1</value>
   </property>
   <property>
      <name>dfs.namenode.name.dir</name>
      <value>$HOME/myapp/hadoop/hdfs/namenode</value>
   </property>
   <property>
      <name>dfs.datanode.data.dir</name>
      <value>$HOME/myapp/hadoop/hdfs/datanode</value>
   </property>
</configuration>
```

Other sittings are under cp-hadoop-conf directory


# Enable Remote Login for Macbook:
"System Preferences" -> "Sharing" -> Check "Remote Login"
verify: ssh localhost

# Start/Stop
Start: hstart <br/>
Stop:  hstop

# Monitor
Resource Manager: http://localhost:50070 <br/>
JobTracker: http://localhost:8088/ <br/>
Node Specific Info: http://localhost:8042/ <br/>
jps


$ jps <br/>
7618 DataNode <br/>
7774 SecondaryNameNode <br/>
7491 NameNode <br/>
8033 NodeManager <br/>
7898 ResourceManager <br/>
7676 Jps

# Format: $HOME/myapp/hadoop/hdfs/datanode
hdfs namenode -format

# Test with command line
hdfs dfs -mkdir /foo <br/>
hdfs dfs -ls /  --> list <br/>
hdfs dfs -put /tmp/helloWorld.txt /foo/helloWorld.txt    --> source[local] dest [hadooop] <br/>
hdfs dfs -get /foo/helloWorld.txt /tmp/helloWorldDownload.txt --> <br/>
hdfs dfs -ls /foo <br/>
http://localhost:50070/explorer.html#/foo <br/>


# Writing a file to HDFS
hadoop fs -copyFromLocal

# Java client to view HDFS and copy local file to HDFS
ReadFileFromHDFS <br>
CopyLocalFileToHDFS <br/>
CopyHDFStoLocal
