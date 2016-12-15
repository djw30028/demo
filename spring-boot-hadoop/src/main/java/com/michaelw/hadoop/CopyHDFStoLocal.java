package com.michaelw.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Created by michaelwang on 12/15/16.
 */
public class CopyHDFStoLocal {

    public void copyHDFStoLocal(String source, String dest) throws Exception {
        String hadoopHome = System.getenv("HADOOP_HOME");
        System.out.println(" hadoopHome="+hadoopHome);

        Configuration conf = new Configuration();
        conf.addResource(new Path(hadoopHome + "/libexec/etc/hadoop/core-site.xml"));

        FileSystem fs = FileSystem.get(conf);

        fs.copyToLocalFile(new Path(source), new Path(dest));

        return;
    }

    public static void main(String[] args) {
        System.out.println(" ---- CopyHDFStoLocal -----");

        String dest = "/tmp/hadoop_install_2.txt";
        String source = "hdfs://localhost:9000/foo/hadoop_install.txt";

        try {
            new CopyHDFStoLocal().copyHDFStoLocal(source, dest);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
