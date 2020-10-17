package com.huanyu.spark.scala.day7

import java.util.Properties

import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
  * 1.从mysql种过滤
  * 2.将数据filter,然后写入到文件
  * 3.将数据保存到mysql
  */
object JdbcDataSource {

  def main(args: Array[String]): Unit = {
    var ip="E:\\wordcount\\spark\\white"
    val spark = SparkSession.builder().appName("JdbcDataSource")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._
    //load这个方法会读取真正mysql的数据吗？
    val logs: DataFrame = spark.read.format("jdbc").options(
      Map("url" -> "jdbc:mysql://localhost:3306/bigdata",
        "driver" -> "com.mysql.jdbc.Driver",
        "dbtable" -> "logs",
        "user" -> "root",
        "password" -> "toortoor")
    ).load()

    //logs.printSchema()

    //logs.show()

//    val filtered: Dataset[Row] = logs.filter(r => {
//      r.getAs[Int]("age") <= 13
//    })
//    filtered.show()

    //lambda表达式
    val r = logs.filter($"age" <= 13)

    //val r = logs.where($"age" <= 13)

    val reslut: DataFrame = r.select($"id", $"name", $"age" * 10 as "age")

    //val props = new Properties()
    //props.put("user","root")
    //props.put("password","123568")
    //reslut.write.mode("ignore").jdbc("jdbc:mysql://localhost:3306/bigdata", "logs1", props)

    //DataFrame保存成text时出错(只能保存一列)

    //reslut.write.text(ip+"text");

    reslut.write.json(ip+"\\json")

    reslut.write.csv(ip+"\\csv")

    reslut.write.parquet(ip+"\\parquet")


    reslut.show()

    spark.close()


  }
}
