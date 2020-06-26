package com.huanyu.scala.day4


/**
  * 干嘛用的
  */
object TraintDemo {


  trait Gd {

    def say(): Unit = {
      println("god say")
    }


  }

  trait Mg {

    def ku(): Unit = {
      println("MG ku...")
    }

    def haiRen(): Unit
  }

  class Person(name: String) extends Gd with Mg {
    def hello(): Unit = {
      println(s"$name say hello")
    }

    override def haiRen(): Unit = {
      println("害人....")
    }
  }


  def main(args: Array[String]): Unit = {
    val person = new Person("nili")
    person.ku()

    person.say()
    person.haiRen()

  }


}
