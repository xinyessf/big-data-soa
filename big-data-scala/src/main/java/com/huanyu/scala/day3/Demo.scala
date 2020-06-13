package com.huanyu.scala.day3

import java.util

import scala.collection.mutable.ListBuffer;

object Demo {


  def main(args: Array[String]): Unit = {
    var javaList = new util.LinkedList[String]();
    javaList.add("hello wrold");
    javaList.add("hello world2");
    for (i <- 0 until javaList.size()) {
      javaList.get(i)
      println(javaList.get(i))
    }
    //数组
    println("arr=================")
    val ints = Array[Int](1, 2, 345, 4)
    print(ints(1))
    val arr02 = Array(1, 2, 34, 5);
    for (elem <- arr02) {
      println(elem)
    }

    arr02.foreach(println)
    // linkedlist
    println("List===========")
    val listDemo = List(222.2, 6, 78, 990, 22)
    listDemo.foreach(println)
    val listBuffer = new ListBuffer[String]()
    listBuffer.+=("33")
    listBuffer.+=("bb")
    listBuffer.+=("aa")
    listBuffer.foreach(println)
    println("set============")
    val sets = Set(2, 3, 3, 33, 3, 366, 777, 3)
    sets.foreach(println)
    import scala.collection.mutable.Set
    val set02 = Set(2, 34, 3, 33, 3, 3, 35, 5, 590, 22)
    set02.add(2)
    set02.add(8)

    val set03: Predef.Set[Int] = scala.collection.immutable.Set(33, 23, 44)
    set03.foreach(println)
    println("tuple================")
    val tuple1 = new Tuple1(11, 234, 3, 4, 3)
    println(tuple1._1)
    val tuple2 = new Tuple6(11, 234, 3, 4, 3, 6)

    val tuple: (Int, Int, Int, Int) = (1, 2, 23, 4)

    //tuple 22
    println("tuple22================")
    val t22: ((Int, Int) => Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int,
      Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int)
    = ((a: Int, b: Int) => a + b + 8, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4)

    println(t22._1(3, 8))

    println("map=========================")
    val map01 = Map(("a", 33), ("b", 33), ("c", 33), ("a", 334))
    val keys: Iterable[String] = map01.keys
    //println
    for (elem <- keys) {
      println(s" key=${elem} value=${map01.get(elem).get}")
    }
    println(map01.get("a"))
    println(map01.get("q"))
    println(map01.get("a").getOrElse("hello world"))
    println(map01.get("q").getOrElse("hello world"))
    //map 02 开始
    val map03 = scala.collection.mutable.Map(("a", 1), ("b", 1), ("a", 1))
    map03.put("2", 33)
    val list = List(1, 2, 5, 6, 7, 8)

    list.foreach(println)

    val listMap: List[Int] = list.map((x: Int) => x + 10)

    val lsitmap: List[Int] = list.map(_ * 10)
    println("=================")


    val listStr:List[String] = List("hello world", "hello msb", "god my")

    val flatMap: List[String] = listStr.flatMap((x: String) => x.split(" "))
    val tuples: List[(String, Int)] = flatMap.map((_, 1))


    //迭代器

    val it:Iterator[String] = listStr.iterator

/*   while (it.hasNext){
     println(it.next())
   }*/

    println("============it")
    val da = it.flatMap((x:String)=>x.split(" " ))

    //da.foreach(println)
    val iterCup = da.map((_,1))
    while(iterCup.hasNext){

      print(iterCup.next())

    }



  }
}
