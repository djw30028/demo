package com.michaelw.hadoop;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;


/**
 * verify:
 *http://localhost:50070/explorer.html#/foo
 * Created by michaelwang on 12/14/16.
 */
public class CopyLocalFileToHDFS {

    public void copyLocalToHDFS(String source, String dest) throws Exception {

        //1. Input stream for the file in local file system to be written to HDFS
        InputStream in = new BufferedInputStream(new FileInputStream(source));

        //2. Get configuration of Hadoop system
        String hadoopHome = System.getenv("HADOOP_HOME");
        System.out.println(" hadoopHome="+hadoopHome);
        Configuration conf = new Configuration();
        conf.addResource(new Path(hadoopHome + "/libexec/etc/hadoop/core-site.xml"));

        System.out.println("Connecting to -- "+conf.get("fs.defaultFS"));
        System.out.println("fs.default.name : - " + conf.get("fs.default.name"));

        //3. Destination file in HDFS
        Path outPath = new Path(dest);
        FileSystem fs = FileSystem.get(URI.create(dest), conf);

        //OutputStream out = fs.create(outPath);
        OutputStream out=fs.create(new Path(dest),new Progressable(){
            public void progress(){
                System.out.print("*");
            }
        });

        //4. Copy file from
        // local to HDFS
        IOUtils.copyBytes(in, out, 4096, true);

        System.out.println(dest + " copied to HDFS");
    }

    public static void main(String[] args) {
        System.out.println(" ---- CopyLocalFileToHDFS -----");

        String source = "/tmp/hadoop_install.txt";
        String dest = "hdfs://localhost:9000/foo/hadoop_install.txt";
        //String dest = "hdfs://192.168.0.103:9000/foo/hadoop_install.txt";
        //String dest = "hdfs://michaels-mbp-3.corp.clearleap.com:9000/foo/hadoop_install.txt";

        try {
            new CopyLocalFileToHDFS().copyLocalToHDFS(source, dest);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
