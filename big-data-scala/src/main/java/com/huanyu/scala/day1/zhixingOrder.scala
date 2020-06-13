package com.huanyu.scala.day1

/**
  * 1.变量先执行,main最后执行
  */
 object zhixingOrder {

  println("ooxx up...1")
  private val xo:xxoo=new xxoo()
  private val xo1:xxoo = new xxoo()

  def main(args: Array[String]): Unit = {
    println("hello3")
    xo.dd()
  }
  println("ooxx down...2")

  class xxoo{
    def dd(): Unit ={
    println("abc4")
    }

  }
}
