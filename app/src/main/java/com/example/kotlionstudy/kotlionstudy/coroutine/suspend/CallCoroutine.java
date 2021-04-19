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
    public static void block(CancellableContinuation<Object> cancellableContinuation) {
    }

    public static void main(String[] args) throws Throwable {

        //  callHello();
        call();
    }

    private static void call() throws Throwable {
        RunSuspend runSuspend = new RunSuspend();
        ContinuationImpl table = new ContinuationImpl(runSuspend);
        table.resumeWith(Unit.INSTANCE);
        runSuspend.await();
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

        @Override
        public void resumeWith(@NotNull Object o) {
            System.out.println("resumeWith():o=" + o);
            try {
                Object result = o;
                switch (label) {
                    case 0: {
                        log(1);
                        result = returnSuspended(this);
                        label++;
                        if (isSuspended(result)) return;
                    }
                    case 1: {
                        log(result);
                        log(2);
                        result = delay(1000, this);
                        label++;
                        if (isSuspended(result)) return;
                    }
                    case 2: {
                        log(3);
                        result = returnImmediately(this);
                        label++;
                        if (isSuspended(result)) return;
                    }
                    case 3: {
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
            synchronized (this) {
                this.result = result;
                notifyAll(); // 协程已经结束，通知下面的 wait() 方法停止阻塞
            }
        }

        public void await() throws Throwable {
            synchronized (this) {
                while (true) {
                    Object result = this.result;
                    if (result == null)
                        wait(); // 调用了 Object.wait()，阻塞当前线程，在 notify 或者 notifyAll 调用时返回
                    else if (result instanceof Throwable) {
                        throw (Throwable) result;
                    } else return;
                }
            }
        }
    }


}

