package com.example.kotlionstudy.data

/**
 * @author BaoQi
 * Date : 2021/3/4
 * Des:
 */

/**
 * 枚举类最基本的用法是实现一个类型安全的枚举。
 * 枚举常量用逗号分隔,每个枚举常量都是一个对象。
 */
fun main(args: Array<String>) {
    println("Color.valueOf(\"BLUE\")=${Color.valueOf("BLUE")};color(\"BLUE\")=${color("BLUE")}")
    println("${Color.RED.ordinal}")
    println("${ValueTest.ONE.ordinal}")

}

enum class Color {
    RED, BLUE, GREEN;
}

fun color(name: String): Color {
    var values: Array<Color> = Color.values()
    values.forEach {
        if (it.name.equals(name)) {
            return it
        }
    }
    return Color.RED
}

enum class ValueTest(value:Int){
    ONE(100),
    TWO(200);
}