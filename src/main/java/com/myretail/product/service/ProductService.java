package com.myretail.product.service;

import com.myretail.product.adaptor.MongoAdaptor;
import com.myretail.product.adaptor.RedSkyAdaptor;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import com.myretail.product.model.ReturnDetails;
import com.myretail.product.model.redsky.RedSkyResponse;
import com.myretail.product.model.response.ProductResponse;
import com.myretail.product.model.response.ProductUpdateResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class ProductService {

    private final RedSkyAdaptor redSkyAdaptor;
    private final MongoAdaptor mongoAdaptor;

    public ProductService(RedSkyAdaptor redSkyAdaptor, MongoAdaptor mongoAdaptor) {
        this.redSkyAdaptor = redSkyAdaptor;
        this.mongoAdaptor = mongoAdaptor;
    }

    public Mono<ProductResponse> service(Long id) {

        Mono<RedSkyResponse> product = redSkyAdaptor.getProduct(id);
        Mono<Price> price = mongoAdaptor.productPrice(id);

        Mono<Tuple2<RedSkyResponse, Price>> zip = product.zipWith(price);

        return zip.map(p -> ProductResponse.builder()
                .id(id)
                .name(p.getT1().getProductItem().getItem().getProductDescription().getTitle())
                .currentPrice(p.getT2())
                .returnDetails(ReturnDetails.builder()
                        .code(200)
                        .message("Success")
                        .source("retail-product-api")
                        .build())
                .build());
    }

    public Mono<ProductUpdateResponse> updatePrice(Price price, Long id) {
        Mono<Product> updatedProduct = mongoAdaptor.updatePrice(price, id);

        return updatedProduct.map(p -> ProductUpdateResponse.builder()
                .id(p.getId())
                .returnDetails(ReturnDetails.builder()
                        .code(200)
                        .message("Success")
                        .source("retail-product-api")
                        .build())
                .build());
    }
}
