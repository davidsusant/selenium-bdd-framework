package com.saucedemo.steps;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ConfigReader;
import com.saucedemo.utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.testng.Assert;

public class LoginSteps {
    private LoginPage loginPage;
    private ProductsPage productsPage;

    @Given("I am on the login page")
    @Step("Navigate to login page")
    public void i_am_on_the_login_page() {
        DriverManager.getDriver().get(ConfigReader.getProperty("base.url"));
        loginPage = new LoginPage(DriverManager.getDriver());
    }

    @When("I enter username {string} and password {string}")
    @Step("Enter credentials - Username: {0}, Password: {1}")
    public void i_enter_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    @Step("Click login button")
    public void i_click_the_login_button() {
        loginPage.clickLogin();
    }

    @Then("I should be redirected to the products page")
    @Step("Verify successful login")
    public void i_should_be_redirected_to_the_products_page() {
        productsPage = new ProductsPage(DriverManager.getDriver());
        Assert.assertTrue(productsPage.isPageLoaded(), "Products page is not loaded");
    }
}
