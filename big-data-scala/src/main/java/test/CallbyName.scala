package test

object CallbyName extends App{


    def currentTime(): Long ={
        println("打印系统当前时间,单位纳秒")
        System.nanoTime()
    }

    /**
      * 该方法的参数为一个无参的函数, 并且函数的返回值为Long
      */
    def delayed(f: => Long): Unit = {
        println("delayed ===============")
        println("time = " + f)
    }

    def delayed1(time: Long) = {
        println("delayed1 ===============")
        println("time1 = " + time)
    }

    // 调用方式一
    delayed(currentTime() _)

    println("------------华丽丽的分割线----------")

    // 调用方式二
    val time = currentTime()
    delayed1(time)

}
