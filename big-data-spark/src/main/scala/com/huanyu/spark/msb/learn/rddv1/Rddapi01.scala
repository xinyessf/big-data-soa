package com.huanyu.spark.msb.learn.rddv1

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Rddapi01 {

  def main(args: Array[String]): Unit = {
    /**
      * 打印大于3 的值
      */
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("test")
    val sc = new SparkContext(sparkConf)
    sc.setLogLevel("ERROR")
    val dataRDD: RDD[Int] = sc.parallelize(List(1, 2, 3, 8, 3, 9))
    dataRDD.filter((x: Int) => {
      x > 3
    })
    val dataRDD2: RDD[Int] = dataRDD.filter(_ > 3)
    val list: Array[Int] = dataRDD2.collect()
    list.foreach(println)

    /**
      * 差集,并集,交集
      */
    println("-----------------")
    val rdd1: RDD[Int] = sc.parallelize(List(2, 36, 9, 23, 9))
    val rdd2: RDD[Int] = sc.parallelize(List(22, 1, 36, 9, 89))
    //rdd1减去rdd2
    val subtract: RDD[Int] = rdd1.subtract(rdd2)
    subtract.foreach(println)
    println("-----------------")
    //交集
    val subtract2: RDD[Int] = rdd1.intersection(rdd2)
    subtract2.foreach(println)
    //随机组合
    val cartesian: RDD[(Int, Int)] = rdd1.cartesian(rdd2)
    println("certesian-----------------")
    cartesian.foreach(println)
    println("rdd1-----------------")
    println(rdd1.partitions.size)
    println(rdd2.partitions.size)
    println("unitRdd-----------------")
    //并集
    val unitRDD: RDD[Int] = rdd1.union(rdd2)
    println(unitRDD.partitions.size)
    println("-----------------")
    unitRDD.foreach(println)

  }


}
