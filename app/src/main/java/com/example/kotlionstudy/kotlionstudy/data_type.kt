package com.example.kotlionstudy.kotlionstudy

import kotlin.math.absoluteValue

/**
 * Created by BaoQi
 * Date : 2020/12/20
 * Des:Kotlin 的基本数值类型包括 Byte、Short、Int、Long、Float、Double 等。
 * 不同于 Java 的是，字符不属于数值类型，是一个独立的数据类型。
 */
fun main(args: Array<String>) {
    int()
    compare()
    typeConvert()
    opreator()
    char()
    arr()
    string()
}

/**
 * 十进制：123
 * 长整型以大写的 L 结尾：123L
 * 16 进制以 0x 开头：0x0F
 * 2 进制以 0b 开头：0b00001011
 * 注意：8进制不支持
 */
fun int() {
    val oneMillion = 1_000_000
    val creditCardNumber = 221010_19980908_9274L

    val ten = 123
    val hex = 0xFF
    val bytes = 0b0000_0001
}

/**
 * Kotlin 中没有基础数据类型，只有封装的数字类型，你每定义的一个变量，
 * 其实 Kotlin 帮你封装了一个对象，这样可以保证不会出现空指针。数字类型也一样，
 * 所以在比较两个数字的时候，就有比较数据大小和比较两个对象是否相同的区别了。
 * 在 Kotlin 中，三个等号 === 表示比较对象地址，两个 == 表示比较两个值大小。
 */
fun compare() {
    //当a和b的值赋值的是相同时候a==b和a===b 都为true

    val a: Int? = 100
    val b: Int? = 100
    println("compare():a==b is ${a == b},a===b is ${a === b}")
    val c: Int? = null
    println("compare():c==null is ${c == null}")
    c.toString()
}

/**
 * 由于不同的表示方式，较小类型并不是较大类型的子类型，较小的类型不能隐式转换为较大的类型。
 * 这意味着在不进行显式转换的情况下我们不能把 Byte 型值赋给一个 Int 变量
 */
fun typeConvert() {
    val a: Int = 1
    // val b: Long = a// error
    val b: Long = a.toLong()

    val c: Int = a + 0b0000_0001//自动转为范围大的类型 Int +byte-> Int
    val d: Long = a + 1L
}

fun opreator() {
    val a: Int = 0b0000_0000_0000_0000_0000_0000_0000_0100 //4
    println("opreator():a shl 1=${a shl 1}")
    println("opreator():a shr 1=${a shr 1}")
    println("opreator():a ushr 1=${a ushr 1}")

    val b: Int = 0b0000_0000_0000_0000_0000_0000_0000_0010 //2
    println("opreator():a and b=${a and b}")
    println("opreator():a or b=${a or b}")
    println("opreator():a xor b=${a xor b}")//异或
    println("opreator():a.inv()=${a.inv()}")//取反
}

fun char() {
    val a: Char = '\t'

    println("char():charMax=${Char.MAX_VALUE},charMin=${Char.MIN_VALUE}")
    println("char():a.toInt()=${a.toInt()}")// Char.toInt()对应的是编码的值
}

fun add3(num1: Int, num2: Int): Int {
    return num1 + num2
}

fun arr() {
    val a: Array<Int> = arrayOf(-1, 2, -3)
    val maxBy = a.maxBy { it.absoluteValue }
    println("arr():maxBy=$maxBy")
    val fold = a.fold(0, { acc: Int, i: Int -> acc + i })
    println("arr():fold=$fold")
    a[0] = 7
    val map = a.map { it.absoluteValue }
    map.forEach { println(it) }
}

fun string() {
    val text = " |哈哈哈|哈哈哈哈哈哈"
    println("string():text=${text.trimMargin()}")//删除多余的空白,默认 | 用作边界前缀
}


