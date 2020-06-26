package com.huanyu.scala.day4

import java.util

object ImplicitDemo3 {
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

    //spark  RDD N方法 scala
    //隐式转换类
        implicit  class KKK[T](list:util.LinkedList[T]){
         def foreach( f:(T)=>Unit): Unit ={
            val iter: util.Iterator[T] = list.iterator()
            while(iter.hasNext) f(iter.next())
         }
       }
     val unit = new KKK[Int](linkedtLinked)
    unit.foreach(println)


  }

  class XXX[T](list: util.Iterator[T]) {

    def foreach(f: (T) => Unit): Unit = {

      while (list.hasNext) f(list.next())
    }

  }

}


