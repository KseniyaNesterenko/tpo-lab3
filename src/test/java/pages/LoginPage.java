package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    private By emailInput = By.xpath("//input[@type='email' and @class='s-input']");
    private By passwordInput = By.xpath("//input[@type='password' and @name='password' and contains(@class, 'flex--item') and contains(@class, 's-input') and @autocomplete='current-password']");
    private By loginButton = By.xpath("//button[@name='submit-button' and contains(@class, 'flex--item') and contains(@class, 's-btn') and contains(@class, 's-btn__filled') and text()='Log in']");
    private By errorLocator = By.xpath("//*[contains(text(), 'Email cannot be empty')]");
    private By avatarXPath = By.xpath("//img[contains(@class, 'js-avatar-me') and contains(@src, 'gravatar.com')]");
    private By askButton = By.xpath("//*[contains(@class, 'ws-nowrap s-btn s-btn__outlined')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void loginButton(String email, String password) {
        driver.findElement(loginButton).click();
    }

    public void askButton() {
        driver.findElement(askButton).click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        loginButton(email, password);
    }

    public WebElement getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(errorLocator));
    }

    public WebElement getAvatar() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(avatarXPath));
    }
}
