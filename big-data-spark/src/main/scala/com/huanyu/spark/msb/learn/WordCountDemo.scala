package com.huanyu.spark.msb.learn

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCountDemo {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()

    conf.setAppName("wordCount").setMaster("local[*]")


    val context = new SparkContext(conf)

    //单词统计

    val fileRDD: RDD[String] = context.textFile("file:///E:\\MySummaryStudy\\big-data-soa\\big-data-spark\\data\\testwordCount.txt")
    val words: RDD[String] = fileRDD.flatMap((x: String) => {
      x.split(" ")
    })
    val partWord: RDD[(String, Int)] = words.map((x: String) => {
      new Tuple2(x, 1)
    })


    val res:RDD[(String,Int)] = partWord.reduceByKey((x: Int, y: Int) => {
      x + y
    })
    res.foreach(println)


  }

}
