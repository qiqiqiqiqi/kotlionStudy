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
    log(1)
    val returnImmediately = returnImmediately()
    log(returnImmediately)
    log(2)
    delay(1000)
    log(3)
    val returnSuspended = returnSuspended()
    log(returnSuspended)
    log(4)
}

suspend fun returnSuspended() = suspendCoroutineUninterceptedOrReturn<String> { continuation ->
    thread {
        Thread.sleep(1000)
        continuation.resume("Return suspended.")
    }
    COROUTINE_SUSPENDED
}

suspend fun returnImmediately() = suspendCoroutineUninterceptedOrReturn<String> { continuation ->
    log(1)
    "Return immediately."
}


