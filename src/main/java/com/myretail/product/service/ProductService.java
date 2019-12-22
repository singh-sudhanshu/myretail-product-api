package com.myretail.product.service;

import com.myretail.product.adaptor.RedSkyAdaptor;
import com.myretail.product.model.response.ProductResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final RedSkyAdaptor redSkyAdaptor;

    public ProductService(RedSkyAdaptor redSkyAdaptor) {
        this.redSkyAdaptor = redSkyAdaptor;
    }

    public Mono<ProductResponse> service(Long id) {

        return redSkyAdaptor.getProduct(id).map(p -> ProductResponse.builder()
                .id(1234L)
                .name(p.getProductItem().getItem().getProductDescription().getTitle())
                .currentPrice(null)
                .returnDetails(null)
                .build());
    }
}
