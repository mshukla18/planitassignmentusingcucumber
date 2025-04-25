package org.planit.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    @FindBy(how= How.LINK_TEXT, using="Contact")
    private WebElement contact;

    @FindBy(how = How.XPATH, using="//a[normalize-space()='Start Shopping »']")
    private WebElement startShoppingBtn;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
        new WebDriverWait(webDriver, Duration.ofSeconds(pageTimeout))
                .until(ExpectedConditions.elementToBeClickable(startShoppingBtn));
        logger.info("Home Page is displayed");
    }

    public void clickStartShoppingBtn() {
        startShoppingBtn.click();
    }

    public void clickContactLink() {contact.click();}
}
