package com.huanyu.spark.scala.game

import java.text.SimpleDateFormat

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zx on 2017/9/2.
  */
object GameKPIV2 {

  def main(args: Array[String]): Unit = {

    //"2016-02-01"
    val startDate = args(0)
    //"2016-02-02"
    val endDate = args(1)

    val dateFormat1 = new SimpleDateFormat("yyyy-MM-dd")

    val startTime = dateFormat1.parse(startDate).getTime

    val endTime = dateFormat1.parse(endDate).getTime


    val conf = new SparkConf().setAppName("GameKPI").setMaster("local[4]")

    val sc = new SparkContext(conf)

    //以后从哪里读取数据
    val lines: RDD[String] = sc.textFile(args(2))

    //整理并过滤
    val splited: RDD[Array[String]] = lines.map(line => line.split("[|]"))


    //按日期过过滤
    val filteredByType = splited.filter(fields => {
      //一个Task中会创建很多的FilterUtils实例，因为每处理一条就会创建一个实例
      val fu = new FilterUtilsV3
      fu.filterByType(fields,"1")
    })

    val filtered = filteredByType.filter(fields => {
      val fu = new FilterUtilsV3
      fu.filterByTime(fields, startTime, endTime)
    })

    val dnu = filtered.count()

    println(dnu)

    sc.stop()


  }

}
