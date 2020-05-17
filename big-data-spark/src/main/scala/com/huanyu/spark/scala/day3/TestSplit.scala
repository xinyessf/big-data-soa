package com.huanyu.spark.scala.day3
import java.net.URL

/**
  * Created by zx on 2017/10/8.
  */
object TestSplit {

  def main(args: Array[String]): Unit = {


    val line = "http://bigdata.edu360.cn/laozhao"

    //学科，老师
  val splits: Array[String] = line.split("/")
//
    val subject1 = splits(2).split("[.]")(0)
//
   val teacher1 = splits(3)
//
   println(subject1 + " " + teacher1)

    val index = line.lastIndexOf("/")

    val teacher = line.substring(index + 1)

    val httpHost = line.substring(0, index)

    val subject = new URL(httpHost).getHost.split("[.]")(0)

    println(teacher + ", " + subject)




  }
}
