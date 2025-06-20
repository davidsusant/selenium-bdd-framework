@smoke @login
  Feature: Login functionality
    As a user
    I want to be able to login to the application
    So that I can access the products

    Background:
      Given I am on the login page

      @positive
        Scenario: Successful login with valid credentials
          When I enter username "standard_user" and password "secret_sauce"
          And I click the login button
          Then I should be redirected to the products page