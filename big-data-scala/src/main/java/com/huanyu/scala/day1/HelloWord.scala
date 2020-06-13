package com.huanyu.scala.day1

object HelloWord {
    def main(args: Array[String]): Unit = {
        println("hello "+args(0)+args(1))
         //yield
        (a: Int, b: Int) => a + b
    }
}
