package com.saucedemo.runners;

import com.saucedemo.utils.SlackNotifier;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.saucedemo.steps"},
        plugin = {
                "pretty",
                "json:build/cucumber-reports/cucumber.json",
                "html:build/cucumber-reports/cucumber.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        tags = "@smoke or @regression"
)
public class TestRunner extends AbstractTestNGCucumberTests {
    private static long startTime;

    @BeforeClass
    public void beforeClass() {
        startTime = System.currentTimeMillis();
        SlackNotifier.sendMessage(":rocket: Starting test execution...");
    }

    @AfterClass
    public void afterClass(ITestContext context) {
        long duration = System.currentTimeMillis() - startTime;
        int totalTests = context.getAllTestMethods().length;
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();

        SlackNotifier.sendTestResults(totalTests, passed, failed, skipped, duration);
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
