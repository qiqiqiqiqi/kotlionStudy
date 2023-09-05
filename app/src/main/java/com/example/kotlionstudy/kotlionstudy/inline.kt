package com.example.kotlionstudy.kotlionstudy
//https://juejin.cn/post/7100559670695165965
/**
 * 虽然使用内联可以减少一定的开销，但是不是每个地方都适合用内联修饰的。
 * 试想，若是都是内联函数，那么调用内联函数的时候会将整个函数体（实现）拷贝到调用处，如果是多次调用呢？岂不是重复的代码很多？
 */
fun main(args: Array<String>) {
    normalFun()
    inline2 {
        it + 7
    }
}

fun normalFun(): Int {
    return 7
}

/**
 * 此种场景下使用内联对性能是没有提升的。
 */
inline fun inlineFun(a: Int): Int {
    a.let {  }
    val b = 7
    return a + b
}

/**
 * 什么场景下使用呢？答案是函数参数是函数类型时使用。
 */
inline fun inline2(block: (Int) -> Int) {
    block(6)
}
