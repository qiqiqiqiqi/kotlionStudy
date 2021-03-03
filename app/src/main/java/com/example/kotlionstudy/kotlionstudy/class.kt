package com.example.kotlionstudy.kotlionstudy

/**
 * Created by BaoQi
 * Date : 2021/1/7
 * Des:
 *
 *
 */

fun main(args: Array<String>) {
    println("次级构造")
    val person1 = Person()
    println("person1=$person1")

    println("主构造")
    val person2 = Person(0, "person")
    println("person2=$person2")

    println("复写set方法")
    val person3 = Person()
    person3.age = -9
    println("person3=${person3.age}")

    println("延时初始化")
    val person4 = Person()
    person4.height = "180"
    println("person4=${person4.height}")
}

// 定义空类
class Empty

/**
 * Koltin 中的类可以有一个 主构造器，以及一个或多个次构造器，主构造器是类头部的一部分，位于类名称之后:
 * 1.如果类有主构造函数，每个次构造函数都要，或直接或间接通过另一个次构造函数代理主构造函数。在同一个类中代理另一个构造函数使用 this 关键字：[Person(name: String, age: Int)].
 */
open class Person constructor(open val sex: Int, val name: String = "default") {
    //成员变量一定要赋值，类型后面加了？可以赋值为null，没有的不行

    var age1: Int // 错误: 需要一个初始化语句, 默认实现了 getter 和 setter 方法
    // var age2: Int？ // 错误: 需要一个初始化语句, 默认实现了 getter 和 setter 方法

    // val age3: Int // 错误: 需要一个初始化语句, 默认实现了 getter 方法
    // val age4: Int？ // 错误: 需要一个初始化语句, 默认实现了 getter 方法

    // var age5: Int = null//报错，不能赋值为null
    // val age6: Int = null//报错，不能赋值为null

    //类型后面加?表示可为空
    var age: Int? = null
        get() {
            return field
        }
        set(value) {
            field = if (value != null) {
                if (value < 0) {
                    0
                } else {
                    value
                }
            } else {
                0
            }
        }

    //延时初始化
    lateinit var height: String


    init {
        age = 22
        age1 = 1
        println("主构造函数person")
    }

    override fun toString(): String {
        return "Person(sex=$sex, name='$name')"
    }

    //如果类有主构造函数，次级构造都要直接或间接代理主构造
    constructor() : this(1)

    constructor(name: String, age: Int) : this(1, name) {
        this.age = age
    }

}

