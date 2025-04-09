package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AskQuestionPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By titleInput = By.xpath("//input[@name='title' and contains(@class, 'js-post-title-field') and @type='text']");
    private By detailsInput = By.xpath("//*[contains(@name, 'post-text')]");
    private By tagsInput = By.xpath("//div[contains(@class,'js-tag-editor')]/input");
    private By startWritingButton = By.xpath("//button[contains(@class, 'js-modal-primary-btn') and text()='Start writing']");
    private By reviewButton = By.xpath("//*[contains(@class, 'flex--item s-btn s-btn__filled s-btn__icon ws-nowrap js-begin-review-button js-gps-track')]");


    public AskQuestionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public void enterTitle(String title) {
        WebElement titleField = driver.findElement(titleInput);
        scrollToElement(titleField);
        titleField.clear();
        titleField.sendKeys(title);
    }

    public void enterDetails(String details) {
        WebElement detailsField = driver.findElement(detailsInput);
        scrollToElement(detailsField);
        detailsField.clear();
        detailsField.sendKeys(details);
    }

    public void enterTags(String tags) {
        WebElement tagsField = driver.findElement(tagsInput);
        scrollToElement(tagsField);
        tagsField.sendKeys(tags);
        tagsField.sendKeys(Keys.ENTER);
    }

    public void clickReviewButton() {
        try {
            waitForElementToBeClickable(reviewButton);
            WebElement rvwBtn = driver.findElement(reviewButton);
            scrollToElement(rvwBtn);
            rvwBtn.click();
        } catch (TimeoutException e) {
            System.out.println("Кнопка недоступна для клика.");
        } catch (NoSuchElementException e) {
            System.out.println("Кнопка не найдена.");
        }
    }

    private void dismissStartWritingPopup() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(startWritingButton));
            WebElement startBtn = driver.findElement(startWritingButton);
            scrollToElement(startBtn);
            startBtn.click();
        } catch (TimeoutException e) {
            System.out.println("Окно не появилось.");
        }
    }


    public void askQuestion(String title, String details, String tags) {
        dismissStartWritingPopup();
        enterTitle(title);
        enterDetails(details);
        enterTags(tags);
        clickReviewButton();
    }
}
