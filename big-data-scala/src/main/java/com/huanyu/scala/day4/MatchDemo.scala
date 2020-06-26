package com.huanyu.scala.day4

object MatchDemo {


  def main(args: Array[String]): Unit = {

    val tuple = (12,"abc","hrllo",false,44)
    val iterator:Iterator[Any] = tuple.productIterator

    val res:Iterator[Unit] = iterator.map((x) => {
      x match {
        case 12 => println("12 匹配上了")
        case "abc" => println("abc 匹配上了")
        case false => println("false 匹配上了")
        case _ => println("nothing")
      }

    })

    while(res.hasNext)  res.next()






  }


}
