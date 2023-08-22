package com.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.app.collections.Product;


public interface ProductRepository extends MongoRepository<Product, String> {
	@Query("{'prodName' : ?0}")
	public Optional<Product> findByProdName(String productName);
}
