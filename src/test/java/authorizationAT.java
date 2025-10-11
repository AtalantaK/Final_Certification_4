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

public class authorizationAT {

    private WebDriver driver;
    private static final String URL = "https://www.saucedemo.com/";

    @BeforeEach
    public void setUp() {
        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new EdgeDriver(options);
        driver.manage().window().setPosition(new Point(0, 0));
        driver.get(URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @RepeatedTest(1)
    @DisplayName("Successful authorization")
    @Story("BS-SM-01 Security matrix")
    @Description("Successful authorization as User = standard_user")
    @Tags({@Tag("Authorization"), @Tag("Smoke"), @Tag("Security_matrix")})
    public void successfulAuthorization() {
        String username = "standard_user";
        String password = "secret_sauce";
        WebElement usernameField = driver.findElement(By.xpath("//input[@placeholder='Username']"));
        step("Enter username = " + username, () -> usernameField.sendKeys(username));
        WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        step("Enter password", () -> passwordField.sendKeys(password));
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Login']"));
        step("Click on 'Login'", () -> loginButton.click());

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@class='shopping_cart_link']"))));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
