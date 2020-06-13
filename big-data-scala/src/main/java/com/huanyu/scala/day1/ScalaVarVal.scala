package com.huanyu.scala.day1



class Student(var name: String, var age: Int)

/**
  * 占位符的使用 if else
  */
object ScalaVarVal {


    def main(args: Array[String]): Unit = {

        // 变量的定义
        /**
          * 可以是var 和val修饰
          * var修饰的变量值可以更改
          * val修饰的变量值不可以改变，相当于java中final修饰的变量
          *
          * var | val 变量名称: 类型 = 值
          *
          * Unit 数据类型相当于java中void关键字，但是在scala它的标识形式是一对（）
          */
        val name: String = "lisi"
        var age = 18

        println("name = "+name, "age = " + age)

        val sql = s"select * from xx where name = ? and province = ?"
        //ps.setInt(1, "')
          println(f"姓名：$name%s  年龄：$age")      // 该行输出有换行
          printf("%s 学费 %5.2f, 网址是%s  补充%s", name, 12534.146516, "xx",2)   // 该行输出没有换行
        println()
//
        val stu = new Student("taotao", 18)
       println(s"${name}")
       println(s"${stu.name}")

        val i: Int = 12
        val s = if(i > 10) {
            i
            i * i
        } else {
            0
            100
        }
//
        val r = if(i < 8) i  else  0//没有写， 编译器会自动推测出你什么都没有返回就是Unit

       // val r1 = if(i<8) i else 1

        println(r)


    }
}
