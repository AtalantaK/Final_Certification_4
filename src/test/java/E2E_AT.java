import helpers.Constants;
import helpers.UsefulMethods;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
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
        driver = UsefulMethods.driverSetUp();
    }

    @ParameterizedTest
    @CsvSource({Constants.STANDARD_USER, Constants.PERFORMANCE_GLITCH_USER})
    @DisplayName("Add to Cart")
    @Story("BS-E2E-01 Add to Cart")
    @Description("The user adds items to the shopping cart")
    @Tags({@Tag("E2E"), @Tag("BusinessAT"), @Tag("Positive")})
    public void e2e_standard_user(String username) {
        WebElement usernameField = UsefulMethods.findUsernameField(driver);
        UsefulMethods.enterUsername(usernameField, username);

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

        step("Ввести First Name = " + Constants.FIRST_NAME, () -> UsefulMethods.fillField(driver, "firstName", Constants.FIRST_NAME));
        step("Ввести Last Name = " + Constants.LAST_NAME, () -> UsefulMethods.fillField(driver, "lastName", Constants.LAST_NAME));
        step("Ввести Postal Code = " + Constants.POSTAL_CODE, () -> UsefulMethods.fillField(driver, "postalCode", Constants.POSTAL_CODE));

        step("Нажать на кнопку 'Continue'", () -> driver.findElement(By.xpath("//input[@name='continue']")).click());

        String actualTotal = driver.findElement(By.xpath("//div[@class='summary_total_label']")).getText();

        step("Нажать на кнопку 'Finish'", () -> UsefulMethods.clickButton(driver, "finish"));

        String actualHeader = driver.findElement(By.xpath("//h2[@class='complete-header']")).getText();
        String actualText = driver.findElement(By.xpath("//div[@class='complete-text']")).getText();

        assertAll("Несколько проверок",
                () -> assertThat(actualShoppingCartBadge).isEqualTo(Constants.EXPECTED_SHOPPING_CART_BADGE),
                () -> assertThat(actualTotal).isEqualTo(Constants.EXPECTED_TOTAL),
                () -> assertThat(actualHeader).isEqualTo(Constants.EXPECTED_HEADER),
                () -> assertThat(actualText).isEqualTo(Constants.EXPECTED_TEXT));
    }

    @AfterEach
    public void tearDown() {
        UsefulMethods.driverQuit(driver);
    }
}
