# File Name: checkout.feature
# Copyright 2015, Opex Software
# Apache License, Version 2.0
# This file contains cucumber steps for login and registration related scenarios


Feature: Verifying the Login and registration functionality

  Background:
    Given I am on magento customer page


  Scenario: Login using valid and invalid users
    Given I follow login link in account section
    When I login with username "admin@mailinator.com" and password "adminadmin"
    Then I should be on "My Dashboard" page
    When I logout
    Then I should be on "Logout" page
    And I am on magento customer page
    And I follow login link in account section
    When I login with username "invalid@email.com" and password "fake_password"
    Then I should see "invalid_credentials" message for login
    Given I am on magento customer page
    When I follow login link in account section
    And I login with username "" and password ""
    Then I should see "mandatory_fields" message for login

  Scenario: Registration validation messages verification
    When I follow register link in account section
    And I click the register button
    Then I should see "mandatory_fields" message for registration
    Given I am on magento customer page
    When I follow register link in account section
    And I register an existing user
    And I click the register button
    Then I should see "existing_user" message for registration

