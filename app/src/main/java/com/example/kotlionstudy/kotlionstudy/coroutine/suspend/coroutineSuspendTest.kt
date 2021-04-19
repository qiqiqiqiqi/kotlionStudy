package com.example.kotlionstudy.kotlionstudy.coroutine.suspend

import com.example.kotlionstudy.kotlionstudy.coroutine.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.*

/**
 * @author BaoQi
 * Date : 2021/4/18
 * Des:
 */
suspend fun main() {
//    log("hello=${hello()}")
    println()
    log(1)
    log(returnSuspended())
    log(2)
    delay(1000)
    log(3)
    log(returnImmediately())
    log(4)
}

suspend fun returnSuspended() = suspendCoroutineUninterceptedOrReturn<String> { continuation ->
    thread {
        Thread.sleep(1000)
        continuation.resume("Return suspended.")
    }
    COROUTINE_SUSPENDED
}

suspend fun returnImmediately() = suspendCoroutineUninterceptedOrReturn<String> {
    log(1)
    "Return immediately."
}


