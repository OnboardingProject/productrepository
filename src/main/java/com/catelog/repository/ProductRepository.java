package com.catelog.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.catelog.entity.Product;

/**
 * This is the repository class for product
 *
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	@Query("{productName:{$regex:?0}}")
	List<Product> getProductByName(String productName);

}
