package com.steammachine.org.junit5.extensions.timeout;

import com.steammachine.org.junit5.extensions.types.Api;
import com.steammachine.org.junit5.extensions.types.APILevel;

import java.util.Objects;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import static ru.socialquantum.common.utils.commonutils.CommonUtils.suppress;

/**
 *
 * Класс предназначенный для выполнения метода ограниченного таймаутом.
 *
 * Created 29/09/16 19:51
 * @author Vladimir Bogodukhov
 *         {@link TimedOutExecutor}
 **/
@Api(value = APILevel.experimental)
public class TimedOutExecutor {

    private class Interrupter implements Runnable {
        public void run() {
            try {
                Thread.sleep(timeout); /*Wait for timeout period and then kill this target*/
                target.interrupt(); /*Gracefully interrupt the waiting thread*/
                success = false;
            } catch (InterruptedException e) {
                success = true;
            }

            try {
                barrier.await(); /* Need to wait on this barrier */
            } catch (InterruptedException e) {
                /*If the Child and Interrupter finish at the exact same millisecond we'll get here*/
                /*In this weird case assume it failed*/
                success = false;
            } catch (@SuppressWarnings("TryWithIdenticalCatches") BrokenBarrierException e) {
                /*Something horrible happened, assume we failed*/
                success = false;
            }
        }

    }

    private final Thread interrupter;
    private final Thread target;
    private final long timeout;
    private final CyclicBarrier barrier;
    private boolean success;
    private Throwable executionException;


    /**
     * Mетод выполнения кода в рамках таймаута.
     * Метод выполняемый в target выполняется не более времени обозначенного в параметре timeout
     * Если код target выполняется больше чем timeout выполнение прерывается с исключением.
     *
     * @param target  - выполняемый код.
     * @param timeout -
     * @throws TestTimedOutException - исключение в случае просрочки выполнения
     *                               (когда время выполнения превысило таймаут).
     * @throws Throwable             - исключение в выполняемом коде target
     */
    @Api(value = APILevel.stable)
    public static void timeoutExecution(long timeout, TimedOutExecution target) throws Throwable {
        TimedOutExecutor timedOutExecutor = new TimedOutExecutor(target, timeout);
        if (!suppress(timedOutExecutor::execute)) {
            throw new TestTimedOutException(timeout, TimeUnit.MILLISECONDS);
        } else if (timedOutExecutor.executionException != null) {
            throw timedOutExecutor.executionException;
        }
    }

    /* -------------------------------------------------- privates --------------------------------------------- */

    /**
     * @param target  The Runnable target to be executed
     * @param timeout The time in milliseconds before target will be interrupted or stopped
     */
    private TimedOutExecutor(TimedOutExecution target, long timeout) {
        this.timeout = Objects.requireNonNull(timeout);
        this.target = new Thread(() -> {
            try {
                target.evaluate();
            } catch (Throwable e) {
                this.executionException = e;
            }

        });
        this.interrupter = new Thread(new Interrupter());
        this.barrier = new CyclicBarrier(2); /* There will always be just 2 threads waiting on this barrier*/
    }

    private boolean execute() throws InterruptedException {
        /*Start target and interrupter*/
        target.start();
        interrupter.start();

        /*Wait for target to finish or be interrupted by interrupter*/
        target.join();

        interrupter.interrupt(); /*stop the interrupter*/
        try {
            barrier.await(); /*Need to wait on this barrier to make sure status is set*/
        } catch (BrokenBarrierException e) {
            /*Something horrible happened, assume we failed*/
            success = false;
        }

        return success; /*status is set in the Interrupter inner class*/
    }

}
