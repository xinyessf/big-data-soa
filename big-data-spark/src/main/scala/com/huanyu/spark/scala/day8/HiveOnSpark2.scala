package com.huanyu.spark.scala.day8

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by zx on 2017/10/16.
  */
object HiveOnSpark2 {

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
    //val sql: DataFrame = spark.sql("CREATE  TABLE  t_dm_goip_original_tactics(phone STRING COMMENT '被叫号码',province STRING COMMENT '省份',city STRING COMMENT '城市',call_time STRING COMMENT '通话时间',call_duration INT COMMENT '通话时长',fraud_type STRING COMMENT '违规类型') partitioned by(day string) row format delimited fields terminated by ','")
    //删表
   // val results: DataFrame = spark.sql("drop table t_cdr_k")
    //val sql1: DataFrame = spark.sql("CREATE TABLE t_cdr_k(c_usernum STRING COMMENT '被加密的主叫号码',c_relatenum STRING COMMENT '被加密的主叫号码',c_begintime BIGINT COMMENT '通话开始时间',c_endtime BIGINT COMMENT '通话结束时间') PARTITIONED BY (day string,hour string) row format delimited fields terminated by ',' ");
    //val sql1: DataFrame = spark.sql("CREATE TABLE t_cdr_k(c_usernum STRING COMMENT '被加密的主叫号码',c_relatenum STRING COMMENT '被加密的主叫号码',c_begintime BIGINT COMMENT '通话开始时间',c_endtime BIGINT COMMENT '通话结束时间') PARTITIONED BY (day string,hour string) row format delimited fields terminated by ',' stored as textfile TBLPROPERTIES (\"parquet.compression\"=\"SNAPPY\")")

    //val sql: DataFrame = spark.sql("load data local inpath 'f:/wordcount/spark/t_cdr_k.txt'  into table t_cdr_k partition(day='20200516',hour='01')")
    //查表
    // val result: DataFrame = spark.sql("select from_unixtime(cdk.c_begintime) as c_begintime from t_cdr_k cdk")
    val result1: DataFrame = spark.sql("select * from t_cdr_k")

    //val result2: DataFrame = spark.sql("SELECT cdr.c_usernum as calling_number,cdr.c_relatenum as called_number,tactics.province as province,tactics.city as city,from_unixtime(cdk.c_begintime) as talking_time,(cdr.c_endtime-cdr.c_begintime) as calling_duration,tactics.fraud_type as fraud_type FROM t_cdr_k as cdr ,t_dm_mvtech_tactics  as tactics  WHERE cdr.day='20200516' AND cdr.hour='01' and cdr.c_usernum = tactics.phone ")
    val result3: DataFrame = spark.sql("SELECT cdr.c_usernum as calling_number,cdr.c_relatenum as called_number,from_unixtime(cdr.c_begintime) as talking_time,tactics.province as province,tactics.city as city,(cdr.c_endtime-cdr.c_begintime) as calling_duration,tactics.fraud_type as fraud_type FROM t_cdr_k as cdr ,t_dm_mvtech_tactics  as tactics  WHERE cdr.day='20200516' AND cdr.hour='01' and cdr.c_usernum = tactics.phone ")
    //
    //val result4: DataFrame = spark.sql("select * from t_dm_mvtech_tactics")
    //result.collectAsList();

    //result1.show()
    //result1.show()
    //result2.show()
    result3.show()
    //result4.show()

    spark.close()


  }
}
