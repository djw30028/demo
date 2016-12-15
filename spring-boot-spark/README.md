# Simple example of SPARK


# Installation
brew install apache-spark

# Spark setup in .bash_profile [install with brew]
export SPARK_HOME=/usr/local/Cellar/apache-spark/2.0.2/libexec
export PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME/sbin

# Quick test of installation: [demo/spring-boot-spark directory]
$ spark-shell <br>
scala> val textFile = sc.textFile("README.md") <br>
scala> textFile.count() <br>

# Test local inside IntelliJ without start Spark
JavaWordCountLocalMaster

Note: no need to start Spark as it is set in the code: .master("local")

# Start/Stop Spark in standalone
start-all.sh
stop-all.sh

# Spark console
http://localhost:8080/

Find URL in the console, something like: <br>
URL: spark://michaels-mbp-3.corp.clearleap.com:7077


# Submit Application
spark-submit --class "com.michaelw.spark.JavaWordCount" --master spark://michaels-mbp-3.corp.clearleap.com:7077 /Users/michaelwang/project/mytechzone/demo/spring-boot-spark/target/spring-boot-spark-0.0.1-SNAPSHOT.jar

spark-submit --class "com.michaelw.spark.JavaWordCount" --master spark://michaels-mbp-3.corp.clearleap.com:7077 /Users/michaelwang/project/mytechzone/demo/spring-boot-spark/target/spring-boot-spark-0.0.1-SNAPSHOT.jar /Users/michaelwang/project/mytechzone/demo/spring-boot-spark/README.md

# Check spark console http://localhost:8080/ to see the application submitted

# Note:
The jar file should not be Spring fat jar, as the class is not find within the fat jar.

```
  Comment out following line to remove fat jar
  <plugin>
	  <groupId>org.springframework.boot</groupId>
	  <artifactId>spring-boot-maven-plugin</artifactId>
  </plugin>
```

