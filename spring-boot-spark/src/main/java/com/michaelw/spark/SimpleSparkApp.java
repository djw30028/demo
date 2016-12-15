package com.michaelw.spark;

import org.apache.spark.api.java.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;

/**
 * Created by michaelwang on 12/15/16.
 */
public class SimpleSparkApp {
    public static void main(String[] args) {
        String logFile = "/Users/michaelwang/project/mytechzone/demo/spring-boot-spark/README.md"; // Should be some file on your system

        SparkConf conf = new SparkConf().setAppName("Simple Application");
        //SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local");


        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> logData = sc.textFile(logFile).cache();

        long numAs = logData.filter(new Function<String, Boolean>() {
            public Boolean call(String s) {
                return s.contains("a");
            }
        }).count();

        long numBs = logData.filter(new Function<String, Boolean>() {
            public Boolean call(String s) {
                return s.contains("b");
            }
        }).count();

        System.out.println("   \n ---- Lines with a: " + numAs + ", lines with b: " + numBs + "\n");

        sc.stop();
    }
}
