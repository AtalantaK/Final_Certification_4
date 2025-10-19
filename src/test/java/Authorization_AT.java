import helpers.Config;
import helpers.Constants;
import helpers.UsefulMethods;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class Authorization_AT {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = UsefulMethods.driverSetUp();
    }

    @RepeatedTest(1)
    @DisplayName("Successful authorization")
    @Story("BS-SM-01 Security matrix")
    @Description("Successful authorization as User = standard_user")
    @Tags({@Tag("Authorization"), @Tag("Smoke"), @Tag("Security_matrix"), @Tag("Positive")})
    public void successfulAuthorization() throws IOException {

        WebElement usernameField = UsefulMethods.findByPlaceholder(driver, "Username");
        UsefulMethods.enterValue(usernameField, Constants.STANDARD_USER);

        UsefulMethods.makeScreeshot(driver);

        WebElement passwordField = UsefulMethods.findByPlaceholder(driver,"Password");
        UsefulMethods.enterValue(passwordField, Config.get("PASSWORD"));

        UsefulMethods.makeScreeshot(driver);

        WebElement loginButton = UsefulMethods.findLoginButton(driver);
        UsefulMethods.clickLogin(loginButton);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("shopping_cart_link"))));
        //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@class='shopping_cart_link']"))));

        UsefulMethods.makeScreeshot(driver);
    }

    @RepeatedTest(1)
    @DisplayName("Blocked user authorization")
    @Story("BS-SM-01 Security matrix")
    @Description("Unsuccessful authorization as User = locked_out_user")
    @Tags({@Tag("Authorization"), @Tag("Smoke"), @Tag("Security_matrix"), @Tag("Negative")})
    public void unsuccessfulAuthorization() throws IOException {

        WebElement usernameField = UsefulMethods.findByPlaceholder(driver, "Username");
        UsefulMethods.enterValue(usernameField, Constants.LOCKED_OUT_USER);

        UsefulMethods.makeScreeshot(driver);

        WebElement passwordField = UsefulMethods.findByPlaceholder(driver, "Password");
        UsefulMethods.enterValue(passwordField, Config.get("PASSWORD"));

        UsefulMethods.makeScreeshot(driver);

        WebElement loginButton = UsefulMethods.findLoginButton(driver);
        UsefulMethods.clickLogin(loginButton);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h3[text()='Epic sadface: Sorry, this user has been locked out.']"))));

        WebElement errorMessage = driver.findElement(By.className("error-message-container"));
        //WebElement errorMessage = driver.findElement(By.xpath("//div[@class='error-message-container error']"));
        String actualBackgroundErrorMessage = errorMessage.getCssValue("background-color");

        usernameField = UsefulMethods.findByPlaceholder(driver, "Username");
        String actualBorderUsername = usernameField.getCssValue("border-bottom-color");

        passwordField = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        String actualBorderPassword = passwordField.getCssValue("border-bottom-color");

        UsefulMethods.makeScreeshot(driver);

        assertAll("Несколько проверок",
                () -> assertThat(actualBackgroundErrorMessage).isEqualTo(Constants.BACKGROUND_COLOR),
                () -> assertThat(actualBorderUsername).isEqualTo(Constants.BACKGROUND_COLOR),
                () -> assertThat(actualBorderPassword).isEqualTo(Constants.BACKGROUND_COLOR));
    }

    @AfterEach
    public void tearDown() {
        UsefulMethods.driverQuit(driver);
    }
}
