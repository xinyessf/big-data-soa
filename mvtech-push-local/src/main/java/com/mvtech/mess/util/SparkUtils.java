package com.mvtech.mess.util;

import org.apache.spark.sql.SparkSession;

/**
 * @author: sunsf
 * @date: 2020/5/20 18:28
 */

public class SparkUtils {
    public static SparkSession getSession() {
        /*本地测试*/
     /*  return SparkSession.builder()
                .appName("HiveOnSpark2")
                .master("local[*]")
                .enableHiveSupport()
                .getOrCreate();*/
        SparkSession sparkSession = SparkSession.builder().appName("mess_mvtech").enableHiveSupport().getOrCreate();
        sparkSession.conf().set("spark.sql.parquet.binaryAsString", "true");
        sparkSession.conf().set("spark.hadoop.hive.metastore.uris", "thrift://hdp-01:9083");
        sparkSession.conf().set("spark.hadoop.hive.metastore.warehouse.dir", "hdfs://user/hive/warehouse");
        return sparkSession;
    }

    public static void close(SparkSession sparkSession) {
        try {
            //关闭spark应用
            if (sparkSession != null) {
                sparkSession.stop();
                sparkSession.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
