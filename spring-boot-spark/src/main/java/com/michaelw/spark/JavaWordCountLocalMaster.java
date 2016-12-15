package com.michaelw.spark;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by michaelwang on 12/15/16.
 */
public class JavaWordCountLocalMaster implements Serializable {
    private static final Pattern SPACE = Pattern.compile(" ");

    public void wordCount(final String filePath) throws Exception {
        System.out.println(" path= " + filePath);

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaWordCount")
                .master("local")
                .getOrCreate();

        JavaRDD<String> lines = spark.read().textFile(filePath).javaRDD();

        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) {
                return Arrays.asList(SPACE.split(s)).iterator();
            }
        });

        JavaPairRDD<String, Integer> ones = words.mapToPair(
                new PairFunction<String, String, Integer>() {
                    @Override
                    public Tuple2<String, Integer> call(String s) {
                        return new Tuple2<>(s, 1);
                    }
                });

        JavaPairRDD<String, Integer> counts = ones.reduceByKey(
                new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer i1, Integer i2) {
                        return i1 + i2;
                    }
                });

        System.out.println("\n\n =================== \n");
        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?,?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
        spark.stop();
    }
    public static void main(String[] args) {
        String path = "/Users/michaelwang/project/mytechzone/demo/spring-boot-spark/README.md";
        try {
            new JavaWordCountLocalMaster().wordCount(path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
