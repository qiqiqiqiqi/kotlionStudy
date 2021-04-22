package com.example.kotlionstudy.kotlionstudy.coroutine.suspend;

import com.example.kotlionstudy.kotlionstudy.coroutine.CoroutineKt;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.CancellableContinuation;

import static com.example.kotlionstudy.kotlionstudy.coroutine.CoroutineKt.log;
import static com.example.kotlionstudy.kotlionstudy.coroutine.suspend.CoroutineSuspendTestKt.returnImmediately;
import static com.example.kotlionstudy.kotlionstudy.coroutine.suspend.CoroutineSuspendTestKt.returnSuspended;
import static com.example.kotlionstudy.kotlionstudy.coroutine.suspend.CoroutinuaSuspendKt.hello;
import static kotlinx.coroutines.CancellableContinuationKt.suspendCancellableCoroutine;
import static kotlinx.coroutines.DelayKt.delay;
import static kotlinx.coroutines.ResumeModeKt.MODE_CANCELLABLE;

/**
 * @author BaoQi
 * Date : 2021/4/18
 * Des:
 */
public class CallCoroutine {

    public static void main(String[] args) {
        //  callHello();
        call();
    }

    private static void call() {
        try {
            RunSuspend runSuspend = new RunSuspend();
            ContinuationImpl table = new ContinuationImpl(runSuspend);
            table.resumeWith(Unit.INSTANCE);
            runSuspend.await();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static void callHello() {
        Object value = hello(new Continuation<Integer>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) { // ①
                if (o instanceof Integer) {
                    handleResult(o);
                } else {
                    Throwable throwable = (Throwable) o;
                    throwable.printStackTrace();
                }
            }
        });

        if (value == IntrinsicsKt.getCOROUTINE_SUSPENDED()) { // ②
            log("Suspended.");
        } else {
            handleResult(value);
        }
    }

    public static void handleResult(Object o) {
        log("The result is " + o);
    }

    /**
     * 1.协程的挂起函数本质上就是一个回调，回调类型就是 Continuation
     * 2.协程体的执行就是一个状态机，每一次遇到挂起函数，都是一次状态转移，就像我们前面例子中的 label 不断的自增来实现状态流转一样
     */
    public static class ContinuationImpl implements Continuation<Object> {

        private int label = 0;
        private final Continuation<Unit> completion;

        public ContinuationImpl(Continuation<Unit> completion) {
            this.completion = completion;
        }

        @Override
        public CoroutineContext getContext() {
            return EmptyCoroutineContext.INSTANCE;
        }

        /**
         * 所有的挂起都用了同一个回调来返回值，每次返回都是一次状态的转移，保证了代码的顺序执行
         * <p>
         * ？？？
         * 挂起的返回值是如何返回的 {@link RunSuspend#resumeWith}
         * ？？？
         *
         * @param o
         */
        @Override
        public void resumeWith(@NotNull Object o) {
            log("ContinuationImpl--resumeWith():o=" + o + "，lable=" + label);
            try {
                Object result = o;
                switch (label) {
                    case 0: {
                        log(1);
                        result = returnSuspended(this);
                        label++;
                        if (isSuspended(result)) return;
                    }
                    case 1: {//returnSuspended的回调会执行到这儿
                        log(result);//
                        log(2);
                        result = delay(1000, this);
                        label++;
                        if (isSuspended(result)) return;
                    }
                    case 2: {// delay完成后会返回resumeWith(kotlin.Unit）执行到这儿
                        log(3);
                        result = returnImmediately(this);
                        label++;
                        if (isSuspended(result)) return;
                    }
                    case 3: {//lable=2时没有挂起，会执行到lable=3处
                        log(result);
                        log(4);
                    }
                }
                completion.resumeWith(Unit.INSTANCE);
            } catch (Exception e) {
                completion.resumeWith(e);
            }
        }

        private boolean isSuspended(Object result) {
            return result == IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
    }

    public static class RunSuspend implements Continuation<Unit> {

        private Object result;

        @Override
        public CoroutineContext getContext() {
            return EmptyCoroutineContext.INSTANCE;
        }

        @Override
        public void resumeWith(@NotNull Object result) {
            log("RunSuspend--resumeWith():result=" + result);

            synchronized (this) {
                this.result = result;
                notifyAll(); // 协程已经结束，通知下面的 wait() 方法停止阻塞
            }
        }

        public void await() throws Throwable {
            synchronized (this) {
                while (true) {
                    Object result = this.result;
                    if (result == null) {
                        log("RunSuspend--await():result=" + result);

                        wait();
                    }// 调用了 Object.wait()，阻塞当前线程，在 notify 或者 notifyAll 调用时返回
                    else if (result instanceof Throwable) {
                        throw (Throwable) result;
                    } else {
                        log("RunSuspend--await():result=" + result);
                        return;
                    }
                }
            }
        }
    }


}

