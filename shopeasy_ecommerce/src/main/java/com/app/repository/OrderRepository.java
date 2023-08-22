package com.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.collections.Order;

public interface OrderRepository extends MongoRepository<Order, String>{

}
