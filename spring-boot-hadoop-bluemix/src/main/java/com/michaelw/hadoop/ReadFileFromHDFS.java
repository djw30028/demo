package com.michaelw.hadoop;

import java.io.*;
import java.util.*;
import java.net.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.fs.FileSystem;

/**
 *
 * hdfs dfs -put /tmp/helloWorld.txt /foo/helloWorld.txt    --> source[local] dest [hadooop]
 * Created by michaelwang on 12/14/16.
 */
public class ReadFileFromHDFS {
    public void readFromHDFS(String hdfsFilePath) throws Exception {
        System.out.println(" hdfsFilePath="+hdfsFilePath);

        String hadoopHome = System.getenv("HADOOP_HOME");
        System.out.println(" hadoopHome="+hadoopHome);

        Configuration conf = new Configuration();


        // Need to get the location of HDFS
        conf.addResource(new Path(hadoopHome + "/libexec/etc/hadoop/core-site.xml"));


        System.out.println("Connecting to -- "+conf.get("fs.defaultFS"));
        System.out.println("fs.default.name : - " + conf.get("fs.default.name"));

        Path pt = new Path(hdfsFilePath);
        FileSystem fs = FileSystem.get(conf);
        BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
        String line;
        line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }

    }

    public static void main(String[] args) {
        System.out.println(" ---- ReadFileFromHDFS -----");

        //String hdfsFilePath = "hdfs://192.168.0.103:9000/foo/helloWorld.txt";
        String hdfsFilePath = "hdfs://localhost:9000/foo/helloWorld.txt";

        try {
            new ReadFileFromHDFS().readFromHDFS(hdfsFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
