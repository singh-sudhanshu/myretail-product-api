package com.myretail.product.adaptor;

import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import reactor.core.publisher.Mono;

public interface MongoAdaptor  {

    Mono<Price> productPrice(Long id);
    Mono<Product> updatePrice(Product product, Long id);
}
