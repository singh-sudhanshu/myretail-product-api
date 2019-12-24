Feature: User should be able to update the price

  Scenario: Update price for given product
    Given Product id and current price of the product
    When User updates the price for given product id
    Then application should be able to update the price of the product