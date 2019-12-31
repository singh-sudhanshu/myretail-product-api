package com.myretail.product.service;

import com.myretail.product.adaptor.MongoAdaptorImpl;
import com.myretail.product.adaptor.RedSkyAdaptor;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import com.myretail.product.model.ReturnDetails;
import com.myretail.product.model.redsky.Item;
import com.myretail.product.model.redsky.ProductDescription;
import com.myretail.product.model.redsky.ProductItem;
import com.myretail.product.model.redsky.RedSkyResponse;
import com.myretail.product.model.response.ProductResponse;
import com.myretail.product.model.response.ProductUpdateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductService productService;

    @Mock
    private RedSkyAdaptor redSkyAdaptor;

    @Mock
    private MongoAdaptorImpl mongoAdaptorImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(redSkyAdaptor, mongoAdaptorImpl);
    }

    @Test
    void service() {

        Mono<RedSkyResponse> product = Mono.just(RedSkyResponse.builder()
                .productItem(ProductItem.builder()
                        .item(Item.builder()
                                .productDescription(ProductDescription.builder()
                                        .title("My Product")
                                        .build())
                                .build())
                        .build())
                .build());

        Price price = Price.builder().value(34.34).currencyCode("USD").build();

        Mono<ProductResponse> expectedResponse = Mono.just(ProductResponse.builder()
                .id(Long.valueOf(13860428))
                .name(product.block().getProductItem().getItem().getProductDescription().getTitle())
                .currentPrice(price)
                .returnDetails(ReturnDetails.builder()
                        .code(200)
                        .source("retail-product-api")
                        .message("Success")
                        .build())
                .build());


        when(redSkyAdaptor.getProduct(anyLong())).thenReturn(product);
        when(mongoAdaptorImpl.productPrice(anyLong())).thenReturn(Mono.just(price));

        Mono<ProductResponse> actualResponse = productService.service(123456L);

        assertEquals(expectedResponse.block().getName(), actualResponse.block().getName());
        assertEquals(expectedResponse.block().getCurrentPrice(), actualResponse.block().getCurrentPrice());
        verify(redSkyAdaptor, times(1)).getProduct(anyLong());
        verify(mongoAdaptorImpl, times(1)).productPrice(anyLong());
    }

    @Test
    public void update_price_test() {

        Price price = Price.builder().value(34.34).currencyCode("USD").build();

        Mono<ProductUpdateResponse> expectedResponse = Mono.just(ProductUpdateResponse.builder()
                .id(123456L)
                .returnDetails(ReturnDetails.builder()
                        .code(200)
                        .source("retail-product-api")
                        .message("Success")
                        .build())
                .build());

        when(mongoAdaptorImpl.updatePrice(any(), anyLong())).thenReturn(Mono.just(Product.builder()
                .id(123456L)
                .currentPrice(price)
                .build()));

        Mono<ProductUpdateResponse> actualResponse = productService.updatePrice(price, 123456L);

        assertEquals(expectedResponse.block().getReturnDetails().getCode(), actualResponse.block().getReturnDetails().getCode());
        verify(mongoAdaptorImpl, times(1)).updatePrice(any(), anyLong());
    }
}