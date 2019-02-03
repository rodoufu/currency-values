package com.github.rodoufu.currencyvalues.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Application configuration.
 */
@Configuration
@EnableAsync
public class ApplicationConfig {
	@Value("${async.executor.pool.size.core}")
	private Integer asyncExecutorPoolSizeCore;
	@Value("${async.executor.pool.size.max}")
	private Integer asyncExecutorPoolSizeMax;
	@Value("${async.executor.pool.queue.capacity}")
	private Integer asyncExecutorPoolQueueCapacity;
	@Value("${async.executor.thread.prefix}")
	private String asyncExecutorThreadPrefix;

	/**
	 * Configures the async task thread pool.
	 * @return The Executor.
	 */
	@Bean(name = "asyncExecutor")
	public Executor asyncExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(asyncExecutorPoolSizeCore);
		executor.setMaxPoolSize(asyncExecutorPoolSizeMax);
		executor.setQueueCapacity(asyncExecutorPoolQueueCapacity);
		executor.setThreadNamePrefix(asyncExecutorThreadPrefix);
		executor.initialize();
		return executor;
	}

}
