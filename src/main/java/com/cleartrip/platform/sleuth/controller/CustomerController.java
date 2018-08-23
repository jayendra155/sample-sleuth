package com.cleartrip.platform.sleuth.controller;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.cleartrip.platform.sleuth.model.Customer;
import com.cleartrip.platform.sleuth.repository.CustomerRepository;
import com.cleartrip.platform.sleuth.service.DummyService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;
    
    @Autowired
    private DummyService dummyService;

    @GetMapping
    public HttpEntity<Flux<Customer>> getCustomerByName(@RequestParam String name, ServerWebExchange exchange) {
        log.info("GET method invoked");
        this.dummyService.doNothing();
        this.dummyService.levelUp();
        ServerHttpRequest request = exchange.getRequest();
        Set<String> keys = request.getHeaders().keySet();
        for (String key : keys) {
            String value = request.getHeaders().get(key).stream().map(Object::toString)
                    .collect(Collectors.joining(";"));
            log.info("{} -> {}", key, value);
        }
        return ResponseEntity.ok(customerRepo.findByName(name));
    }

    @PutMapping
    public HttpEntity<Mono<Customer>> createANewCustomer(@RequestBody Customer customer) {
        log.info("PUT method invoked");
        try {
            customerRepo.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(Mono.just(customer));
        } catch (Exception e) {
            log.error("Unable to create new customer");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Mono.empty());
        }
    }

    @PatchMapping
    public Mono<HttpEntity<Mono<Customer>>> updateCustomerName(@RequestBody Customer customer) {
        log.info("PATCH method invoked");
        try {
            if (Objects.nonNull(customer) && Objects.nonNull(customer.getName())) {
                String name = customer.getName();
                Mono<Customer> dbCustomer = customerRepo.findById(customer.getId());
                return dbCustomer.defaultIfEmpty(new Customer(null, null, null)).flatMap(cust -> {
                    if (Objects.isNull(cust.getId())) {
                        log.error("Customer not found to update");
                    } else {
                        cust.setName(name);
                        customerRepo.save(cust);
                        return Mono.just(ResponseEntity.ok().body(Mono.just(cust)));
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(Mono.just(cust)));
                });
            }
        } catch (Exception e) {
            log.error("Unable to create new customer");
        }
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Mono.empty()));
    }

}
