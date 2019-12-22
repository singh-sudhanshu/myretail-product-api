package com.myretail.product.adaptor;

import com.myretail.product.connector.RedSkyConnector;
import com.myretail.product.model.redsky.Item;
import com.myretail.product.model.redsky.ProductDescription;
import com.myretail.product.model.redsky.ProductItem;
import com.myretail.product.model.redsky.RedSkyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RedSkyAdaptorTest {

    private RedSkyAdaptor redSkyAdaptor;

    @Mock
    private RedSkyConnector redSkyConnector;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        redSkyAdaptor = new RedSkyAdaptor(redSkyConnector);
    }

    @Test
    void getProduct_test() {

        Mono<RedSkyResponse> product = Mono.just(RedSkyResponse.builder()
                .productItem(ProductItem.builder()
                        .item(Item.builder()
                                .productDescription(ProductDescription.builder()
                                        .title("My Product")
                                        .build())
                                .build())
                        .build())
                .build());

        when(redSkyConnector.getProductById(anyLong())).thenReturn(product);

        Mono<RedSkyResponse> actualResponse = redSkyAdaptor.getProduct(123456L);

        assertEquals(product.block().getProductItem(), actualResponse.block().getProductItem());
        verify(redSkyConnector, times(1)).getProductById(anyLong());
    }
}