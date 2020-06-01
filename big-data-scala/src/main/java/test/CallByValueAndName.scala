package test

object CallByValueAndName {

    // 钱包总金额
    var money = 50

    // 每次扣5块钱
    def huaQian(): Unit = {
        money = money - 5
    }

    // 数钱，看看卡里还有
    def shuQian(): Int = {
        huaQian
        money
    }

    // x:  => Int 表示的是一个方法的签名 =》 没有参数，返回值为Int类型的函数
    def printByName(x: => Int) = {
        for(b<- 0 to 3) {
            println(s"每次都算算还剩: ${x}元")
        }
    }

    def printByValue(x: Int) = {
        for (a<- 0 until  3) {
            println(s"测试: ${x}元")
        }
    }

    def main(args: Array[String]): Unit = {
        // 传名（函数）调用
        // 将shuQian方法名称传递到方法的内部执行
        printByName(shuQian)

        // 传值调用 printByValue 参数为一个具体的数值
        // 1. 计算shuQian的返回值 = 45
        // 2. 将45作为参数传入printByValue
        // printByValue(shuQian)
    }

}
