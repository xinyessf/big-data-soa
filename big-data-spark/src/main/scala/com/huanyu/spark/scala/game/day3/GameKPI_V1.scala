package com.huanyu.spark.scala.game.day3

import java.text.SimpleDateFormat

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by laozhao on 2017/8/5.
  * 计算游戏的关键指标
  *
  */
object GameKPI_V1 {

  def main(args: Array[String]): Unit = {

    //条件的SimpleDateFormat
    val conditionSdf = new SimpleDateFormat("yyyy-MM-dd")
    val startTime = conditionSdf.parse("2016-02-01").getTime
    val endTime = conditionSdf.parse("2016-02-02").getTime
    //如果在Driver端new出一个SimpleDateFormat实例，每个Task内部都会一个独立的SimpleDateFormat实例
    //如何用一个SimpleDateFormat实例？定义一个object类型的工具类，让SimpleDateFormat作为一个成员变量
    val sdf = new SimpleDateFormat("yyyy年MM月dd日,E,HH:mm:ss")

    val conf = new SparkConf().setAppName("GameKPI").setMaster("local[4]")

    val sc = new SparkContext(conf)

    val lines: RDD[String] = sc.textFile("/Users/zx/Desktop/user.log")

    val filteredData = lines.filter(line => {
      val fileds = line.split("[|]")
      val tp = fileds(0)
      val timeStr = fileds(1);
      //将字符串转换成Date
      val date = sdf.parse(timeStr)
      val timeLong = date.getTime
      timeLong >= startTime && timeLong < endTime
    })
    val r = filteredData.collect()

    println(r.toBuffer)



  }

}
