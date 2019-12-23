Feature: Retrieve product name for a given product id

  Scenario: Success: Get product name for an existing product id
    Given for a given product id
    When make a request to service
    Then application should receive a product name

  Scenario: Success: Application should be able retrieve price information for given product id
    Given Product id application
    When user consumes the application with product id
    Then response should return product response