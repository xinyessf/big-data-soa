package com.huanyu.spark.scala.day05

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


//1.本章的目的,写入和测试
object Customerlocal {

  def main(args: Array[String]): Unit = {
    // test()
    //
    test1()
    //
  }


  def test1(): Unit = {
    val conf = new SparkConf().setAppName("sort").setMaster("local[*]")
    val sc = new SparkContext(conf)

    //排序规则,先找年龄排序,如果严等,则用年龄的升序排
    val users = Array("laoduan 30 99", "laozhao 29 9999", "laozhang 28 98", "laoyang 28 99")

    val lines: RDD[String] = sc.parallelize(users)
    //切
    var usrRDD: RDD[(String, Int, Int)] = lines.map(line => {
      val fields = line.split(" ")
      val name = fields(0)
      val age = fields(1).toInt
      val fv = fields(2).toInt
      (name, age, fv)
    })
    val sorted: RDD[(String, Int, Int)] = usrRDD.sortBy(tp => (tp._3, -tp._2))
    //排序一行搞定的
    //将RDD里的数据进行排序
    var usr = sorted.collect()
    println(usr.toBuffer)
    sc.stop()
  }

  def test(): Unit = {
    val conf = new SparkConf().setAppName("sort").setMaster("local[*]")
    val sc = new SparkContext(conf)

    //排序规则,先找年龄排序,如果严等,则用年龄的升序排
    val users = Array("laoduan 30 99", "laozhao 29 9999", "laozhang 28 98", "laoyang 28 99")

    val lines: RDD[String] = sc.parallelize(users)
    //切
    var usrRDD: RDD[User] = lines.map(line => {
      val fields = line.split(" ")
      val name = fields(0)
      val age = fields(1).toInt
      val fv = fields(2).toInt
      new User(name, age, fv)
    })
    val sorted: RDD[User] = usrRDD.sortBy(u => u)
    //排序一行搞定的
    //将RDD里的数据进行排序
    var usr = sorted.collect()
    println(usr.toBuffer)
    sc.stop()
  }

  class User(val name: String, val age: Int, val fv: Int) extends Ordered[User] with Serializable {
    override def compare(that: User): Int = {
      if (this.fv == that.fv) {
        this.age - that.age
      } else {
        -(this.fv - that.fv)
      }
    }

    override def toString: String = s"name: $name, age: $age, fv: $fv"
  }


}


