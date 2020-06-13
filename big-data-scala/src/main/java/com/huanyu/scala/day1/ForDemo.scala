package com.huanyu.scala

object ForDemo {
  def main(args: Array[String]): Unit = {

    //fun01()
    //fun02()
    //fun03()
    //fun04
    //fun05
    fun06

  }

  //99 乘法表
  def fun06(): Unit = {
    var num=0
    for (i <- 1 to 9;j<-1 to 9 if(j<=i)){
      num=num+1
      if(j<=i){
        print(s"$i * $j = ${i*j}\t")
      }
      if(j==i)
      println()

    }

  }

  //守护逻辑
  def fun05(): Unit = {
    val range = 1 until(10, 2)
    for (i <- range if (i % 2 != 0)) {
      println(i)
    }

  }

  //4 控制步进
  def fun04(): Unit = {
    //  var seqs:Range.Inclusive=
    val range = 1 until(10, 2)
    println(range)

  }

  //for 3

  def fun03(): Unit = {

    for (i <- 1 to 3 if i != 2) {

      println(i + "")
    }

  }


  //for 1
  def fun01(): Unit = {
    for (i <- 1 to 3) {
      println(i)
    }
  }

  //for 2  打印123
  def fun02(): Unit = {
    for (i <- 1 until 4) {
      println(i)
    }
  }


}
