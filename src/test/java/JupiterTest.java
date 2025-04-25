import io.github.bonigarcia.wdm.WebDriverManager;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.planit.pages.CartPage;
import org.planit.pages.ContactPage;
import org.planit.pages.HomePage;
import org.planit.pages.ShoppingPage;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.lang.Double.parseDouble;

public class JupiterTest {
    WebDriver webDriver;
    Properties properties;
    final String propertyFileLocation = "src/test/resources/test.properties";

    @BeforeClass
    void setup() throws IOException {
        properties = new Properties();
        FileReader reader = new FileReader(propertyFileLocation);
        properties.load(reader);

        webDriver = getDriver();

    }

    @BeforeMethod
    void startWithFreshApplication() {
        webDriver.get(properties.getProperty("application.url"));

    }

    @AfterClass
    void cleanup() {
        webDriver.close();
    }

    WebDriver getDriver() {
        switch (properties.getProperty("application.browser")) {
            case "safari":
                WebDriverManager.safaridriver().setup();
                return new SafariDriver();
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
        }
    }

    //@Test
    public void testContactPageErrorMsgs() {
        Map<String, String> testData = Map.of("forename","John",
                "email", "test_example@example.com",
                "message", "Some text");
        HomePage homePage = new HomePage(webDriver);
        homePage.clickContactLink();
        ContactPage contactPage = new ContactPage(webDriver);
        contactPage.clickSubmit();
        Assert.assertEquals(contactPage.verifyErrorDisplayed("forename"),
                true, "forename error is not displayed");
        Assert.assertEquals(contactPage.verifyErrorDisplayed("email"),
                true, "email error is not displayed");
        Assert.assertEquals(contactPage.verifyErrorDisplayed("message"),
                true, "message error is not displayed");
        contactPage.enterForename(testData.get("forename"));
        Assert.assertEquals(contactPage.verifyErrorDisplayed("forename"),
                false, "forename error was not supposed to be displayed");
        contactPage.enterEmail(testData.get("email"));
        Assert.assertEquals(contactPage.verifyErrorDisplayed("email"),
                false, "email error was not supposed to be displayed");
        contactPage.enterMessage(testData.get("message"));
        Assert.assertEquals(contactPage.verifyErrorDisplayed("message"),
                false, "message error was not supposed to be displayed");
    }

    //@Test(invocationCount = 5)
    public void testContactPageHappyPath() {
        Map<String, String> testData = Map.of("forename","John",
                "email", "test_example@example.com",
                "message", "Some text");
        HomePage homePage = new HomePage(webDriver);
        homePage.clickContactLink();
        ContactPage contactPage = new ContactPage(webDriver);
        contactPage.enterForename(testData.get("forename"));
        contactPage.enterEmail(testData.get("email"));
        contactPage.enterMessage(testData.get("message"));
        contactPage.clickSubmit();
        Assert.assertEquals(contactPage.verifySuccessMsgDisplayed(testData.get("forename")),
                true, "Success message is not displayed");
    }

    @Test
    public void testCartTotalAmount() {
        Map<String, Integer> testData = Map.of("Stuffed Frog",2,
                "Fluffy Bunny", 5,
                "Valentine Bear", 3);
        HomePage homePage = new HomePage(webDriver);
        homePage.clickStartShoppingBtn();
        ShoppingPage shoppingPage = new ShoppingPage(webDriver);
        HashMap<String, String> itemPrice= new HashMap<>();
        testData.forEach((k,v) -> {
            itemPrice.put(k, shoppingPage.getItemPrice(k));
            for (int i = 0 ; i < v; i++) {
                shoppingPage.buyItem(k);
            }
        });
        shoppingPage.clickCartButton();
        CartPage cartPage = new CartPage(webDriver);
        testData.forEach((k,v) -> {
            HashMap<String, String> cartItem = cartPage.getItemDetails(k);
            Assert.assertEquals(cartItem.get("itemPrice"), itemPrice.get(k),
                    "Price of item" + k + "not equal on cart page");
            Assert.assertEquals(cartItem.get("itemQuantity"), testData.get(k).toString(),
                    "Quantity of item " + k + " does not match the test data");
            Double calculatedSubTotal = Double.parseDouble(cartItem.get("itemPrice").substring(1)) * testData.get(k);
            System.out.println("cal " + calculatedSubTotal);
            Assert.assertEquals(cartItem.get("itemSubTotal").substring(1), calculatedSubTotal.toString(),
                    "Subtotal of item " + " does not match");
        });

        System.out.println("hello");
    }

}
