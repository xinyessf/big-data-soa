package com.huanyu.spark.scala.game

import java.text.SimpleDateFormat

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zx on 2017/9/2.
  */
object GameKPIV4 {

  def main(args: Array[String]): Unit = {

    //"2016-02-01"
    val startDate = args(0)
    //"2016-02-02"
    val endDate = args(1)

    val dateFormat1 = new SimpleDateFormat("yyyy-MM-dd")

    val startTime = dateFormat1.parse(startDate).getTime

    val endTime = dateFormat1.parse(endDate).getTime


    val conf = new SparkConf().setAppName("GameKPI").setMaster("local[2]")

    val sc = new SparkContext(conf)

    //以后从哪里读取数据
    val lines: RDD[String] = sc.textFile(args(2))

    //整理并过滤
    val splited: RDD[Array[String]] = lines.map(line => line.split("[|]"))


    //按日期过过滤
//    val filteredByType = splited.filter(fields => {
//      //如果FilterUtilsV4是一个object，把FilterUtilsV4写在函数内部，它是在Executor中被初始化的
//      //FilterUtilsV4是在一个Executor进程中是单例的
//      FilterUtilsV4.filterByType(fields, "1")
//    })

    val filtered = splited.filter(fields => {
      FilterUtilsV4.filterByTime(fields, startTime, endTime)
    })

    val dnu = filtered.count()

    println(dnu)

    sc.stop()


  }

}
