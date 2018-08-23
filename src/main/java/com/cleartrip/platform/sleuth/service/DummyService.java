package com.cleartrip.platform.sleuth.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class DummyService {

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
    }
}
