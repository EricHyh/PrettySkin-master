package com.hyh.prettyskin.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Administrator
 * @description
 * @data 2018/3/20
 */

public class PrettySkinThread {

    private static volatile ThreadPoolExecutor sExecutorService;

    private static AtomicLong sThreadCount = new AtomicLong(0);

    private static ExecutorService getExecutorService() {
        if (sExecutorService != null) {
            return sExecutorService;
        }
        synchronized (PrettySkinThread.class) {
            if (sExecutorService == null) {
                sExecutorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 30, TimeUnit.SECONDS,
                        new SynchronousQueue<>(), createThreadFactory());
            }
            return sExecutorService;
        }
    }

    public static void execute(Runnable command) {
        getExecutorService().execute(command);
    }

    private static ThreadFactory createThreadFactory() {
        return runnable -> {
            Thread result = new Thread(runnable, "PrettySkinThread-" + sThreadCount.getAndIncrement());
            result.setDaemon(true);
            return result;
        };
    }
}