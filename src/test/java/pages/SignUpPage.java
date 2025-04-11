package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class SignUpPage {
    private WebDriver driver;

    private By emailInput = By.xpath("//input[@name='email' and contains(@class, 's-input')]");
    private By passwordInput = By.xpath("//input[@class='flex--item s-input' and @name='password' and @type='password' and @autocomplete='new-password']");
    private By signUpButton = By.xpath("//button[@class='s-btn flex--item mt12 s-btn__filled' and @name='submit-button' and @type='submit' and text()='Sign up']");

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    private WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void enterEmail(String email) {
        waitForElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        waitForElement(passwordInput).sendKeys(password);
    }

    public void clickSignUpButton() {
        waitForElement(signUpButton).click();
    }

    public void signUp(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignUpButton();
    }
}
