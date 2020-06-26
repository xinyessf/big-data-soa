package com.huanyu.scala

import java.util.Date

object Fun {

  def main(args: Array[String]): Unit = {
    //fun01
    //
    //println(fun02)
    //fun03
    //fun04(4)
    //fun05(5)
    //fun06(1, "bbb")
    //fun06(2)
    //var yy = fun07()
    //fun08("ab")

    //fun09(new Date(), "info", "ok")
    //var info = fun09(_: Date, "error", _: String)

    // var info = fun09(_: Date, "info", _: String)
    //info(new Date, "ok")
    //var error = fun09(_: Date, "error", _: String)
    //error(new Date, "ok")

    fun08(1, 32, 4, 32, 432, 2)

  }

  // 可变参数
  def fun08(a: Int*): Unit = {
    for (e <- a) {
      println(e)
    }
    a.foreach((x: Int) => {
      println(x)
    })
  }


  //应用
  def fun09(date: Date, tp: String, msg: String): Unit = {
    println(s"$date\t$tp\t$msg")
  }


  //嵌套函数
  def fun08(a: String): Unit = {
    def fun01(): Unit = {
      println("abcdefg")
    }

    fun01()
  }


  //匿名函数
  def fun07() {
    var yy: (Int, Int) => Int = (a: Int, b: Int) => {
      a + b
    }

    println(yy(3, 4))

  }


  // 约束

  def fun06(a: Int, b: String = "abc"): Unit = {
    println(s"$a\t$b")
  }


  //递归函数
  def fun05(num: Int): Int = {
    println(num)
    if (num == 1) {
      num
    } else {
      fun05(num - 1)
    }

  }


  //参数,约束
  def fun04(a: Int): Unit = {
    println(a)

  }

  //==list
  /*def fun03(): util.LinkedList[String]={

    new util.LinkedList[String]()

  }*/


  def fun02(): Int = {
    3
  }


  //无返回值
  def fun01(): Unit = {
    println(123)
  }

  //


}
