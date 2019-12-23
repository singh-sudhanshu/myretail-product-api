package com.myretail.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.product.model.Price;
import com.myretail.product.model.ReturnDetails;
import com.myretail.product.model.response.ProductResponse;
import com.myretail.product.service.ProductService;
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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private WebTestClient client;
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private ProductService productService;

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
    public void api_response_test() throws JsonProcessingException {

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
}