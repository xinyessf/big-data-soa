package com.huanyu.spark.scala.day05

/**
  * Created by zx on 2017/10/10.
  */
object SortRules {

  implicit object OrderingXiaoRou extends Ordering[XianRou] {
    override def compare(x: XianRou, y: XianRou): Int = {
      if(x.fv == y.fv) {
        x.age - y.age
      } else {
        y.fv - x.fv
      }
    }
  }
}
