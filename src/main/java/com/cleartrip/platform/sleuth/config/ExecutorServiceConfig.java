package com.cleartrip.platform.sleuth.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorServiceConfig {

    @Autowired
    private BeanFactory beanFactory;
    
    @Bean
    public Executor executor() {
        return new LazyTraceExecutor(beanFactory, Executors.newFixedThreadPool(2));
    }
    
    @Bean
    public ExecutorService executorService() {
        return new TraceableExecutorService(beanFactory, Executors.newFixedThreadPool(2));
    }
    
}
