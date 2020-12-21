package com.example.kotlionstudy.kotlionstudy

/**
 * Created by BaoQi
 * Date : 2020/12/20
 * Des:
 */
fun main(args: Array<String>) {

    println("add():add=${add(1, 2)}")
    println("add2():add2=${add2(3, 4)}")


}

/**
 * 有返回值的函数
 */
fun add(num1: Int, num2: Int): Int {
    return num1 + num2
}

/**
 * 带有返回值的简单函数的简写
 */
fun add2(num1: Int, num2: Int) = num1 + num2

/**
 * 没有返回值的函数默认
 */
fun noReturnFun():Unit{

}

