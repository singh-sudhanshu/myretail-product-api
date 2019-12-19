package com.myretail.product.stepdef;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.jupiter.api.BeforeEach;

public class GetProductName {

    private String id;

    @BeforeEach
    void setUp() {
        id = "12345678";
    }

    @Given("^for a given product id$")
    public void for_a_given_product_id() {


    }

    @When("^make a request to service$")
    public void make_a_request_to_service() {

    }

    @Then("^application should receive a prduct name$")
    public void application_should_receive_a_prduct_name() {

    }
}
