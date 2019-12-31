package com.myretail.product.adaptor;

import com.myretail.product.connector.MongoConnector;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class MongoAdaptorImplTest {

    private MongoAdaptorImpl mongoAdaptorImpl;

    @Mock
    private MongoConnector mongoConnector;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mongoAdaptorImpl = new MongoAdaptorImpl(mongoConnector);
    }

    @Test
    void product_price_test() {

        Product product = Product.builder()
                .id(123456L)
                .currentPrice(Price.builder()
                        .value(12.32)
                        .currencyCode("USD")
                        .build())
                .build();

        when(mongoConnector.findById(anyLong())).thenReturn(Mono.just(product));

        Mono<Price> price = mongoAdaptorImpl.productPrice(123456L);

        assertEquals(product.getCurrentPrice().getValue(), price.block().getValue());
        assertEquals(product.getCurrentPrice().getCurrencyCode(), price.block().getCurrencyCode());
        verify(mongoConnector, times(1)).findById(123456L);
    }

    @Test
    public void update_price_test() {
        Product product = Product.builder()
                .id(123456L)
                .currentPrice(Price.builder()
                        .value(12.32)
                        .currencyCode("USD")
                        .build())
                .build();

        when(mongoConnector.save(product)).thenReturn(Mono.just(product));
        when(mongoConnector.findById(product.getId())).thenReturn(Mono.just(product));

        Mono<Product> actualResponse = mongoAdaptorImpl.updatePrice(product, 123456L);
        assertTrue(actualResponse.block().getCurrentPrice() != null);

        verify(mongoConnector, times(1)).findById(product.getId());
        verify(mongoConnector, times(1)).save(product);
    }
}