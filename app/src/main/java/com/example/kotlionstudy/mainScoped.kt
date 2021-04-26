package com.example.kotlionstudy

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.View
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

interface MainScoped {
    companion object {
        internal val scopeMap = IdentityHashMap<Activity, CoroutineScope>()
    }

    val mainScope: CoroutineScope
        get() = scopeMap[this as Activity]!!

    fun createScope() {
        Log.d(this.javaClass.simpleName, "createScope()")
        scopeMap[this as Activity] ?: MainScope().also { scopeMap[this] = it }
    }

    fun destroyScope() {
        Log.d(this.javaClass.simpleName, "destroyScope()")
        scopeMap.remove(this as Activity)?.cancel()
    }

    fun <T> withScope(block: CoroutineScope.() -> T) = with(mainScope, block)

}


class AutoDisposableJob(val view: View, val job: Job) : Job by job,
    View.OnAttachStateChangeListener {
    init {
        if (isViewAttached()) {
            view.addOnAttachStateChangeListener(this)
        } else {
            cancel()
        }

        invokeOnCompletion { view.removeOnAttachStateChangeListener(this) }
    }

    private fun isViewAttached() =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && view.isAttachedToWindow || view.windowToken != null

    override fun onViewDetachedFromWindow(v: View?) {
        Log.d(this.javaClass.simpleName, "onViewDetachedFromWindow()")
        view.removeOnAttachStateChangeListener(this)
        cancel()
    }

    override fun onViewAttachedToWindow(v: View?) {

    }


}

fun Job.asAutoDisposableJob(view: View) = AutoDisposableJob(view, this)


fun View.onClick(
    context: CoroutineContext = Dispatchers.Main,
    block: suspend CoroutineScope.(view: View) -> Unit
) {
    setOnClickListener {
        GlobalScope.launch(context, CoroutineStart.DEFAULT) { block(it) }.asAutoDisposableJob(it)
    }

}
