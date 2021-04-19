package com.example.kotlionstudy.kotlionstudy.coroutine

import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author BaoQi
 * Date : 2021/4/1
 * Des: 协程
 *
 */
//https://juejin.cn/post/6844903854245412871
@ExperimentalCoroutinesApi
suspend fun main() {
    launch_default()
    launch_lazy()
    launch_atomic()
    launch_unDispatch()
}

/**
 * 立即执行协程体
 *
 * DEFAULT 是饿汉式启动，launch 调用后，会立即进入待调度状态，一旦调度器 OK 就可以开始执行
 *
 */
private suspend fun launch_default() {
    println("launch_default:")
    log(1)
    val job = GlobalScope.launch {
        log(2)
    }//没有立刻调用start和join不执行，是因为默认调度的后台线程是守护线程
    log(3)
    job.join()
    log(4)

}

/**
 * 只有在需要的情况下运行
 * LAZY 是懒汉式启动，launch 后并不会有任何调度行为，协程体也自然不会进入执行状态，直到我们需要它执行的时候
 */
private suspend fun launch_lazy() {
    println("launch_lazy:")
    log(1)
    val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
        log(2)
    }
    log(3)
    job.cancel()
    job.start()//已经cancel了，start启动了也不会再执行了
    log(4)
}

/**
 * 立即执行协程体，但在开始运行之前无法取消
 *
 * ATOMIC 只有涉及 cancel 的时候才有意义，cancel 本身也是一个值得详细讨论的话题，
 * 在这里我们就简单认为 cancel 后协程会被取消掉，也就是不再执行了。那么调用 cancel 的时机不同，
 * 结果也是有差异的，例如协程调度之前、开始调度但尚未执行、已经开始执行、执行完毕等等。
 */
@ExperimentalCoroutinesApi
private suspend fun launch_atomic() {
    println("launch_atomic:")
    log(1)
    GlobalScope.launch(start = CoroutineStart.ATOMIC) {
        log(2)
        delay(1000)
        log(3)
    }.apply {
        cancel()//delay 后的不会再执行了
    }
    log(4)
}

/**
 * 立即在当前线程执行协程体，直到第一个 suspend 调用
 *
 * UNDISPATCHED 不经过任何调度器即开始执行协程体。当然遇到挂起点之后的执行就取决于挂起点本身的逻辑以及上下文当中的调度器了。
 *
 */
@ExperimentalCoroutinesApi
private suspend fun launch_unDispatch() {
    println("luanch_unDisPatch")
    log(1)
    GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
        println(coroutineContext[Job])
        log(2)
        delay(1000)
        log(3)
    }.apply {
        log(4)
        join()
    }
    log(5)
}


fun log(any: Any) {
    val timeInMillis = Calendar.getInstance().timeInMillis
    val date = SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Date(timeInMillis))
    println("$date [${Thread.currentThread().name},${Thread.currentThread().isDaemon}] $any")
}