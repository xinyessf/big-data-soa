package com.huanyu.spark.scala.game

import java.text.SimpleDateFormat

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zx on 2017/9/2.
  */
object GameKPIV3 {

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

    //FilterUtils是在Driver端创建的
    val fu = new FilterUtilsV3 with Serializable

    //按日期过过滤
//    val filteredByType = splited.filter(fields => {
//      fu.filterByType(fields, "1")
//    })

    val filtered = splited.filter(fields => {
      fu.filterByTime(fields, startTime, endTime)
    })

    val dnu = filtered.count()

    println(dnu)

    sc.stop()


  }

}
