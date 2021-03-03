package com.example.kotlionstudy.kotlionstudy

import android.view.View
import kotlin.properties.ReadOnlyProperty

/**
 * Created by BaoQi
 * Date : 2020/12/24
 * Des: 条件控制语句
 */
fun main(args: Array<String>) {
    testIfElse()
    testWhen()

}

fun testIfElse() {
    val a = 7
    //传统方法
    if (a in 1..10) {
        println("$a 在1..10内")
    } else {
        println("$a 不在1..10内")
    }

    // if..else作为表达式赋值给变量
    val b = if (a in 1..10) a else 0

    val c = if (a in 1..10) {
        77
        88
        "sun"
    } else {
        55
        66
    }
    println("c=$c")
}

fun testWhen() {
    var a = 1
    /**
     * when 既可以被当做表达式使用也可以被当做语句使用。
     * 如果它被当做表达式，符合条件的分支的值就是整个表达式的值，如果当做语句使用， 则忽略个别分支的值。
     */
    when (a) {
        1, 2 -> println("a= $a")
        else -> {
            println("a==1 is ${a == 1}")
        }
    }
    /**
     *  when 当做表达式使用
     */

    a = when (a) {
        in 1..10 -> a
        else -> 7
    }
    println("when 表达式 a=$a")


    /**
     * when 也可以用来取代 if-else if链。 如果不提供参数，
     * 所有的分支条件都是简单的布尔表达式，而当一个分支的条件为真时则执行该分支
     */
    val b = a
    when {
        a == 1 && b == a -> {
            println("a==1 && b==a ${true}")
        }
        else -> {

        }
    }
    /**
     * 另一种可能性是检测一个值是（is）或者不是（!is）一个特定类型的值。
     * 注意： 由于智能转换，你可以访问该类型的方法和属性而无需 任何额外的检测。
     */
    var d = "ddshiqqd"
    when (d) {
        is String -> {
            println("d startWith \"dd\" is ${d.startsWith("dd")}")
        }
        else -> {
        }
    }

}

