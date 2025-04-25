package org.planit.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v126.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactPage extends BasePage {

    @FindBy(how = How.ID, using="forename")
    private WebElement forename;

    @FindBy(how = How.ID, using="forename-err")
    private WebElement forenameError;

    @FindBy(how = How.ID, using="surname")
    private WebElement surname;

    @FindBy(how = How.ID, using="email")
    private WebElement email;

    @FindBy(how = How.ID, using="email-err")
    private WebElement emailError;

    @FindBy(how = How.ID, using="telephone")
    private WebElement telephone;

    @FindBy(how = How.ID, using="message")
    private WebElement message;

    @FindBy(how = How.XPATH, using="//a[normalize-space()='Submit']")
    private WebElement submit;

    @FindBy(how = How.ID, using="message-err")
    private WebElement messageError;

    @FindBy(how = How.XPATH, using="//strong[starts-with(text(),'Thanks')]")
    private WebElement successMsg;

    private final int timeoutForSuccessMsg = 15;

    public ContactPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(submit));
    }

    public void enterForename(String name) {
        forename.sendKeys(name);
    }

    public void enterSurname(String name) {
        surname.sendKeys(name);
    }

    public void enterEmail(String em) {
        email.sendKeys(em);
    }

    public void enterTelephone(String tphone) {
        telephone.sendKeys(tphone);
    }

    public void enterMessage(String msg) {
        message.sendKeys(msg);
    }

    public void clickSubmit() {
        submit.click();
    }

    public boolean verifyErrorDisplayed(String element) {
        try {
            switch (element) {
                case "forename":
                    return forenameError.isDisplayed();
                case "email" :
                    return emailError.isDisplayed();
                case "message" :
                    return messageError.isDisplayed();
                default :
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifySuccessMsgDisplayed(String name) {
        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(timeoutForSuccessMsg))
                    .until(ExpectedConditions.textToBePresentInElement(successMsg, name));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
