package com.huanyu.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 1.读取spark-warehouse下wordcount的aa.txt数据
 * 2.统计每个单词出现的次数,并且打印出来
 */
public class JavaWordCount {

    public static void main(String[] args) {
        //参数1 F:\\wordcount\\input\\a.txt
        //参数2 F:\\wordcount\wordLocal
        SparkConf conf = new SparkConf().setAppName("JavaWordCount").setMaster("local[*]");
        //创建sparkContext
        System.setProperty("hadoop.home.dir", "F:\\bigData\\hadoop-2.8.1");

        // 在代码中设置JVM系统参数，用于给job对象来获取访问HDFS的用户身份
        // System.setProperty("HADOOP_USER_NAME", "root");

        JavaSparkContext jsc = new JavaSparkContext(conf);
        //指定以后从哪里读取数据F:\wordcount\input
        JavaRDD<String> lines = jsc.textFile("spark-warehouse//wordcount//aa.txt");
        //切分压平
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String line) throws Exception {
                return Arrays.asList(line.split(" ")).iterator();
            }
        });
        //将单词和一组合在一起
        JavaPairRDD<String, Integer> wordAndOne = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<>(word, 1);
            }
        });

        //聚合
        JavaPairRDD<String, Integer> reduced = wordAndOne.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        //调换顺序
        JavaPairRDD<Integer, String> swaped = reduced.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> tp) throws Exception {
                //return new Tuple2<>(tp._2, tp._1);
                return tp.swap();
            }
        });

        //排序
        JavaPairRDD<Integer, String> sorted = swaped.sortByKey(false);

        //调整顺序
        JavaPairRDD<String, Integer> result = sorted.mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<Integer, String> tp) throws Exception {
                return tp.swap();
            }
        });

        //将数据保存到hdfs
        result.saveAsTextFile("F:\\wordcount\\wordcount\\local");

        //释放资源
        jsc.stop();


    }
}
