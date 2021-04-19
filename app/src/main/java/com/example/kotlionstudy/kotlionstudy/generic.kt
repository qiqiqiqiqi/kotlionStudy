package com.example.kotlionstudy.kotlionstudy

/**
 * @author BaoQi
 * Date : 2021/3/4
 * Des: 泛型
 */

/**
 * 型变
 * Kotlin 中没有通配符类型，它有两个其他的东西：声明处型变（declaration-site variance）与类型投影（type projections）。
 */

fun main(args: Array<String>) {
    val producer = Producer<String>("土豆")
    val produce = producer.produce()
    println("produce=$produce")
    ProducerA<String>().produce("红薯")
}

/**
 * 声明处型变
 *
 * 声明处的类型变异使用协变注解修饰符：in、out，消费者 in, 生产者 out。
 * 使用 out 使得一个类型参数协变，协变类型参数只能用作输出，可以作为返回值类型但是无法作为入参的类型：
 */
class Producer<out T>(private val source: T) {
    fun produce(): T {
        return source
    }
}


/**
 * in 使得一个类型参数逆变，逆变类型参数只能用作输入，可以作为入参的类型但是无法作为返回值的类型：
 */
class ProducerA<in T> {
    fun produce(t: T) {
        println("ProducerA--produce():t=$t")
    }
}


class BaseBo


