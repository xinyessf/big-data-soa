package com.huanyu.spark.scala.game

import java.text.SimpleDateFormat

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zx on 2017/9/2.
  */
object GameKPI {

  def main(args: Array[String]): Unit = {

    //"2016-02-01"
    val startDate = "2016-09-01"
    //
    val endDate = "2017-12-02"

    val gameLog="spark-warehouse//wordcount//GameLog.txt"

    //查询条件
    val dateFormat1 = new SimpleDateFormat("yyyy-MM-dd")

    //查寻条件的的起始时间
    val startTime = dateFormat1.parse(startDate).getTime
    //查寻条件的的截止时间
    val endTime = dateFormat1.parse(endDate).getTime

    //Driver定义的一个simpledataformat
    val dateFormat2 = new SimpleDateFormat("yyyy年MM月dd日,E,HH:mm:ss")

    val conf = new SparkConf().setAppName("GameKPI").setMaster("local[*]")

    val sc = new SparkContext(conf)

    //以后从哪里读取数据
    val lines: RDD[String] = sc.textFile(gameLog)

    //整理并过滤
    val splited: RDD[Array[String]] = lines.map(line => line.split("[|]"))

    //按日期过过滤
    val filterd = splited.filter(fields => {
      val t = fields(0)
      val time = fields(1)
      val timeLong = dateFormat2.parse(time).getTime
      t.equals("1") && timeLong >= startTime && timeLong < endTime
    })

    //按类型过滤
//    val filterdByType = filterdByDate.filter(fields => {
//      val t = fields(0)
//      t.equals("1")
//    })

    val dnu = filterd.count()

    println(dnu)

    sc.stop()


  }

}
