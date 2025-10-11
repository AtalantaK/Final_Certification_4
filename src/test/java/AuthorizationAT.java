import helpers.Constants;
import helpers.UsefulMethods;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthorizationAT {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new EdgeDriver(options);
        driver.manage().window().setPosition(new Point(0, 0));
        driver.get(Constants.URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @RepeatedTest(1)
    @DisplayName("Successful authorization")
    @Story("BS-SM-01 Security matrix")
    @Description("Successful authorization as User = standard_user")
    @Tags({@Tag("Authorization"), @Tag("Smoke"), @Tag("Security_matrix"), @Tag("Positive")})
    public void successfulAuthorization() {

        WebElement usernameField = UsefulMethods.findUsernameField(driver);
        UsefulMethods.enterUsername(usernameField, Constants.STANDARDUSER);

        WebElement passwordField = UsefulMethods.findPasswordField(driver);
        UsefulMethods.enterPassword(passwordField, Constants.PASSWORD);

        WebElement loginButton = UsefulMethods.findLoginButton(driver);
        UsefulMethods.clickLogin(loginButton);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@class='shopping_cart_link']"))));
    }

    @RepeatedTest(1)
    @DisplayName("Blocked user authorization")
    @Story("BS-SM-01 Security matrix")
    @Description("Unsuccessful authorization as User = locked_out_user")
    @Tags({@Tag("Authorization"), @Tag("Smoke"), @Tag("Security_matrix"), @Tag("Negative")})
    public void unsuccessfulAuthorization() {

        WebElement usernameField = UsefulMethods.findUsernameField(driver);
        UsefulMethods.enterUsername(usernameField, Constants.LOCKEDOUTUSER);

        WebElement passwordField = UsefulMethods.findPasswordField(driver);
        UsefulMethods.enterPassword(passwordField, Constants.PASSWORD);

        WebElement loginButton = UsefulMethods.findLoginButton(driver);
        UsefulMethods.clickLogin(loginButton);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h3[text()='Epic sadface: Sorry, this user has been locked out.']"))));

        WebElement errorMessage = driver.findElement(By.xpath("//div[@class='error-message-container error']"));
        String actualBackgroundErrorMessage = errorMessage.getCssValue("background-color");

        usernameField = UsefulMethods.findUsernameField(driver);
        String actualBorderUsername = usernameField.getCssValue("border-bottom-color");

        passwordField = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        String actualBorderPassword = passwordField.getCssValue("border-bottom-color");

        assertAll("Несколько проверок",
                () -> assertThat(actualBackgroundErrorMessage).isEqualTo(Constants.BACKGROUNDCOLOR),
                () -> assertThat(actualBorderUsername).isEqualTo(Constants.BACKGROUNDCOLOR),
                () -> assertThat(actualBorderPassword).isEqualTo(Constants.BACKGROUNDCOLOR));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
