package com.myretail.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.product.Application;
import com.myretail.product.model.Price;
import com.myretail.product.model.ReturnDetails;
import com.myretail.product.model.redsky.RedSkyResponse;
import com.myretail.product.model.response.ProductResponse;
import com.myretail.product.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.myretail.product.test.ReadFixture.readFixture;
import static org.mockito.ArgumentMatchers.anyLong;

@ActiveProfiles("ci")
class ProductControllerTest {

    private ProductController productController;
    private WebTestClient client;
    private ObjectMapper mapper = new ObjectMapper();

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        productController = new ProductController(productService);
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder().sources(Application.class)
                .web(WebApplicationType.REACTIVE)
                .run("--server.port=0");

        int port = applicationContext.getEnvironment().getProperty("local.server.port", Integer.class, 0);

        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    void getProduct_test() {

        client.get()
                .uri("api/v1/products/" + 13860428L)
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

        RedSkyResponse redSkyResponse = mapper.readValue(readFixture("valid-product.json"), RedSkyResponse.class);

        Mono<ProductResponse> expectedResponse = Mono.just(ProductResponse.builder()
                .id(Long.valueOf(13860428))
                .name(redSkyResponse.getProductItem().getItem().getProductDescription().getTitle())
                .currentPrice(Price.builder()
                        .value(34.34)
                        .currencyCode("USD")
                        .build())
                .returnDetails(ReturnDetails.builder()
                        .code(200)
                        .source("retail-product-api")
                        .message("Success")
                        .build())
                .build());

        Mockito.when(productService.service(anyLong())).thenReturn(expectedResponse);

        client.get()
                .uri("api/v1/products/" + Long.valueOf(13860428))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(expectedResponse.block().getId())
                .jsonPath("$.name").isEqualTo(expectedResponse.block().getName())
                .jsonPath("$.current_price").isEqualTo(expectedResponse.block().getCurrentPrice())
                .jsonPath("$.returnDetails.code").isEqualTo(expectedResponse.block().getReturnDetails().getCode())
                .jsonPath("$.returnDetails.message").isEqualTo(expectedResponse.block().getReturnDetails().getMessage())
                .jsonPath("$.returnDetails.source").isEqualTo(expectedResponse.block().getReturnDetails().getSource());

        Mockito.verify(productService, Mockito.times(1)).service(anyLong());
    }
}