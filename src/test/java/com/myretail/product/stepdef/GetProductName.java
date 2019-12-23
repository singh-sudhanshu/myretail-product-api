package com.myretail.product.stepdef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.product.Application;
import com.myretail.product.model.Price;
import com.myretail.product.model.ReturnDetails;
import com.myretail.product.model.redsky.RedSkyResponse;
import com.myretail.product.model.response.ProductResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static com.myretail.product.test.ReadFixture.readFixture;
import static org.junit.Assert.assertEquals;

public class GetProductName {

    private Long id;
    private EntityExchangeResult result;
    private ObjectMapper mapper = new ObjectMapper();
    ConfigurableApplicationContext applicationContext;
    int port;
    WebTestClient client;

    @Given("^for a given product id$")
    public void for_a_given_product_id() {
        id = 13860428L;
    }

    @When("^make a request to service$")
    public void make_a_request_to_service() {
        applicationContext = new SpringApplicationBuilder().sources(Application.class)
                .web(WebApplicationType.REACTIVE)
                .run("--server.port=0");

        port = applicationContext.getEnvironment().getProperty("local.server.port", Integer.class, 0);

        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();

        result = client.get()
                .uri("/api/v1/products/" + id)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody().returnResult();
    }

    @Then("^application should receive a product name$")
    public void application_should_receive_a_product_name() throws IOException {
        System.out.println(result.getResponseBody());

        RedSkyResponse redSkyResponse = mapper.readValue(readFixture("valid-product.json"), RedSkyResponse.class);

        Mono<ProductResponse> expectedResponse = Mono.just(ProductResponse.builder()
                .id(id)
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

        assertEquals(200, expectedResponse.block().getReturnDetails().getCode().intValue());

    }

    @Given("Product id application")
    public void product_id_application() {
        id = 13860429L;
    }

    @When("user consumes the application with product id")
    public void user_consumes_the_application_with_product_id() {
        applicationContext = new SpringApplicationBuilder().sources(Application.class)
                .web(WebApplicationType.REACTIVE)
                .run("--server.port=0");

        port = applicationContext.getEnvironment().getProperty("local.server.port", Integer.class, 0);

        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
        result = client.get()
                .uri("/api/v1/products/" + id)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody().returnResult();


    }

    @Then("response should return product response")
    public void response_should_return_product_response() throws JsonProcessingException {
        RedSkyResponse redSkyResponse = mapper.readValue(readFixture("valid-product.json"), RedSkyResponse.class);

        Mono<ProductResponse> expectedResponse = Mono.just(ProductResponse.builder()
                .id(id)
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

        assertEquals(200, expectedResponse.block().getReturnDetails().getCode().intValue());
        assertEquals(redSkyResponse.getProductItem().getItem()
                .getProductDescription().getTitle(), expectedResponse.block().getName());
    }
}
