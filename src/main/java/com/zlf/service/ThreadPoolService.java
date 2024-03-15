package com.zlf.service;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zlf
 * @time: 2024/3/14
 */
@Slf4j
public class ThreadPoolService {

    private static final int DEFAULT_CORE_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAX_QUEUE_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int QUEUE_INIT_MAX_SIZE = 500;
    private volatile static ThreadPoolExecutor executor;

    private ThreadPoolService() {
    }

    // 获取单例的线程池对象
    public static ThreadPoolExecutor getInstance() {
        if (executor == null) {
            synchronized (ThreadPoolService.class) {
                if (executor == null) {
                    //拒绝策略
                    executor = new ThreadPoolExecutor(
                            DEFAULT_CORE_SIZE,// 核心线程数
                            MAX_QUEUE_SIZE, // 最大线程数
                            10L, // 闲置线程存活时间
                            TimeUnit.SECONDS,// 时间单位
                            new LinkedBlockingDeque<>(QUEUE_INIT_MAX_SIZE),// 线程队列
                            Executors.defaultThreadFactory(),// 线程工厂
                            (r, executor) -> {
                                try {
                                    executor.getQueue().put(r);
                                } catch (InterruptedException e) {
                                    log.error("线程处理拒绝策略失败:{}", e.getMessage());
                                }
                            }
                    );
                }
            }
        }
        return executor;
    }

    public void execute(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        executor.execute(runnable);
    }

    // 从线程队列中移除对象
    public void cancel(Runnable runnable) {
        if (executor != null) {
            executor.getQueue().remove(runnable);
        }
    }

}