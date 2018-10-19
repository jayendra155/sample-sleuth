package com.cleartrip.platform.sleuth.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class DummyService {

    @Autowired
    private ExecutorService executorService;
    // private Tracer
    
    

    public void doNothing() {
        log.info("this method is not doing anything");
        Mono.defer(() -> {
            return this.doSomething();
        }).subscribe();
        Mono.fromCallable(() -> {
            log.info("Callable Mono");
            return Mono.just("");
        }).subscribe();
    }

    public Mono<Void> doSomething() {
        log.info("This method is doing something");
        return Mono.empty();
    }

    public void levelUp() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<String> data = service.submit(new NextLevel());
        while (!data.isDone()) {
            try {
                log.info("Data is : {}", data.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("Exception was found : {}", e.getMessage());
            }
        }
        log.info("Calling sleuth executor service");
        this.levelupAgain();
    }

    private void levelupAgain() {
        Future<String> data = this.executorService.submit(new NextLevel());
        while (!data.isDone()) {
            try {
                log.info("Data is : {}", data.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("Exception was found : {}", e.getMessage());
            }
        }
    }
}
