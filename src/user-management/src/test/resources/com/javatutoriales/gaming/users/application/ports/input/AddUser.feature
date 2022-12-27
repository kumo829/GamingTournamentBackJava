@CreateAccount
Feature: Can I create a new user account?
  Scenario: Adding a valid user
    Given I have a new valid using account
    Then I save the new account
