package org.planit.util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CucumberUtils {
    static WebDriver driver;

    public static WebDriver getWebDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        return driver;
    }
}
