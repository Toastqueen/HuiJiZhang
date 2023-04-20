package com.toast.fuchuang.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author ming
 * @version 1.0.0
 * @date 2020/8/21 18:23
 **/

@Configuration
@EnableAsync
public class ScheduleConfig implements AsyncConfigurer {
    /**
     * 根据系统核心数来设置线程数: private static int corePoolSize = Runtime.getRuntime().availableProcessors();
     * 核心线程数
     */
    private static int corePoolSize = 100;
    /**
     * 最大线程数
     */
    private static int maximumPoolSize = corePoolSize * 2;
    /**
     * 线程存活时间
     */
    private static long keepAliveTime = 101L;
    /**
     * 阻塞队列大小
     */
    private static final int CAPACITY = 1000;
    /**
     * 线程池命名
     */
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("my-work-pool-").build();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
            keepAliveTime, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(CAPACITY),
            namedThreadFactory,
            (r, executor) -> userDefinedRejectionPolicy(r));

    /**
     * 拒绝策略；当任务太多来不及处理时，如何拒绝任务；
     *
     * @param r Runnable
     */
    private static void userDefinedRejectionPolicy(Runnable r) {
        //1、AbortPolicy策略：该策略会直接抛出异常，阻止系统正常工作；
        //
        //2、CallerRunsPolicy策略：如果线程池的线程数量达到上限，该策略会把任务队列中的任务放在调用者线程当中运行；
        //
        //3、DiscardOldestPolicy策略：该策略会丢弃任务队列中最老的一个任务，也就是当前任务队列中最先被添加进去的，马上要被执行的那个任务，并尝试再次提交；
        //
        //4、DiscardPolicy策略：该策略会默默丢弃无法处理的任务，不予任何处理。当然使用此策略，业务场景中需允许任务的丢失；
        System.out.println(r.toString() + "执行了拒绝策略");
        // TODO: 2020/8/19 这里写拒绝策略的相关逻辑 ，在某些场景下我们应该尽量避免丢弃任务
    }

    @Override
    public Executor getAsyncExecutor() {
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }

}
