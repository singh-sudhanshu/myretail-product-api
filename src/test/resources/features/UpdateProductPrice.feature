Feature: User should be able to update the price

  Scenario: Success: Update price for given product
    Given Product id and current price of the product
    When User updates the price for given product id
    Then application should be able to update the price of the product

  Scenario: Failure: Update price for invalid currency code
    Given Product id and invalid current code of the product
    When user tries to update the price of the product
    Then application should not allow user to update the price