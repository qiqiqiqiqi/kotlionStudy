package com.example.kotlionstudy.kotlionstudy.coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @author BaoQi
 * Date : 2021/4/6
 * Des: 协程调度
 */


/**
 *
 *
 * look [CoroutineDispatcher]
 *
 */
abstract class CustomCoroutineDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
    }
}

typealias Callback = (a: Int) -> Unit

fun getUser(callback: Callback) {
    callback(0)
}

/**
 * suspendCoroutine 这个方法并不是帮我们启动协程的，
 * 它运行在协程当中并且帮我们获取到当前协程的 Continuation 实例，
 * 也就是拿到回调，方便后面我们调用它的 resume 或者 resumeWithException 来返回结果或者抛出异常。
 */
suspend fun getUserCoroutine() = suspendCoroutine<Int> { continuation ->
    getUser {
        continuation.resume(it)
    }
}

suspend fun main() {

    uiDispatch()
    // bindThreadDispatch()
    // moreThreadRunCoroutine()
}

/**
 * 编写 UI 相关程序
 */
private suspend fun uiDispatch() {
    println("uiDispatch")
    val launch = GlobalScope.launch(Dispatchers.Default) {
        val userCoroutine = getUserCoroutine()
        log("userCoroutine=$userCoroutine")
    }
    launch.join()
    // log(1)//没有这句上方不执行？？？
}

/**
 * 绑定到任意线程的调度器
 */
private suspend fun bindThreadDispatch() {
    println("bindThreadDispatch")

    val myDispatcher =
        Executors.newSingleThreadExecutor { r -> Thread(r, "bindThreadDispatch") }
            .asCoroutineDispatcher()
    GlobalScope.launch(myDispatcher) {
        log(1)
    }.join()
    log(2)
    myDispatcher.close()
}

private suspend fun moreThreadRunCoroutine() {
    println("moreThreadRunCoroutine")
    Executors.newFixedThreadPool(10)
        .asCoroutineDispatcher().use { dispatcher ->
            GlobalScope.launch(dispatcher) {
                log(1)
                val job = async {
                    log(2)
                    delay(1000)
                    log(3)
                    "Hello"
                }
                log(4)
                val result = job.await()
                log("5. $result")
            }.join()
            log(6)
        }

}

