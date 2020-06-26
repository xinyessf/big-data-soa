package com.huanyu.scala.day1


//类里，裸露的代码是默认构造中的。有默认构造
//个性化构造！！
//类名构造器中的参数就是类的成员属性，且默认是val类型，且默认是private
//只有在类名构造其中的参数可以设置成var，其他方法函数中的参数都是val类型的，且不允许设置成var类型
object GouZao {
  def main(args: Array[String]): Unit = {
    val oxxx1:oxxx = new oxxx("aaa")
    println(oxxx1.sex)
    println(oxxx1.name)
  }

class oxxx(val sex:String){

  var name="class name"

def this(xname:Int){
  this("abc")
}

var a:Int=3
}



  }
