package com.example.kotlionstudy.kotlionstudy

/**
 * @author BaoQi
 * Date : 2021/2/28
 * Des: 继承
 */
fun main() {
    val personA = PersonA("dd")

    val student = Student("Student")

    val primaryStudent = PrimaryStudent(100, "PrimaryStudent")


}

// open 可继承
open class BaseE(val name: String)

/**
 * 子类有主构造函数
 *
 * 如果子类有主构造函数， 则基类必须在主构造函数中立即初始化。
 */
open class PersonA(name: String) : BaseE(name) {


    init {
        println("${this.javaClass.simpleName}:${name}")
    }

}

/**
 * 子类没有主构造函数
 *
 * 如果子类没有主构造函数，则必须在每一个二级构造函数中用 super 关键字初始化基类，或者在代理另一个构造函数。
 * 初始化基类时，可以调用基类的不同构造方法。
 */
open class Student : PersonA {
    constructor(name: String) : super(name)

    //private 不能重写
    private val score: Int = 99
    open var age = 9

    open fun study() {
        println("Student-study()")
    }
}

interface StudentInterface {
    val score: Int
    fun study() {
        println("StudentInterface-study()")
    }
}

/**
 * 重写
 * 在基类中，使用fun声明函数时，此函数默认为final修饰，不能被子类重写。
 * 如果允许子类重写该函数，那么就要手动添加 open 修饰它, 子类重写方法使用 override 关键词：
 *
 * 属性重写使用 override 关键字，属性必须具有兼容类型，每一个声明的属性都可以通过初始化程序或者getter方法被重写
 * (属性重写只能在主构造中)
 */
class PrimaryStudent(override var score: Int, name: String) : Student(name), StudentInterface {

    override var age: Int = super.age
        get() {
            return 0
        }
        set(value) {
            field = if (value < 18) {
                value
            } else {
                20
            }
        }


    override fun study() {
        println("PrimaryStudent-study()")
        //有重名的方法，使用super范型去选择性地调用父类的实现。
        super<Student>.study()
        super<StudentInterface>.study()
    }

    init {
        study()
        println("PrimaryStudent-init:age=$age")
        println("PrimaryStudent-init:score=$score")
        score=90
        println("PrimaryStudent-init:score=$score,修改后")
    }
}

