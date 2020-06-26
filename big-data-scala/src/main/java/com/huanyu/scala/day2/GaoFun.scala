package com.huanyu.scala

object GaoFun {

  def main(args: Array[String]): Unit = {
   /* computer(1, 1, (x: Int, y: Int) => {
      x + y
    });

    computer(1, 1, (x: Int, y: Int) => {
      x * y
    });
    //甜函数
    computer(3, 8, _ + _)*/

    fun02(3)(8)("sfg")


  }

  //函数作为参数
  def computer(a: Int, b: Int, f: (Int, Int) => Int): Unit = {
    val res: Int = f(a, b)
    println(res)
  }

  //ke 粒化


  def fun02(a: Int)(b: Int)(c: String): Unit = {

    println(s"$a $b $c")

  }

  def fun03(a: Int*)(b: String*): Unit = {
    for (elem <- a) {
      println(elem)
    }
    b.foreach(println)
  }

}
