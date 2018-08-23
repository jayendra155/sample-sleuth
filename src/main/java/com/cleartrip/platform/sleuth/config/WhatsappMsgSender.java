package com.cleartrip.platform.sleuth.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WhatsappMsgSender {

//    private Map<HttpStatus, String> map;
//    
//    @Autowired
//    private ApplicationContext context;
//    
//    public void sendMessage(Object object, HttpStatus status) {
//        //sender
//           sender =  context.getBean(map.get(status) + "whatsappMessageSender");
//    }
//    
//    @PostConstruct
//    public void createMap() {
//        map = new HashMap<>();
//        map.put(HttpStatus.OK, value)
//    }
}
