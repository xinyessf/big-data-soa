package com.huanyu.spark.scala.day8.mess

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by zx on 2017/10/16.
  */
object Cmcc {

  def main(args: Array[String]): Unit = {

    //如果想让hive运行在spark上，一定要开启spark对hive的支持
    val spark = SparkSession.builder()
      .appName("HiveOnSpark")
      .master("local[*]")
      .config("spark.kryoserializer.buffer", "1024m")
      .config("spark.kryoserializer.buffer.max", "2046m")
      .enableHiveSupport() //启用spark对hive的支持(可以兼容hive的语法了)
      .getOrCreate()

    //想要使用hive的元数据库，必须指定hive元数据的位置，添加一个hive-site.xml到当前程序的classpath下即可
    //

    //删表
    //val results22: DataFrame = spark.sql("create database  original")

    //val results20: DataFrame = spark.sql("drop table original.t_ods_cmcc_block_sm")
    //val sql1 = "CREATE TABLE original.t_ods_cmcc_block_sm(c_usernum STRING COMMENT '主叫号码',c_relatenum STRING COMMENT '被叫号码',c_time STRING COMMENT '时间',c_content STRING COMMENT '短信内容') PARTITIONED BY (day string) row format delimited fields terminated by ','";
    // val results: DataFrame = spark.sql(sql1)

     //val sql: DataFrame = spark.sql("load data local inpath 'E:/wordcount/cmcc/2.txt'  into table original.t_ods_cmcc_block_sm partition(day='20200521')")
   // val result4: DataFrame = spark.sql("select count(1) from original.t_ods_cmcc_block_sm u where day='20200520'")

    // for 循环
    //val result2: DataFrame = spark.sql("select c_usernum,c_relatenum,c_time,c_content from original.t_ods_cmcc_block_sm where day='20200520' and c_time>='2020-05-20 12:00:00' and c_time<'2020-05-20 13:00:00'")
    //val result4: DataFrame = spark.sql("select c_usernum,c_relatenum,c_time,c_content from original.t_ods_cmcc_block_sm where day='20200520'")
   // val result4: DataFrame = spark.sql("select row_number() over (order by c_time desc) as rownum,u.* from original.t_ods_cmcc_block_sm u where day='20200520'")
    val result4: DataFrame = spark.sql("select rownum,c_usernum,c_relatenum,c_time,c_content from (select row_number() over (order by u.c_time desc) as rownum,u.* from original.t_ods_cmcc_block_sm u where day='20200521') cmcc where cmcc.rownum between 1 and 15")
    //result.collectAsList();

    //result1.show()
    result4.show(400000)

    spark.close()
  }
}
