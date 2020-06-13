package com.huanyu.scala.day4

import java.util

object ImplicitDemo {
  // 偏函数使用,隐式转换的功能

  def main(args: Array[String]): Unit = {
    var linkedtLinked = new util.LinkedList[Int]()
    linkedtLinked.add(1)
    linkedtLinked.add(2)
    linkedtLinked.add(3)
    linkedtLinked.add(4)
    var unit = new XXX[Int](linkedtLinked)
    unit.foreach(println)

  }

  class XXX[T](list: util.LinkedList[T]) {
    def foreach(f: (T) => Unit): Unit = {
      val iter: util.Iterator[T] = list.iterator()
      while (iter.hasNext) f(iter.next())
    }

  }

}


