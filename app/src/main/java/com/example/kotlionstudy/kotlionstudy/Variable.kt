package com.example.kotlionstudy.kotlionstudy

/**
 * Created by BaoQi
 * Date : 2020/12/20
 * Des:
 */
fun main(args: Array<String>) {
    variableStudy()
    println("${add(3, 7)}")
}

/**
 * var 用来定义一个变量
 * val 用来定义一个常量
 */
fun variableStudy() {
    var a = 22

    /**
     * a 在第一次赋值的时候赋值为Int型数据，所以a自动推断为Int型变量，
     * 再次赋值也只能是Int型的数据
     */
    // a = 3.14 //报错

    var maxInt = Int.MAX_VALUE
    var maxDouble: Double = Double.MAX_VALUE
    println("maxInt=$maxInt,maxDouble=$maxDouble")

    /**
     * maxDouble即使是double型数据，还是不能赋值范围更小的Int型数据
     */
    // maxDouble = maxInt;

    val name = "dandan"
    println("name=$name")

}

