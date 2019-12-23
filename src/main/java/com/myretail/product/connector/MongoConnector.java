package com.myretail.product.connector;

import com.myretail.product.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoConnector extends ReactiveMongoRepository<Product, Long> {

}
