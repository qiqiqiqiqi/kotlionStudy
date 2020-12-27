package com.example.kotlionstudy.kotlionstudy

/**
 * Created by BaoQi
 * Date : 2020/12/27
 * Des: Kotlin 循环控制
 */
fun main(args: Array<String>) {
    testFor()
    testWhile()
    testLoopLable()
}

fun testFor() {
    val arr = arrayOf("apple", "xiaomi", "huawei")
    /**
     * 无索引
     */
    println("无索引")
    for (item in arr) {
        println(item)
    }
    /**
     * 索引遍历
     */
    println("\n索引遍历")
    for (index in arr.indices) {
        println("$index,${arr[index]}")
    }
    /**
     * 索引遍历,带值
     */
    println("\n索引遍历,带值")
    for ((index, value) in arr.withIndex()) {
        println("$index,$value")
    }

    /**
     * 倒序遍历
     */
    println("\n倒序遍历")
    for (index in arr.count() - 1 downTo 0) {
        println(arr[index])
    }
    /**
     *指定步长遍历
     */
    println("\n指定步长遍历")
    for (index in arr.indices step 2) {
        println(arr[index])
    }
    /**
     * [start,end) 左闭右开区间
     */
    println("\n左闭右开区间")
    for (index in 0 until arr.count()) {
        println(arr[index])
    }

    val s = arr.fold("", { acc: String, value: String -> acc + value })
    val cs = arr.fold("", customAdd)
    arr.fold("", fun(acc: String, value: String) = acc + value)

    println("s=$s,cs=$cs")
}

var customAdd = fun(sum: String, value: String): String {
    return sum + value
}


fun testWhile() {
    var a = 0
    while (a < 7) {
        a++
    }
    println(a)

    do {
        println("至少执行一次")
    } while (a < 7)
}

fun testLoopLable() {
    /**
     * 标签限制的 break 跳转到刚好位于该标签指定的循环后面的执行点。
     * continue 继续标签指定的循环的下一次迭代。
     */
    // 在内循环中断了，外循环继续执行
    for (i in 1..4) {
        println("out$i")
        for (i in 1..3) {
            if (i == 2) break
            println("in $i")
        }
    }
    println()
    // 在外循环中断了
    out@ for (i in 1..4) {
        println("out$i")
        for (i in 1..3) {
            if (i == 2) break@out
            println("in $i")
        }
    }
    testLable1()
    testLable2()
}

fun testLable1(): Unit {
    val arr = arrayOf("apple", "huawei", "xiaomi")
    arr.forEach { if (arr.indexOf(it) == 1) return }//testLable1返回了
    println("testLable()")//不打印
}

fun testLable2(): Unit {
    val arr = arrayOf("apple", "huawei", "xiaomi")
    arr.forEach { if (arr.indexOf(it) == 1) return@forEach }//forEach返回了
    println("testLable1()")//打印
}








