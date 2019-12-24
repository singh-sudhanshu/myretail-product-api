package com.myretail.product.adaptor;

import com.myretail.product.connector.RedSkyConnector;
import com.myretail.product.model.redsky.RedSkyResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RedSkyAdaptor {

    private final RedSkyConnector redSkyConnector;

    public RedSkyAdaptor(RedSkyConnector redSkyConnector) {
        this.redSkyConnector = redSkyConnector;
    }

    public Mono<RedSkyResponse> getProduct(Long id) {
        return redSkyConnector.getProductById(id);
    }
}
