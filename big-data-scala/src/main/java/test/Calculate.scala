package test

object Calculate {

    // add方法拥有2个Int类型的参数， 返回值为2个Int的和
    def add(a: Int, b: Int) = {
        a + b
    }


    // add2方法拥有3个参数，第一个参数是一个函数， 第二个，第三个为Int类型的参数
    // 第一个参数：
    //     是拥有2个Int类型的参数，返回值为Int类型的函数
    def add2(f:(Int, Int) => Int, a: Int, b: Int) = {
        f(a, b) // f(1, 2) => 1 + 2
    }

    def add3(a:Int => Int, b: Int) = {
        a(b) + b // x * 10 + 6
    }


    // fxx: (Int, Int) => Int
    val fxx = (a: Int, b: Int) => a + b


    val f1 = (x: Int) => x * 10

    def main(args: Array[String]): Unit = {
//
//        // (1，(2+2)=4)
//        val r1 = add(1, 2 + 2)
//        println(r1)
//
//        // add(8 ,8)
//        add(fxx(2, 6), 8)

        // add3(f1, 6)
        // f1(6) + 6
        // 6 * 10 + 6
        val r3 = add3(f1, 6)
        println(r3)



        // add2(fxx, 1, 2)

    }

}
