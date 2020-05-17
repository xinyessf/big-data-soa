package com.huanyu.spark.scala.game

import java.text.SimpleDateFormat

import org.apache.commons.lang3.time.FastDateFormat

/**
  * Created by zx on 2017/9/2.
  */
object FilterUtilsV4{

  //如果object使用了成员变量，那么会出现线程安全问题，因为object是一个单例，多线程可以同时调用这个方法
  //val dateFormat = new SimpleDateFormat("yyyy年MM月dd日,E,HH:mm:ss")
  //FastDateFormat是线程安全的
  val dateFormat = FastDateFormat.getInstance("yyyy年MM月dd日,E,HH:mm:ss")


  def filterByType(fields: Array[String], tp: String) = {
    //
    val _tp = fields(0)
    _tp == tp
  }

  def filterByTime(fields: Array[String], startTime: Long, endTime: Long) = {
    val time = fields(1)
    val timeLong = dateFormat.parse(time).getTime
    timeLong >= startTime && timeLong < endTime
  }
}
