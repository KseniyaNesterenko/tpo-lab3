package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseSearchPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    private By searchInput;
    private By resultLocator;

    public BaseSearchPage(WebDriver driver, By searchInput, By resultLocator) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.searchInput = searchInput;
        this.resultLocator = resultLocator;
    }

    public void search(String searchText) {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));

        WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        inputField.clear();
        inputField.sendKeys(searchText);
        inputField.sendKeys(Keys.ENTER);
    }

    public boolean isResultFound() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(resultLocator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}