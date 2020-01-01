package com.myretail.product.stepdef;

import com.myretail.product.Application;
import com.myretail.product.model.Price;
import com.myretail.product.model.ReturnDetails;
import com.myretail.product.model.request.ProductRequest;
import com.myretail.product.model.response.ProductUpdateResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UpdateProductPrice {

    private Long id;
    private ProductRequest request;
    private EntityExchangeResult result;

    ConfigurableApplicationContext applicationContext;
    int port;
    WebTestClient client;

    @Given("Product id and current price of the product")
    public void product_id_and_current_price_of_the_product() {
        id = 13860428L;
        request = new ProductRequest(id, Price.builder().value(56.64).currencyCode("USD").build());
    }

    @When("User updates the price for given product id")
    public void user_updates_the_price_for_given_product_id() {
        applicationContext = new SpringApplicationBuilder().sources(Application.class)
                .web(WebApplicationType.REACTIVE)
                .run("--server.port=0");

        port = applicationContext.getEnvironment().getProperty("local.server.port", Integer.class, 0);

        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();

        result = client.put()
                .uri("/api/v1/products/" + id)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody().returnResult();

    }

    @Then("application should be able to update the price of the product")
    public void application_should_be_able_to_update_the_price_of_the_product() {

        Mono<ProductUpdateResponse> expectedResponse = Mono.just(ProductUpdateResponse.builder()
                .id(id)
                .returnDetails(ReturnDetails.builder()
                        .code(200)
                        .source("retail-product-api")
                        .message("Success")
                        .build())
                .build());

        assertTrue(result.getStatus().is2xxSuccessful());

        assertEquals(200, expectedResponse.block().getReturnDetails().getCode().intValue());

    }

    @Given("Product id and invalid current code of the product")
    public void product_id_and_invalid_current_code_of_the_product() {
        id = 13860428L;
        request = new ProductRequest(id, Price.builder().value(56.64).currencyCode("any").build());
    }

    @When("user tries to update the price of the product")
    public void user_tries_to_update_the_price_of_the_product() {
        applicationContext = new SpringApplicationBuilder().sources(Application.class)
                .web(WebApplicationType.REACTIVE)
                .run("--server.port=0");

        port = applicationContext.getEnvironment().getProperty("local.server.port", Integer.class, 0);

        client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();

        result = client.put()
                .uri("/api/v1/products/" + id)
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody().returnResult();

    }

    @Then("application should not allow user to update the price")
    public void application_should_not_allow_user_to_update_the_price() {

        assertTrue(result.getStatus().is4xxClientError());
    }
}
