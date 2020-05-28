package com.huanyu.spark.scala.day8

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by zx on 2017/10/16.
  */
object HiveOnSpark {

  def main(args: Array[String]): Unit = {

    //如果想让hive运行在spark上，一定要开启spark对hive的支持
    val spark = SparkSession.builder()
      .appName("HiveOnSpark")
      .master("local[*]")
      .enableHiveSupport()//启用spark对hive的支持(可以兼容hive的语法了)
      .getOrCreate()

    //想要使用hive的元数据库，必须指定hive元数据的位置，添加一个hive-site.xml到当前程序的classpath下即可

    //有t_boy真个表或试图吗？
    //val result: DataFrame = spark.sql("SELECT * FROM niu ")
    //val result: DataFrame = spark.sql("select * from t_dm_goip_original_tactics")

    //val sql: DataFrame = spark.sql("CREATE TABLE niu (id bigint, name string)")

    //val sql: DataFrame = spark.sql("load data local inpath 'f:/wordcount/spark/t_access.log' into table t_access  partition(dt='20200513')")
    //val sql: DataFrame = spark.sql("load data local inpath 'f:/wordcount/spark/t_access.log' into table t_access  partition(dt='20200513')")
    //新增表
    //val sql: DataFrame = spark.sql("CREATE  TABLE  t_dm_goip_black_tactics(phone STRING COMMENT '被叫号码',province STRING COMMENT '省份',city STRING COMMENT '城市',call_time STRING COMMENT '通话时间',call_duration INT COMMENT '通话时长',fraud_type STRING COMMENT '违规类型') partitioned by(day string) row format delimited fields terminated by ','")
    //加载数据,加载的数据要放在对应的目录下边
    val sql2: DataFrame = spark.sql("load data local inpath 'e:/wordcount/spark/20200512030001.csv' into table t_dm_goip_black_tactics  partition(day='20200509')")
    //查表
    val result: DataFrame = spark.sql("select * from t_dm_goip_black_tactics where day='20200509'")
    //删表
    //val result: DataFrame = spark.sql("drop table t_dm_goip_original_tactics")
    //result.collectAsList();

    result.show()

    spark.close()


  }
}
