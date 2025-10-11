package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static io.qameta.allure.Allure.step;

public class UsefulMethods {

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
}
