package com.huanyu.spark.scala.day8

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by zx on 2017/10/16.
  */
object HiveOnSpark4 {

  def main(args: Array[String]): Unit = {

    //如果想让hive运行在spark上，一定要开启spark对hive的支持
    val spark = SparkSession.builder()
      .appName("HiveOnSpark")
      .master("local[*]")
      .enableHiveSupport() //启用spark对hive的支持(可以兼容hive的语法了)
      .getOrCreate()

    //想要使用hive的元数据库，必须指定hive元数据的位置，添加一个hive-site.xml到当前程序的classpath下即可
    //

    //删表
   //val results20: DataFrame = spark.sql("drop table original.t_ods_cmcc_block_sm")
    //val sql1 = "CREATE TABLE original.t_ods_cmcc_block_sm(c_usernum STRING COMMENT '主叫号码',c_relatenum STRING COMMENT '被叫号码',c_time STRING COMMENT '时间',c_content STRING COMMENT '短信内容') PARTITIONED BY (day string) row format delimited fields terminated by ','";
    //val results: DataFrame = spark.sql(sql1)


    var a = 0;
    // for 循环
    //val result2: DataFrame = spark.sql("select c_usernum,c_relatenum,c_time,c_content from original.t_ods_cmcc_block_sm where day='20200520' and c_time>='2020-05-20 12:00:00' and c_time<'2020-05-20 13:00:00'")
    //val sql: DataFrame = spark.sql("load data local inpath 'f:/wordcount/spark/t_cdr_k.txt'  into table original.t_cdr_k partition(day='20200518',hour='10')")

    val result3: DataFrame = spark.sql("select c_usernum,c_relatenum,c_time,c_content from original.t_ods_cmcc_block_sm where day='20200520' and c_time>='2020-05-20 13:00:00' and c_time<'2020-05-20 14:00:00'")
    //result.collectAsList();

    //result1.show()
    result3.show();

    spark.close()


  }
}
