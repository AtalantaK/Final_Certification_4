package helpers;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

import static io.qameta.allure.Allure.step;

public class UsefulMethods {

    public static WebDriver driverSetUp() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless");  // Включаем headless режим
        options.addArguments("--disable-gpu"); // Для Windows, чтобы избежать ошибок
        options.addArguments("--no-sandbox");  // Для CI/CD, если нужно
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

    public static WebElement findByPlaceholder(WebDriver driver, String placeholderName) {
        return driver.findElement(By.xpath("//input[@placeholder='" + placeholderName + "']"));
    }

    public static WebElement findLoginButton(WebDriver driver) {
        return driver.findElement(By.xpath("//input[@value='Login']"));
    }

    public static void enterValue(WebElement webElement, String fieldName) {
        if (Objects.equals(fieldName, Constants.PASSWORD))
            step("Ввести пароль = " + fieldName, () -> webElement.sendKeys(fieldName));
        else step("Ввести логин = " + fieldName, () -> webElement.sendKeys(fieldName));
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

    public static void makeScreeshot(WebDriver driver) throws IOException {
        Screenshot screenshot = new AShot().takeScreenshot(driver);
        //ImageIO.write(screenshot.getImage(), "PNG", new File("src/test/java/screenshots/" + screenshotName + "_AR.png"));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(screenshot.getImage(), "PNG", baos);
        byte[] screenshotBytes = baos.toByteArray();

        // Прикрепляем скриншот к Allure отчету
        step("Скриншот", () -> Allure.addAttachment("Актуальный результат", "image/png", new ByteArrayInputStream(screenshotBytes), ".png"));
    }
}
