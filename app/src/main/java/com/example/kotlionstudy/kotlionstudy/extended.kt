package com.example.kotlionstudy.kotlionstudy

/**
 * @author BaoQi
 * Date : 2021/3/1
 * Des:扩展
 */
fun main(args: Array<String>) {
    val baseF = BaseF()
    log(baseF)
    baseF.foo2()
    CompanionClass.foo()
    BaseH()
    BaseHChild()

}

open class BaseD()

class BaseF : BaseD() {
    var datas: List<Int> = listOf(0, 1, 2)
    fun foo2() {
        println("BaseF.foo2()--${this.javaClass.simpleName},成员函数")
        println("BaseF.foo2()--datas.lastIndex=${datas.lastIndex},BaseF.size=${this.size}")
    }
}

/**
 * this关键字指代接收者对象(receiver object)(也就是调用扩展函数时, 在点号之前指定的对象实例)。
 */
fun BaseD.foo() {
    println("BaseD.foo()--${this.javaClass.simpleName}")
}

fun BaseF.foo() {
    println("BaseF.foo()--${this.javaClass.simpleName}")
}

/**
 * 扩展属性
 *
 * 除了函数，Kotlin 也支持属性对属性进行扩展:
 * 扩展属性只能被声明为 val,扩展属性允许定义在类或者kotlin文件中，不允许定义在函数中。初始化属性因为属性没有后端字段（backing field），
 * 所以不允许被初始化，只能由显式提供的 getter/setter 定义。
 */
val BaseF.size: Int
    get() = this.datas.size


val <T> List<T>.lastIndex: Int
    get() = this.size

/**
 * 若扩展函数和成员函数一致，则使用该函数时，会优先使用成员函数。
 */
fun BaseF.foo2() {
    println("BaseF.foo2()--${this.javaClass.simpleName}")
}

/**
 * 扩展函数是静态解析的
 *
 * 扩展函数是静态解析的，并不是接收者类型的虚拟成员，在调用扩展函数时，具体被调用的的是哪一个函数，由调用函数的的对象表达式来决定的，而不是动态的类型决定的:
 */
fun log(baseD: BaseD) {
    baseD.foo()// 一定是打印 BaseD.foo()
}

/**
 * 伴生对象的扩展
 * 如果一个类定义有一个伴生对象 ，你也可以为伴生对象定义扩展函数和属性。
 * 伴生对象通过"类名."形式调用伴生对象，伴生对象声明的扩展函数，通过用类名限定符来调用：
 */

class CompanionClass {
    companion object {
    }
}

fun CompanionClass.Companion.foo() {
    println("CompanionClass的伴生对象的扩展函数,CompanionClass的伴生对象的扩展属性：name=${CompanionClass.name},${this.javaClass.simpleName}")
}

val CompanionClass.Companion.name: String
    get() = "CompanionClass"

/**
 * 扩展声明为成员
 * 在一个类内部你可以为另一个类声明扩展。
 * 在这个扩展中，有个多个隐含的接受者，其中扩展方法定义所在类的实例称为分发接受者，而扩展方法的目标类型的实例称为扩展接受者。
 *
 * 假如在调用某一个函数，而该函数在分发接受者和扩展接受者均存在，则以扩展接收者优先，要引用分发接收者的成员你可以使用限定的 this 语法。
 */
class BaseG : BaseD() {
    fun foo() {
        println("BaseG-foo()")
    }
}

open class BaseH : BaseD() {
    fun baseH() {
        println("BaseH-baseH()")
    }

    private fun foo() {
        println("baseH-foo()")
    }

    //扩展BaseG
    open fun BaseG.foo2() {
        //持有扩展接受者的对象和分发接受者的对象
        println("BaseH--BaseG.foo2():this=${this.javaClass.simpleName},分发接受者=${this@BaseH.javaClass.simpleName}")
        foo() //调用 BaseG.foo()
        this@BaseH.foo()
        baseH() //调用 BaseH.baseH()
    }

    init {
        println("")
        BaseG().foo2()
    }
}

/**
 * 以成员的形式定义的扩展函数, 可以声明为 open , 而且可以在子类中覆盖.
 * 也就是说, 在这类扩展函数的派 发过程中, 针对分发接受者是虚拟的(virtual), 但针对扩展接受者仍然是静态的。
 */
class BaseHChild : BaseH() {
    override fun BaseG.foo2() {
        println("BaseHChild--BaseG.foo2():this=${this.javaClass.simpleName},分发接受者=${this@BaseHChild.javaClass.simpleName}")
    }

    init {
        println()
        BaseG().foo2()
    }
}

