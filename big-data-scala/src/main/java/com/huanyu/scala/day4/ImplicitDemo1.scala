package com.huanyu.scala.day4

import java.util

object ImplicitDemo1 {
  // 偏函数使用,隐式转换的功能

  def main(args: Array[String]): Unit = {
    var linkedtLinked = new util.LinkedList[Int]()
    linkedtLinked.add(1)
    linkedtLinked.add(2)
    linkedtLinked.add(3)
    linkedtLinked.add(4)

    def foreach[T](list: util.LinkedList[T], f: (T) => Unit): Unit = {
      val iter: util.Iterator[T] = list.iterator()
      while (iter.hasNext) f(iter.next())
    }

    foreach(linkedtLinked, println)

  }




}


