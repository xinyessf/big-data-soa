package com.huanyu.spark.scala.game.day3

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * Created by zx on 2017/8/5.
  */
object DataSetGameKPI_2 {

  def main(args: Array[String]): Unit = {

    val queryTime = "2016-02-01 00:00:00"
    val beginTime = TimeUtils(queryTime)
    //2016-02-03 00:00:00
    val endTime = TimeUtils.getCertainDayTime(3)


    //创建SparkSession
    val spark = SparkSession.builder()
      .appName("DataSetGameKPI")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    //读取数据
    val lines: Dataset[String] = spark.read.textFile(args(0))

    val filterUtils = new FilterUtils_V2

    //切分数据
    val splited: Dataset[Array[String]] = lines.map(_.split("[|]"))
    //过滤数据
    val filtered: Dataset[Array[String]] = splited.filter(fields => {
      filterUtils.filterByTime(fields, beginTime, endTime)
    })


    val updateUser = filtered.filter(fields => {
      filterUtils.filterByTypes(fields, EventType.REGISTER, EventType.UPGRADE)
    })

    val updateUserDF: DataFrame = updateUser.map(fields => {
      (fields(3), fields(6).toDouble)
    }).toDF("username", "level")

    updateUserDF.createTempView("v_update_user")

    //写SparkSQL

    val maxLevelDf = spark.sql("SELECT username, MAX(level) max_level FROM v_update_user GROUP BY username")

    maxLevelDf.createTempView("v_max_level")

    val result = spark.sql("SELECT AVG(max_level) FROM v_max_level")

    result.show()

    //
    spark.stop()

  }

}
