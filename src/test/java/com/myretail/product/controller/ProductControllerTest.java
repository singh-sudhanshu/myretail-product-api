package com.myretail.product.controller;

import com.myretail.product.model.Price;
import com.myretail.product.model.ReturnDetails;
import com.myretail.product.model.response.ProductResponse;
import com.myretail.product.model.response.ProductUpdateResponse;
import com.myretail.product.service.ProductService;
import com.myretail.product.util.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ProductService productService;

    @MockBean
    private Validator validator;

    @Test
    void getProduct_test() {

        ProductResponse product = ProductResponse.builder()
                .id(13860428L)
                .name("mock product name")
                .currentPrice(Price.builder()
                        .value(34.34)
                        .currencyCode("USD")
                        .build())
                .returnDetails(ReturnDetails.builder()
                        .message("mock Success")
                        .code(200)
                        .source("mock service")
                        .build())
                .build();
        when(productService.service(13860428L)).thenReturn(Mono.just(product));
        doNothing().when(validator).validate(any(), anyLong());

        client.get()
                .uri("http://localhost:8080/api/v1/products/" + 13860428L)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()

                .consumeWith(response -> {
                    Assertions.assertThat(response.getResponseBody()).isNotNull();
                    Assertions.assertThat(response.getResponseBody()).isNotEmpty();
                });
    }

    @Test
    public void api_get_response_test() {

        ProductResponse product = ProductResponse.builder()
                .id(13860428L)
                .name("mock product name")
                .currentPrice(Price.builder()
                        .value(34.34)
                        .currencyCode("USD")
                        .build())
                .returnDetails(ReturnDetails.builder()
                        .message("mock Success")
                        .code(200)
                        .source("mock service")
                        .build())
                .build();
        when(productService.service(13860428L)).thenReturn(Mono.just(product));

        client.get()
                .uri("http://localhost:8080/api/v1/products/" + Long.valueOf(13860428))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(product.getId())
                .jsonPath("$.name").isEqualTo(product.getName())
                // .jsonPath("$.current_price").isEqualTo(product.getCurrentPrice())
                .jsonPath("$.returnDetails.code").isEqualTo(product.getReturnDetails().getCode())
                .jsonPath("$.returnDetails.message").isEqualTo(product.getReturnDetails().getMessage())
                .jsonPath("$.returnDetails.source").isEqualTo(product.getReturnDetails().getSource());

        Mockito.verify(productService, Mockito.times(1)).service(anyLong());
    }

    @Test
    public void api_put_test() {

        Price price = Price.builder()
                .value(59.99)
                .currencyCode("USD")
                .build();

        ProductUpdateResponse response = ProductUpdateResponse.builder()
                .id(13860428L)
                .returnDetails(ReturnDetails.builder()
                        .code(200)
                        .source("mocked api")
                        .message("mocked success")
                        .build())
                .build();

        when(productService.updatePrice(any(), anyLong())).thenReturn(Mono.just(response));

        client
                .put()
                .uri("http://localhost:8080/api/v1/products/" + Long.valueOf(13860428))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(price)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    Assertions.assertThat(res.getResponseBody()).isNotNull();
                    Assertions.assertThat(res.getResponseBody()).isNotEmpty();
                });

        verify(productService, times(1)).updatePrice(any(), anyLong());
    }
}