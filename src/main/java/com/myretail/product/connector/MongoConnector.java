package com.myretail.product.connector;

import com.myretail.product.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MongoConnector extends ReactiveCrudRepository<Product, Long> {

}
