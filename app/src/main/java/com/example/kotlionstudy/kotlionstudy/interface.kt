package com.example.kotlionstudy.kotlionstudy

/**
 * @author BaoQi
 * Date : 2021/3/1
 * Des:
 */
fun main(args: Array<String>) {
    BaseImpl()

}

interface BaseA {
    fun foo() {
        println("BaseA--foo()")
    }

    fun bar() {
        println("BaseA--bar()")
    }
}

interface BaseB {
    fun foo() {
        println("BaseB--foo()")
    }

    fun bar()//未实现，没有方法体，是抽象的
}

class BaseImpl : BaseA, BaseB {
    init {
        foo()
        bar()
    }
    override fun foo() {
        super<BaseA>.foo()
        super<BaseB>.foo()
    }

    override fun bar() {
        super<BaseA>.bar()
        // BaseB没有实现改方法，显然不能调用
        // super<BaseB>.bar()

    }
}


