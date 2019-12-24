package com.myretail.product.integration;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/feature"},
        plugin = {"pretty"},
        glue={"src/test/com.myretail.product.stepdef"},
        monochrome = true
)
public class IntegrationTest {
}
