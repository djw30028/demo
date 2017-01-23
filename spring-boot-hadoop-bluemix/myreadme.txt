Java program to copy a file from local disk to Hadoop Cluster with progress bar
Posted on September 6, 2014 by Pandian Ramaiah
You need to look at the following blog posts to understand this post in a better way.

Copying the File to HDFS file system
A java program to read the file from HDFS
A java program to read the file from HDFS – 2
Java program to read a file from Hadoop Cluster 2 (with file seek)
We copied the file using CLI in example 1 given above. Lets use java to do the same.

Here comes the full java program to copy the local file to HDFS. You can get it from https://github.com/tomwhite/hadoop-book/blob/3e/ch03/src/main/java/FileCopyWithProgress.java

import java.io.*;
import java.net.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;

public class FileCopyWithProgress{
    public static void main(String [] args) throws Exception{
        String localSrc=args[0];
        String dst=args[1];
        InputStream in=new BufferedInputStream(new FileInputStream(localSrc));
        Configuration conf=new Configuration();
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        OutputStream out=fs.create(new Path(dst),new Progressable(){
            public void progress(){
                System.out.print(“*”);
            }
        });
        IOUtils.copyBytes(in, out, 4096, true);
    }
}

Note, it use Progressable Interface. For each 65KB, you will see a * getting printed. Here comes the compilation.

$ bin/hadoop com.sun.tools.javac.Main FileCopyWithProgress.java
Lets execute it now.

$ bin/hadoop FileCopyWithProgress /home/pandian/anthem.txt hdfs://localhost:9000/user/pandian/anthem2.hadoop.txt

Configuration conf = new Configuration();
FileSystem fs = FileSystem.get(new URI("webhdfs://NN_HOST:50070"), conf);

The REST API instead is documented at
http://archive.cloudera.com/cdh4/cdh/4/hadoop/hadoop-project-dist/hadoop-hdfs/WebHDFS.html
.

Configuration conf = new Configuration();
FileSystem fs = FileSystem.get(new URI("webhdfs://NN_HOST:50070"), conf);

The REST API instead is documented at
http://archive.cloudera.com/cdh4/cdh/4/hadoop/hadoop-project-dist/hadoop-hdfs/WebHDFS.html
.