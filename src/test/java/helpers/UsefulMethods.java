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
        step("Enter username = " + username, () -> usernameField.sendKeys(username));
    }

    public static void enterPassword(WebElement passwordField, String password) {
        step("Enter password = " + password, () -> passwordField.sendKeys(password));
    }

    public static void clickLogin(WebElement loginButton) {
        step("Click on 'Login'", () -> loginButton.click());
    }
}
