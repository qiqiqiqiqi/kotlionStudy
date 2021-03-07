package com.example.kotlionstudy.data

/**
 * @author BaoQi
 * Date : 2021/3/3
 * Des:
 */
/**
 * 密封类用来表示受限的类继承结构：当一个值为有限几种的类型, 而不能有任何其他类型时。
 * 在某种意义上，他们是枚举类的扩展：枚举类型的值集合 也是受限的，但每个枚举常量只存在一个实例，
 * 而密封类 的一个子类可以有可包含状态的多个实例。
 */
sealed class Status
class Small : Status()
class Middle : Status()
class Big : Status()
class Normal : Status()//其中的一个类，需要创建对象
object Other : Status()//具体对象，唯一

fun eval(status: Status) = when (status) {
    is Small -> {
        println("Small")
    }
    is Middle -> {
        println("Middle")
    }
    is Big -> {
        println("Big")
    }
    is Normal -> {
        println("Normal")
    }
    Other -> {
        println("Other")
    }
}

fun main(args: Array<String>) {
    eval(Small())
}