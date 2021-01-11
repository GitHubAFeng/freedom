package com.afeng.admin.module.admin.aspectj;

import com.afeng.admin.common.util.SpringUtils;
import org.springframework.core.task.TaskExecutor;

import java.util.TimerTask;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理器
 *
 * @author liuhulu
 */
public class AsyncManager {

    /**
     * 异步操作任务调度线程池
     */
    private TaskExecutor executor = SpringUtils.getBean("taskExecutor");
    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService executorService = SpringUtils.getBean("scheduledExecutorService");

    /**
     * 单例模式
     */
    private AsyncManager() {
    }

    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me() {
        return me;
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(FutureTask<Object> task) {
        executor.execute(task);
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(TimerTask task) {
        executorService.schedule(task, 10, TimeUnit.MILLISECONDS);
    }

}
