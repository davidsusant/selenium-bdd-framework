package com.saucedemo.steps;

import com.saucedemo.utils.ConfigReader;
import com.saucedemo.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        DriverManager.initializedDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && Boolean.parseBoolean(ConfigReader.getProperty("screenshot.on.failure"))) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot on failure", new ByteArrayInputStream(screenshot));
            scenario.attach(screenshot, "image/png", "Screenshot");
        }

        DriverManager.closeDriver();
        System.out.println("Finished scenario: " + scenario.getName() + " - Status: " + scenario.getStatus());
    }
}
