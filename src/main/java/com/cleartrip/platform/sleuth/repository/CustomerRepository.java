package com.cleartrip.platform.sleuth.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.cleartrip.platform.sleuth.model.Customer;

import reactor.core.publisher.Flux;

/**
 * @author jayendrasingh
 *
 */
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

    Flux<Customer> findByName(String name);
}
