package com.huanyu.spark.scala.day4

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

 object IpLocal {

  //1.读取文件
  //2.将ip的工具类,ip对应省份解析和处理
  //3.写入数据库
  def main(args: Array[String]): Unit = {

    var accessPath = "E:\\wordcount\\ip\\access.log"

    var ipPath = "E:\\wordcount\\ip\\ip.txt"


    val conf = new SparkConf().setAppName("IpLocal").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val ruleLines: RDD[String] = sc.textFile(ipPath)
    //1.整理ip规则数据

    //2.开始ip,3结束ip,6,省份
    val ipRule: RDD[(Long, Long, String)] = ruleLines.map(line => {
      val fields = line.split("[|]")
      val startNum = fields(2).toLong
      val endNum = fields(3).toLong
      val province = fields(6)
      (startNum, endNum, province)
    })
    //将分散再多个excutor中部分ip规则,收集到Driver端
    val rulesInDriver: Array[(Long, Long, String)] = ipRule.collect()
    //上传到executor端
    val broadCast: Broadcast[Array[(Long, Long, String)]] = sc.broadcast(rulesInDriver)
    //创建RDD,读取访问日志
    val accesslines: RDD[String] = sc.textFile(accessPath)
    var provicneAndOne: RDD[(String, Int)] = accesslines.map(log => {
      val fields = log.split("[|]")
      val ip = fields(1)
      val ipNum = MyUtils.ip2Long(ip)
      //ip转换成十进制,二分查找
      val ruleInExecurot: Array[(Long, Long, String)] = broadCast.value
      var province = "未知"
      val index = MyUtils.binarySearch(ruleInExecurot, ipNum)

      if (index != -1) {
        province = ruleInExecurot(index)._3
      }
      (province, 1)

    })

    val reduce: RDD[(String, Int)] = provicneAndOne.reduceByKey(_ + _)

    reduce.foreachPartition(it => {
      MyUtils.data2MySQL(it)

    })
    sc.stop;
  }


  def data2MySQL(it: Iterator[(String, Int)]): Unit = {
    //1.一个迭代器,代表一个分区,分区中有多个数据
    val conn: Connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata?characterEncoding=UTF-8", "root", "toortoor")
    val pstm: PreparedStatement = conn.prepareStatement("INSERT INTO access_log VALUES (?, ?)")
    it.foreach(tp => {
      pstm.setString(1, tp._1)
      pstm.setInt(2, tp._2)
      pstm.executeUpdate()
    })
    //将分区中的数据全部写完之后,
    if (pstm != null) {
      pstm.close()
    }
    if (conn != null) {
      conn.close()
    }


  }

}
