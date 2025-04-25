package org.planit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ShoppingPage extends BasePage {

    @FindBy(how = How.XPATH, using = "//ul/li[@ng-repeat='item in catalog']")
    private List<WebElement> shoppingItems;

    @FindBy(how = How.XPATH, using = "//a[text()='Buy']")
    private WebElement buyBtn;

    @FindBy(how = How.XPATH, using = "//a[contains(text(), 'Cart')]")
    private WebElement cartBtn;

    public ShoppingPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
        new WebDriverWait(webDriver, Duration.ofSeconds(pageTimeout))
                .until(ExpectedConditions.elementToBeClickable(buyBtn));
        logger.info("Shopping Page is displayed");
    }

    public void clickCartButton() {
        cartBtn.click();
    }

    public String getItemPrice(String name) {
        for (WebElement shoppingItem : shoppingItems) {
            try {
                WebElement item = shoppingItem.findElement(By.xpath("./div/h4[text()='" + name + "']"));
                return item.findElement(By.xpath("parent::div/p/child::span")).getText();
            } catch (Exception e) { }
        }
        return null;
    }

    //Method returns price of the item and clicks on buy
    public void buyItem(String name) {
        for (WebElement shoppingItem : shoppingItems) {
            try {
                WebElement item = shoppingItem.findElement(By.xpath("./div/h4[text()='" + name + "']"));
                item.findElement(By.xpath("parent::div/p/child::a[normalize-space()='Buy']")).click();
            } catch (Exception e) { }
        }
    }
}
