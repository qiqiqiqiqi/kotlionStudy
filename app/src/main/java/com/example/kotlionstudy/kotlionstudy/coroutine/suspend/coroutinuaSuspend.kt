package com.example.kotlionstudy.kotlionstudy.coroutine.suspend

import com.example.kotlionstudy.kotlionstudy.coroutine.log
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.*

/**
 * @author BaoQi
 * Date : 2021/4/18
 * Des:https://juejin.cn/post/6844903854253834248
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

