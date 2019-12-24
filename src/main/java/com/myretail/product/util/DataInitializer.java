package com.myretail.product.util;

import com.myretail.product.connector.MongoConnector;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class DataInitializer {

    private final MongoConnector mongoConnector;

    public DataInitializer(MongoConnector mongoConnector) {
        this.mongoConnector = mongoConnector;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {

        Flux<Product> products = Flux.just(
                new Product(13860428L, new Price(20.85, "USD")),
                new Product(13860429L, new Price(12.57, "USD")),
                new Product(13860425L, new Price(6.97, "USD")),
                new Product(13860427L, new Price(19.32, "EURO")),
                new Product(13860421L, new Price(31.00, "INR")),
                new Product(13860422L, new Price(53.65, "USD")),
                new Product(13860423L, new Price(09.41, "EURO")),
                new Product(13860424L, new Price(78.12, "USD")),
                new Product(13860425L, new Price(76.72, "INR")),
                new Product(13860426L, new Price(12.32, "USD"))
        );

        Flux<Product> result = products.flatMap(res -> mongoConnector.save(res));

        mongoConnector.deleteAll()
                .thenMany(result)
                .thenMany(mongoConnector.findAll())
                .subscribe(a -> log.info(a.toString()));
    }
}
