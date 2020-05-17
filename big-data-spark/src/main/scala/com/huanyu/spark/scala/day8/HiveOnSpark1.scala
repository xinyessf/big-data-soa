package com.huanyu.spark.scala.day8

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by zx on 2017/10/16.
  */
object HiveOnSpark1 {

  def main(args: Array[String]): Unit = {

    //如果想让hive运行在spark上，一定要开启spark对hive的支持
    val spark = SparkSession.builder()
      .appName("HiveOnSpark")
      .master("local[*]")
      .enableHiveSupport() //启用spark对hive的支持(可以兼容hive的语法了)
      .getOrCreate()

    //想要使用hive的元数据库，必须指定hive元数据的位置，添加一个hive-site.xml到当前程序的classpath下即可

    //有t_boy真个表或试图吗？
    //val result: DataFrame = spark.sql("SELECT * FROM niu ")
    //val result: DataFrame = spark.sql("select * from t_dm_goip_original_tactics")

    //val sql: DataFrame = spark.sql("CREATE TABLE niu (id bigint, name string)")

    //val sql: DataFrame = spark.sql("load data local inpath 'f:/wordcount/spark/t_access.log' into table t_access  partition(dt='20200513')")
    //val sql: DataFrame = spark.sql("load data local inpath 'f:/wordcount/spark/t_access.log' into table t_access  partition(dt='20200513')")
    //加载数据
    //val sql: DataFrame = spark.sql("load data local inpath 'f:/wordcount/spark/tactics.txt' into table t_dm_goip_original_tactics ")
    //新增中间表
    //val sql2: DataFrame = spark.sql("CREATE  TABLE  t_dm_goip_original_tactics(phone STRING COMMENT '被叫号码',province STRING COMMENT '省份',city STRING COMMENT '城市',call_time STRING COMMENT '通话时间',call_duration INT COMMENT '通话时长',fraud_type STRING COMMENT '违规类型') partitioned by(day string) row format delimited fields terminated by ','")
    //删表
    //val result1: DataFrame = spark.sql("drop table t_dm_mvtech_tactics")
    //val sql1: DataFrame = spark.sql("CREATE  TABLE t_dm_mvtech_tactics(phone STRING COMMENT '电话号码',province STRING COMMENT '省份',city STRING COMMENT '城市',start_time BIGINT COMMENT '开始时间',fraud_type STRING COMMENT '违规类型',phone_m STRING COMMENT '被叫电话号码') partitioned by(day string) row format delimited fields terminated by ','")
   // val sql2: DataFrame = spark.sql("CREATE  TABLE  t_dm_mvtech_tactics(phone STRING COMMENT '加密后电话号码',province STRING COMMENT '省份',city STRING COMMENT '城市',start_time BIGINT COMMENT '开始时间',fraud_type STRING COMMENT '违规类型',phone_m STRING COMMENT '未被加密电话号码')partitioned by(day string)row format delimited fields terminated by ','stored as textfile TBLPROPERTIES (\"parquet.compression\"=\"SNAPPY\")")
   // val sql: DataFrame = spark.sql("load data local inpath 'f:/wordcount/spark/tactics.txt'  into table t_dm_mvtech_tactics partition(day='20200516')")

    //查表
    val result: DataFrame = spark.sql("select * from t_dm_mvtech_tactics")

    //result.collectAsList();

    result.show()

    spark.close()


  }
}
