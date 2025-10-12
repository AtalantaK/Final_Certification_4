package helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static io.qameta.allure.Allure.step;

public class UsefulMethods {

    public static WebDriver driverSetUp() {
        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        WebDriver driver = new EdgeDriver(options);
        driver.manage().window().setPosition(new Point(0, 0));
        driver.get(Constants.URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return driver;
    }

    public static void driverQuit(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }

    public static WebElement findUsernameField(WebDriver driver) {
        return driver.findElement(By.xpath("//input[@placeholder='Username']"));
    }

    public static WebElement findPasswordField(WebDriver driver) {
        return driver.findElement(By.xpath("//input[@placeholder='Password']"));
    }

    public static WebElement findLoginButton(WebDriver driver) {
        return driver.findElement(By.xpath("//input[@value='Login']"));
    }

    public static void enterUsername(WebElement usernameField, String username) {
        step("Ввести username = " + username, () -> usernameField.sendKeys(username));
    }

    public static void enterPassword(WebElement passwordField, String password) {
        step("Ввести password = " + password, () -> passwordField.sendKeys(password));
    }

    public static void clickLogin(WebElement loginButton) {
        step("Нажать на кнопку 'Login'", () -> loginButton.click());
    }

    public static WebElement findItemByName(WebDriver driver, String itemName) {
        return driver.findElement(By.xpath("//div[text()='" + itemName + "']/../../../div[@class='pricebar']/button[text()='Add to cart']"));
    }

    public static void fillField(WebDriver driver, String fieldName, String value) {
        driver.findElement(By.xpath("//input[@name='" + fieldName + "']"))
                .sendKeys(value);
    }

    public static void clickButton(WebDriver driver, String buttonName) {
        driver.findElement(By.xpath("//button[@name='" + buttonName + "']")).click();
    }

    public static void makeScreeshot(WebDriver driver, String screenshotName) throws IOException {
        Screenshot screenshot = new AShot().takeScreenshot(driver);
        ImageIO.write(screenshot.getImage(), "PNG", new File("src/test/java/screenshots/" + screenshotName + "_AR.png"));
    }
}
