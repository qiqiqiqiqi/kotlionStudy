package com.example.kotlionstudy.kotlionstudy

/**
 * @author BaoQi
 * Date : 2021/2/28
 * Des:
 */

fun main(args: Array<String>) {
    val chinesePerson = ChinesePerson()
    println("${chinesePerson.feature()}::${chinesePerson.area()}")
    //嵌套类的使用
    val nested = ChinesePerson.Nested()
    nested.nested()
    //内部类的使用
    val inner = ChinesePerson().Inner()
    inner.innerTest()
    //匿名内部类
    chinesePerson.testTestInterface()

}

interface Base {}

abstract class BasePerson : Base {
    protected val TAG: String = this.javaClass.simpleName
    abstract fun feature(): String
    abstract fun area(): String
}

class ChinesePerson : BasePerson() {
    val name = "ChinesePerson"
    override fun feature(): String {
        return "$TAG--黄皮肤"
    }

    override fun area(): String {
        return "$TAG--亚洲"
    }

    /**
     * 嵌套类
     */
    class Nested {
        fun nested() {
            println("Nested--nested():测试嵌套类的使用")

        }
    }

    /**
     * 内部类
     */

    inner class Inner {
        fun innerTest() {
            //内部类持有外部类的引用
            println("ChinesePerson.Inner--innerTest():${this@ChinesePerson.name}")
        }
    }

    fun setTestInterface(test: TestInterface) {
        test.onTestInterface()
    }

    fun testTestInterface() {
        //（对象表达式）匿名内部类
        setTestInterface(object : TestInterface {
            override fun onTestInterface() {
                println("$TAG-testTestInterface():测试匿名内部类,${this@ChinesePerson.name}")
            }
        })
    }

}

interface TestInterface {
    fun onTestInterface()
}



