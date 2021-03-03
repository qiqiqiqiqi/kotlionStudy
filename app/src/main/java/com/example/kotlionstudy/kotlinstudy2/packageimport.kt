package com.example.kotlionstudy.kotlinstudy2

/**
 * @author BaoQi
 * Date : 2021/3/2
 * Des:
 */
import com.example.kotlionstudy.kotlionstudy.BaseF
import com.example.kotlionstudy.kotlionstudy.CompanionClass
import com.example.kotlionstudy.kotlionstudy.foo // 导入所有名为 foo 的扩展

fun main(args: Array<String>) {
    CompanionClass.foo()
    BaseF().foo()
}