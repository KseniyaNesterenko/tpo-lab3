package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class QuestionsPage extends BaseSearchPage {

    public QuestionsPage(WebDriver driver) {
        super(driver, By.xpath("//*[contains(@name, 'q')]"),
                By.xpath("//*[contains(text(), 'selenium')]"));
    }

    private By topQuestionLink = By.xpath("//div[@id='questions']//div[contains(@class, 's-post-summary')][1]//h3/a");

    public void clickTopQuestion() {
        WebElement topQuestion = driver.findElement(topQuestionLink);
        topQuestion.click();
    }

    public String getTopQuestionText() {
        return driver.findElement(topQuestionLink).getText();
    }

    public String getTopQuestionHref() {
        return driver.findElement(topQuestionLink).getAttribute("href");
    }

    public void typeAnswer(String answerText, String name, String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement yourAnswerHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'Your Answer') or @id='your-answer-header']")
        ));
        scrollToElement(yourAnswerHeader);

        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@name, 'display-name')]")
        ));
        nameField.sendKeys(name);

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@name, 'm-address')]")
        ));
        emailField.sendKeys(email);

        js.executeScript("window.scrollBy(0, -350);");

        WebElement answerField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'js-editor') and contains(@class, 'ProseMirror') and @contenteditable='true']")
        ));
        answerField.click();
        answerField.sendKeys(answerText);

        WebElement postAnswerButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[@type='submit' and contains(., 'Post Your Answer')]")
        ));

        if (postAnswerButton.isEnabled()) {
            System.out.println("Кнопка 'Post Your Answer' активна.");
        } else {
            System.out.println("Кнопка не активна (печально).");
        }
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }
}
