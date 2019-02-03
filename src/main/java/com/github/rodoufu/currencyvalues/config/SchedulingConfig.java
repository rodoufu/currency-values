package com.github.rodoufu.currencyvalues.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Application configuration.
 */
@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {
    @Value("${scheduled.task.pool.size}")
    private Integer scheduledTaskPoolSize;
    @Value("${scheduled.task.thread.prefix}")
    private String scheduledTaskThreadPrefix;

    /**
     * Configure the scheduled task thread pool.
     *
     * @param scheduledTaskRegistrar The ScheduledTaskRegistrar.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(scheduledTaskPoolSize);
        threadPoolTaskScheduler.setThreadNamePrefix(scheduledTaskThreadPrefix);
        threadPoolTaskScheduler.initialize();
        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }

}
