package com.huanyu.scala.day4

import java.util

object ImplicitDemo2 {
  // 偏函数使用,隐式转换的功能

  def main(args: Array[String]): Unit = {
    var linkedtLinked = new util.LinkedList[Int]()
    linkedtLinked.add(1)
    linkedtLinked.add(2)
    linkedtLinked.add(3)
    linkedtLinked.add(4)

    val listArray = new util.ArrayList[Int]()
    listArray.add(1)
    listArray.add(2)
    listArray.add(3)

    //隐式转换：  隐式转换方法
    implicit def sdfsdf[T](list: util.LinkedList[T]) = {
      val iter: util.Iterator[T] = list.iterator()
      new XXX(iter)
    }

    implicit def sldkfjskldfj[T](list: java.util.ArrayList[T]) = {
      val iter: util.Iterator[T] = list.iterator()
      new XXX(iter)
    }
    linkedtLinked.foreach(println)
    listArray.foreach(println)

  }

  class XXX[T](list: util.Iterator[T]) {

    def foreach(f: (T) => Unit): Unit = {

      while (list.hasNext) f(list.next())
    }

  }

}


