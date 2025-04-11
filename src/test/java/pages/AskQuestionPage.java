package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AskQuestionPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By titleInput = By.xpath("//input[@name='title' and contains(@class, 'js-post-title-field') and @type='text']");
//    private By detailsInput = By.xpath("//*[contains(@class, 'fl-grow1 outline-none p12 pt6 w100 s-prose js-editor ProseMirror')]");
//
//    private By expectsInput = By.xpath("//*[contains(@class, 'fl-grow1 outline-none p12 pt6 w100 s-prose js-editor ProseMirror')]");
    private By editorInputs = By.xpath("//*[contains(@class, 'js-editor ProseMirror')]");

    private By tagsInput = By.xpath("//*[contains(@class, 's-input js-tageditor-replacing')]");
    private By startWritingButton = By.xpath("//button[contains(@class, 'js-modal-primary-btn') and text()='Start writing']");
    private By nextButton = By.xpath("//*[contains(@class, 's-btn s-btn__filled mt12 js-next-problem-details js-next-buttons')]");
    private By reviewButton = By.xpath("//*[contains(@class, 's-btn s-btn__filled s-btn__icon js-review-question-button')]");


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
        List<WebElement> editors = driver.findElements(editorInputs);
        if (editors.size() > 0) {
            WebElement detailsField = editors.get(0);
            scrollToElement(detailsField);
            detailsField.clear();
            detailsField.sendKeys(details);
        }
    }

    private void enterExpectations(String expects) {
        List<WebElement> editors = driver.findElements(editorInputs);
        if (editors.size() > 1) {
            WebElement expectsField = editors.get(1);
            scrollToElement(expectsField);
            expectsField.clear();
            expectsField.sendKeys(expects);
        }
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

    public void clickNextButton() {
        List<WebElement> buttons = driver.findElements(nextButton);
        if (buttons.isEmpty()) {
            return;
        }

        WebElement nextBtn = buttons.get(0);
        try {
            waitForElementToBeClickable(nextButton);
            scrollToElement(nextBtn);
            nextBtn.click();
        } catch (Exception ignored) {
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


    public void askQuestion(String title, String details, String expects, String tags) {
//        dismissStartWritingPopup();
        enterTitle(title);
        clickNextButton();
        enterDetails(details);
        clickNextButton();
        enterExpectations(expects);
        clickNextButton();
        enterTags(tags);
        clickReviewButton();
    }

}
