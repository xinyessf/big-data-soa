package com.huanyu.spark.scala.day7

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by zx on 2017/9/18.
  */
object JsonDataSource {

  def main(args: Array[String]): Unit = {
    var path="E:\\wordcount\\spark\\white\\json"

    val spark = SparkSession.builder().appName("JdbcDataSource")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    //指定以后读取json类型的数据(有表头)
    val jsons: DataFrame = spark.read.json(path)

    val filtered: DataFrame = jsons.where($"age" <=110)


    filtered.printSchema()

    filtered.show()

    spark.stop()


  }
}
