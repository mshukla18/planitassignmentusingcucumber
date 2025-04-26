package org.planit.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.planit.pages.CartPage;
import org.planit.pages.ContactPage;
import org.planit.pages.HomePage;
import org.planit.pages.ShoppingPage;
import org.planit.util.ScenarioContext;

import java.util.HashMap;
import java.util.Map;

public class JupiterSteps {
    private WebDriver driver = Hooks.driver;
    private static Logger logger = LogManager.getLogger(JupiterSteps.class);
    private ScenarioContext scenarioContext;

    public JupiterSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @When("I navigate to page {string} from home page")
    public void navigateToPage(String pageName) {
        HomePage homePage = new HomePage(driver);
        if (pageName.equalsIgnoreCase("contact")) {
            homePage.clickContactLink();
        } else if (pageName.equalsIgnoreCase("shopping")) {
            homePage.clickStartShoppingBtn();
        } else {
            Assert.fail("invalid page name");
        }
    }

    @And("^I click on submit button$")
    public void clickSubmit() {
        ContactPage contactPage = new ContactPage(driver);
        contactPage.clickSubmit();
    }

    @And("^I validate all the error messages$")
    public void validateAllErrors() {
        ContactPage contactPage = new ContactPage(driver);
        Assert.assertEquals("forename error is not displayed",
                contactPage.verifyErrorDisplayed("forename"),
                true);
        logger.info("forename error is displayed");
        Assert.assertEquals("email error is not displayed",
                contactPage.verifyErrorDisplayed("email"),
                true);
        logger.info("Email error is displayed");
        Assert.assertEquals("message error is not displayed",
                contactPage.verifyErrorDisplayed("message"),
                true);
        logger.info("Message error is displayed");
    }

    @And("in the field {string} enter {string} and validate that error is gone")
    public void enterValueAndValidate(String field, String value) {
        ContactPage contactPage = new ContactPage(driver);
        if (field.equalsIgnoreCase("forename")) {
            contactPage.enterForename(value);
            Assert.assertEquals("forename error was not supposed to be displayed",
                    contactPage.verifyErrorDisplayed("forename"),
                    false);
            logger.info("forename error is not displayed");

        } else if (field.equalsIgnoreCase("email")) {
            contactPage.enterEmail(value);
            Assert.assertEquals("email error was not supposed to be displayed",
                    contactPage.verifyErrorDisplayed("email"),
                    false);
            logger.info("Email error is not displayed");

        } else if (field.equalsIgnoreCase("message")) {
            contactPage.enterMessage(value);
            Assert.assertEquals("message error was not supposed to be displayed",
                    contactPage.verifyErrorDisplayed("message"),
                    false);
            logger.info("Message error is not displayed");

        } else {
            Assert.fail("Invalid field name");
        }
    }

    @And("in the field {string}, {string} and {string} enter respective values")
    public void enterValuesInField(String forename, String email, String message) {
        ContactPage contactPage = new ContactPage(driver);
           contactPage.enterForename(forename);
            scenarioContext.setData("name", forename);
            contactPage.enterEmail(email);
            scenarioContext.setData("email", email);
            contactPage.enterMessage(message);
            scenarioContext.setData("message", message);
    }

    @And("validate success message")
    public void validateSuccessMsg() {
        ContactPage contactPage = new ContactPage(driver);
        Assert.assertEquals("Success message is not displayed",
                contactPage.verifySuccessMsgDisplayed(scenarioContext.getData("name").toString()),
                true);
    }

    @And("add {int} {string} to the cart")
    public void addItemsToCart(int count, String item) {
        ShoppingPage shoppingPage = new ShoppingPage(driver);
        if (scenarioContext.getData("itemPrice") != null) {
            HashMap<String, String> scenarioItemPrice = ((HashMap<String, String>)scenarioContext
                    .getData("itemPrice"));
            scenarioItemPrice.put(item, shoppingPage.getItemPrice(item));
        } else {
            Map<String, String> itemPrice = new HashMap<>();
            itemPrice.put(item, shoppingPage.getItemPrice(item));
            scenarioContext.setData("itemPrice", itemPrice);
        }
        if (scenarioContext.getData("itemQuantity") != null) {
            HashMap<String, Integer> scenarioItemQuantity = ((HashMap<String, Integer>)scenarioContext
                    .getData("itemQuantity"));
            scenarioItemQuantity.put(item, count);
        } else {
            Map<String, Integer> itemQuantity = new HashMap<>();
            itemQuantity.put(item, count);
            scenarioContext.setData("itemQuantity", itemQuantity);
        }
        for (int i = 0; i < count; i++) {
            shoppingPage.buyItem(item);
        }
    }

    @And("navigate to {string} page from shopping page")
    public void navigateToPageFromShoppingPage(String pageName) {
        ShoppingPage page = new ShoppingPage(driver);
        if (pageName.equalsIgnoreCase("cart")) {
            page.clickCartButton();
        } else {
            Assert.fail("invalid page name");
        }
    }

    @And("validate the price, quantity and subtotal of each item on the cart page")
    public void validateAllItemsDetailsOnCartPage() {
        CartPage cartPage = new CartPage(driver);
        HashMap<String, String> scenarioItemPrice = ((HashMap<String, String>)scenarioContext
                .getData("itemPrice"));
        HashMap<String, Integer> scenarioItemQuantity = ((HashMap<String, Integer>)scenarioContext
                .getData("itemQuantity"));
        scenarioItemQuantity.forEach((k,v) -> {
            HashMap<String, String> cartItem = cartPage.getItemDetails(k);
            Assert.assertEquals("Price of item" + k + "not equal on cart page",
                    cartItem.get("itemPrice"), scenarioItemPrice.get(k)
                    );
            logger.info("Price of item " + k + " is correct");
            Assert.assertEquals("Quantity of item " + k + " does not match the test data",
                    cartItem.get("itemQuantity"), scenarioItemQuantity.get(k).toString());
            logger.info("Quantity of item " + k + " is correct");
            Double calculatedSubTotal = Double.parseDouble(cartItem.get("itemPrice").substring(1))
                    * scenarioItemQuantity.get(k);
            Assert.assertEquals("Subtotal of item " + " does not match",
                    cartItem.get("itemSubTotal").substring(1), calculatedSubTotal.toString());
            logger.info("Subtotal of item " + k + " is correct");
        });
    }
}
