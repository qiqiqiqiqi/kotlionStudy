package com.example.kotlionstudy.kotlionstudy.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * @author BaoQi
 * Date : 2021/4/6
 * Des: 协程拦截器
 */
class CustomContinuationInterceptor : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return CustomContinuation(continuation)
    }


}

/**
 * 它拦截协程的方法也很简单，因为协程的本质就是回调 + “黑魔法”，而这个回调就是被拦截的 Continuation 了。
 */
class CustomContinuation<T>(val continuation: Continuation<T>) : Continuation<T> {
    override val context: CoroutineContext
        get() = continuation.context

    override fun resumeWith(result: Result<T>) {
        log("${this.javaClass.simpleName}:result=${result}")
        continuation.resumeWith(result)
    }
}

suspend fun main() {
    GlobalScope.launch(CustomContinuationInterceptor()) {
        log(1)
        val job = async {
            log(2)
            delay(1000)
            log(3)
            "Hello"
        }
        log(4)
        val result = job.await()
        log("5--result=$result")
    }.join()
    log(6)
}
