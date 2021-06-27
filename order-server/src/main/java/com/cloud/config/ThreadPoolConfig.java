package com.cloud.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池配置
 *
 * @Author qiankaixia
 * @Date 2020/05/11
 */
@Configuration
public class ThreadPoolConfig {

    @Resource
    MeterRegistry meterRegistry;

    @PostConstruct
    public void init() {

    }

    /**
     * 单宝运营任务线程池
     *
     * @return
     */
    @Bean(name = "batteryOperationExecutor")
    public ExecutorService getBatteryOperationExecutor() {
        AtomicInteger prometheusThreadCounter = new AtomicInteger(1);
        ThreadPoolExecutor promExecutor = new ThreadPoolExecutor(
                10,
                20,
                2,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(500),
                r -> new Thread(r, "battery-operation-" + prometheusThreadCounter.getAndIncrement()));
        ExecutorServiceMetrics.monitor(meterRegistry, promExecutor, "battery-operation-executor");
        return promExecutor;
    }


    /**
     * 渠道商异步查询组织信息 线程池
     *
     * @return
     */
    @Bean(name = "departmentQueryExecutor")
    public ThreadPoolExecutor orderQueryThreadPool() {
        return new ThreadPoolExecutor(20, 30,
                180, TimeUnit.SECONDS, new ArrayBlockingQueue<>(500), new ThreadPoolExecutor.DiscardPolicy());
    }

}
