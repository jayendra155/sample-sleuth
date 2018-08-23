package com.cleartrip.platform.sleuth.service;

import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NextLevel implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        log.info("We are going next level!! ohh yeah");
        return "hehe";
    }

}
