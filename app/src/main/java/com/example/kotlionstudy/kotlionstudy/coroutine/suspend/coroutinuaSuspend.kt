package com.example.kotlionstudy.kotlionstudy.coroutine.suspend

import com.example.kotlionstudy.kotlionstudy.coroutine.log
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.*

/**
 * @author BaoQi
 * Date : 2021/4/18
 * Des:https://juejin.cn/post/6844903854253834248
 *
 *  挂起是有返回值的
 *  在Java中调用挂起函数可以拿到COROUTINE_SUSPENDED返回值
 *  在kotlin中调用返回COROUTINE_SUSPENDED代表要挂起了
 *
 */
suspend fun hello() = suspendCoroutineUninterceptedOrReturn<Int> { continuation ->
    log(1)
    thread {
        Thread.sleep(1000)
        log("2,将要返回结果")
        continuation.resume(1024)
    }
    log(3)
    COROUTINE_SUSPENDED
}

