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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class E2E_AT {
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
    @DisplayName("standard_user")
    @Story("BS-E2E-01 Add to Cart")
    @Description("The user adds items to the shopping cart")
    @Tags({@Tag("E2E"), @Tag("BusinessAT"), @Tag("Positive")})
    public void e2e_standard_user() {
        WebElement usernameField = UsefulMethods.findUsernameField(driver);
        UsefulMethods.enterUsername(usernameField, Constants.STANDARDUSER);

        WebElement passwordField = UsefulMethods.findPasswordField(driver);
        UsefulMethods.enterPassword(passwordField, Constants.PASSWORD);

        WebElement loginButton = UsefulMethods.findLoginButton(driver);
        UsefulMethods.clickLogin(loginButton);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@class='shopping_cart_link']"))));

        List<String> items = new ArrayList<>(Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt", "Sauce Labs Onesie"));

        for (String item : items) {
            step("Добавить 1 товар '" + item + "' в корзину", () -> UsefulMethods.findItemByName(driver, item).click());
        }

        String actualShoppingCartBadge = driver.findElement(By.xpath("//span[@class='shopping_cart_badge']")).getText();

        step("Перейти в корзину", () -> driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click());

        for (String item : items) {
            step("Товар '" + item + "' в корзине", () -> driver.findElement(By.xpath("//div[text()='" + item + "']")));
        }

        step("Нажать на кнопку 'Checkout'", () -> UsefulMethods.clickButton(driver, "checkout"));

        step("Ввести First Name = " + Constants.firstName, () -> UsefulMethods.fillField(driver, "firstName", Constants.firstName));
        step("Ввести Last Name = " + Constants.lastName, () -> UsefulMethods.fillField(driver, "lastName", Constants.lastName));
        step("Ввести Postal Code = " + Constants.postalCode, () -> UsefulMethods.fillField(driver, "postalCode", Constants.postalCode));

        step("Нажать на кнопку 'Continue'", () -> driver.findElement(By.xpath("//input[@name='continue']")).click());

        String actualTotal = driver.findElement(By.xpath("//div[@class='summary_total_label']")).getText();

        step("Нажать на кнопку 'Finish'", () -> UsefulMethods.clickButton(driver, "finish"));

        String actualHeader = driver.findElement(By.xpath("//h2[@class='complete-header']")).getText();
        String actualText = driver.findElement(By.xpath("//div[@class='complete-text']")).getText();

        assertAll("Несколько проверок",
                () -> assertThat(actualShoppingCartBadge).isEqualTo(Constants.expectedShoppingCartBadge),
                () -> assertThat(actualTotal).isEqualTo(Constants.expectedTotal),
                () -> assertThat(actualHeader).isEqualTo(Constants.expectedHeader),
                () -> assertThat(actualText).isEqualTo(Constants.expectedText));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
