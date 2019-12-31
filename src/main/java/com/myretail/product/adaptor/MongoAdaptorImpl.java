package com.myretail.product.adaptor;

import com.myretail.product.connector.MongoConnector;
import com.myretail.product.exception.RecordNotFoundException;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MongoAdaptorImpl implements MongoAdaptor {

    private final MongoConnector mongoConnector;

    public MongoAdaptorImpl(MongoConnector mongoConnector) {
        this.mongoConnector = mongoConnector;
    }

    public Mono<Price> productPrice(Long id) {

        Mono<Product> product = mongoConnector.findById(id);

        return product.map(p -> Price.builder()
                .value(p.getCurrentPrice().getValue())
                .currencyCode(p.getCurrentPrice().getCurrencyCode())
                .build());
    }

    @Override
    public Mono<Product> updatePrice(Product product, Long id) {

        return mongoConnector.findById(id)
                .switchIfEmpty(Mono
                        .error(new RecordNotFoundException("No record found to update product with id: " + id)))
                .doOnSuccess(findProduct -> {
                    findProduct.setCurrentPrice(product.getCurrentPrice());
                    mongoConnector.save(product).subscribe();
                });
    }
}
