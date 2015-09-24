# File Name: checkout.feature
# Copyright 2015, Opex Software
# Apache License, Version 2.0
# This file contains cucumber steps for checkout related scenarios


Feature: Add product to cart and checkout using different payment methods

Background:
    Given I am on magento customer page
    
Scenario: Product Checkout as a GUEST with a CREDIT CARD payment
	When I do a global search using "Samsung" keyword
	Then I should see products
    When I add to cart the product based on RUN_INDEX
    Then I proceed to checkout
    When I select checkout method as Guest
    And I fill all mandatory details in Billing Information as guest
    And I continue with shipping method
    And I select payment method as "credit_card"
    And I place the order
    Then I should see order confirmation message
    
Scenario: Product Checkout as a REGISTERED USER with CREDIT CARD payment
	Given I follow register link in account section
    And I register a new user
    When I click the register button
    Then I should see "new_user" message for registration
    When I do a global search using "Samsung" keyword
    Then I should see products
    When I add to cart the product based on RUN_INDEX
    Then I proceed to checkout
    And I fill all mandatory details in Billing Information as member
    And I continue with shipping method
    And I select payment method as "credit_card"
    And I place the order
    Then I should see order confirmation message
    
Scenario: Product Checkout as a GUEST with CASH ON DELIVERY payment
    When I do a global search using "Samsung" keyword
    Then I should see products
    When I add to cart the product based on RUN_INDEX
    Then I proceed to checkout
    When I select checkout method as Guest
    And I fill all mandatory details in Billing Information as guest
    And I continue with shipping method
    And I select payment method as "cash_on_delivery"
    And I place the order
    Then I should see order confirmation message
    
Scenario: Product Checkout as a REGISTERED USER with CASH ON DELIVERY payment
    Given I follow register link in account section
    And I register a new user
    When I click the register button
    Then I should see "new_user" message for registration
    When I do a global search using "Samsung" keyword
    Then I should see products
    When I add to cart the product based on RUN_INDEX
    Then I proceed to checkout
    And I fill all mandatory details in Billing Information as member
    And I continue with shipping method
    And I select payment method as "cash_on_delivery"
    And I place the order
    Then I should see order confirmation message
    
Scenario: Product Checkout as a GUEST with CHEQUE payment
    When I do a global search using "Samsung" keyword
    Then I should see products
    When I add to cart the product based on RUN_INDEX
    Then I proceed to checkout
    When I select checkout method as Guest
    And I fill all mandatory details in Billing Information as guest
    And I continue with shipping method
    And I select payment method as "cheque"
    And I place the order
    Then I should see order confirmation message
    
Scenario: Product Checkout as a REGISTERED USER with CHEQUE payment
    Given I follow register link in account section
    And I register a new user
    When I click the register button
    Then I should see "new_user" message for registration
    When I do a global search using "Samsung" keyword
    Then I should see products
    When I add to cart the product based on RUN_INDEX
    Then I proceed to checkout
    And I fill all mandatory details in Billing Information as member
    And I continue with shipping method
    And I select payment method as "cheque"
    And I place the order
    Then I should see order confirmation message            