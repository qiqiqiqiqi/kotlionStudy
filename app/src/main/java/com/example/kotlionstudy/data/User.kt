package com.example.kotlionstudy.data

/**
 * @author BaoQi
 * Date : 2021/3/3
 * Des:
 */

/**
 * 为了保证生成代码的一致性以及有意义，数据类需要满足以下条件：
 * 主构造函数至少包含一个参数。
 * 所有的主构造函数的参数必须标识为val 或者 var ;
 * 数据类不可以声明为 abstract, open, sealed 或者 inner;
 * 数据类不能继承其他类 (但是可以实现接口)。
 */
data class User(val name: String, val age: Int) {
}