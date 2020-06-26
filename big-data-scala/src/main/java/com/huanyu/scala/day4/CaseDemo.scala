package com.huanyu.scala.day4

case class Dog(name:String,age:Int){
}

object CaseDemo {

  def main(args: Array[String]): Unit = {
    val dog1 =  Dog("hsq",18)
    val dog2 =  Dog("hsq",18)
    println(dog1.equals(dog2))
    println(dog1 == dog2)
  }

}
