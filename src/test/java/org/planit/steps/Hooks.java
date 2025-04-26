package org.planit.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.planit.util.CucumberUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Hooks {
    public static WebDriver driver;
    Properties properties;
    final String propertyFileLocation = "src/test/resources/test.properties";

    @Before
    public void beforeScenario() throws IOException {
        properties = new Properties();
        FileReader reader = new FileReader(propertyFileLocation);
        properties.load(reader);
        driver = CucumberUtils.getWebDriver();
        driver.navigate().to(properties.getProperty("application.url"));
    }

    @After
    public void afterScenario(){
        driver.quit();
    }
}