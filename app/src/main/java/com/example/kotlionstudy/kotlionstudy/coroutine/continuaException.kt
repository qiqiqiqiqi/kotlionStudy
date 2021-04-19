package com.example.kotlionstudy.kotlionstudy.coroutine

import kotlinx.coroutines.*

/**
 * @author BaoQi
 * Date : 2021/4/10
 * Des:
 */
//https://juejin.cn/post/6844903854245429255
suspend fun main() {
    // coroutineScopeTest()
    supervisorScopeTest()
}

private suspend fun coroutineScopeTest() {
    println("coroutineScopeTest()")
    log(1)
    try {
        coroutineScope { //①
            log(2)
            launch(exceptionHandler + CoroutineName("②")) { // ②
                log(3)
                launch(exceptionHandler + CoroutineName("③")) { // ③
                    log(4)
                    delay(1000)
                    throw ArithmeticException("Hey!!")
                }
                log(5)
            }
            log(6)
            val job = launch { // ④
                log(7)
                delay(1000)
            }
            try {
                log(8)
                job.join()
                log(9)
            } catch (e: Exception) {
                log("10. $e")
            }
        }
        log(11)//为什么没有打印,等待①执行完
    } catch (e: Exception) {
        log("12. $e")
    }
    log(13)
}

private suspend fun supervisorScopeTest() {
    println("supervisorScopeTest()")
    log(1)
    try {
        supervisorScope { //①
            log(2)
            launch(exceptionHandler + CoroutineName("②")) { // ②
                log(3)
                launch(exceptionHandler + CoroutineName("③")) { // ③
                    log(4)
                    delay(100)
                    throw ArithmeticException("Hey!!")
                }
                log(5)
            }
            log(6)
            val job = launch { // ④
                log(7)
                delay(1000)
            }
            try {
                log(8)
                job.join()
                log("9")
            } catch (e: Exception) {
                log("10. $e")
            }
        }
        log(11)
    } catch (e: Exception) {
        log("12. $e")
    }
    log(13)
}


val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    log("${coroutineContext[CoroutineName]} $throwable")
}


