package tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.*;

import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StackOverflowTest extends BaseTest {

    @Order(3)
    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    void testInvalidLogin(String browser) {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? chromeDriver : firefoxDriver;
        driver.get("https://stackoverflow.com/users/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        try {
            WebElement errorElement = loginPage.getErrorMessage();
            assertTrue(errorElement.isDisplayed(), "Сообщение об ошибке не отображается.");
        } catch (TimeoutException e) {
            fail("Не дождались появления сообщения об ошибке");
        }
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    void testValidLogin(String browser) {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? chromeDriver : firefoxDriver;
        driver.get("https://stackoverflow.com/users/login");

        LoginPage loginPage = new LoginPage(driver);
        closeBanner(driver);
        String username = ConfigLoader.getProperty("username");
        String password = ConfigLoader.getProperty("password");

        loginPage.login(username, password);

        WebElement avatar = loginPage.getAvatar();
        assertTrue(avatar.isDisplayed(), "Вход не выполнен.");
    }

    @Order(5)
    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    void testInvalidSignUp(String browser) throws InterruptedException {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? new ChromeDriver() : new FirefoxDriver();
        driver.get("https://stackoverflow.com/users/signup");

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp("", "");

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Email cannot be empty"), "Ошибка не найдена на странице");

        driver.quit();
    }

    @Order(6)
    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    public void testValidSignUp(String browser) {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? chromeDriver : firefoxDriver;
        driver.get("https://stackoverflow.com/users/signup");

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp("xenon@mail.ru", "xenon123");

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Something went wrong."), "Ошибка не найдена на странице");
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    public void testSearchUser(String browser) {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? chromeDriver : firefoxDriver;
        driver.get("https://stackoverflow.com/users");

        UsersPage usersPage = new UsersPage(driver);
        String userName = "Дмитрий";

        usersPage.search(userName);

        boolean isUserFound = usersPage.isResultFound();
        assertTrue(isUserFound, "Результаты поиска не найдены на странице.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    public void testSearchTag(String browser) {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? chromeDriver : firefoxDriver;
        driver.get("https://stackoverflow.com/tags");

        TagsPage tagsPage = new TagsPage(driver);
        String tag = "selenium";

        tagsPage.search(tag);

        boolean isUserFound = tagsPage.isResultFound();
        assertTrue(isUserFound, "Результаты поиска не найдены на странице.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    public void testSearchQuestion(String browser) {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? chromeDriver : firefoxDriver;
        driver.get("https://stackoverflow.com/questions");

        QuestionsPage questionsPage = new QuestionsPage(driver);
        String question = "selenium";

        questionsPage.search(question);

        boolean isUserFound = questionsPage.isResultFound();
        assertTrue(isUserFound, "Результаты поиска не найдены на странице.");
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    public void testClickTopQuestion(String browser) {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? chromeDriver : firefoxDriver;
        driver.get("https://stackoverflow.com/questions");

        QuestionsPage questionsPage = new QuestionsPage(driver);

        String expectedTitle = questionsPage.getTopQuestionText();
        String expectedHref = questionsPage.getTopQuestionHref();

        questionsPage.clickTopQuestion();
        String actualTitle = driver.getTitle();

        assertTrue(actualTitle.contains(expectedTitle), "Заголовок страницы не совпадает.");
        assertTrue(driver.getCurrentUrl().contains(expectedHref), "URL не совпадает.");
    }

    @Order(2)
    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    public void testTypeAnswer(String browser) throws IOException {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? chromeDriver : firefoxDriver;
        driver.get("https://stackoverflow.com/questions");

        QuestionsPage questionsPage = new QuestionsPage(driver);
        closeBanner(driver);
        questionsPage.clickTopQuestion();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        questionsPage.typeAnswer("Скажите пожалуйста", "xenon", "xenon@bk.ru");

    }

    @Order(1)
    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    public void testCreateQuestion(String browser) {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? chromeDriver : firefoxDriver;
        driver.get("https://stackoverflow.com/users/login");

        LoginPage loginPage = new LoginPage(driver);
        closeBanner(driver);
        String username = ConfigLoader.getProperty("username");
        String password = ConfigLoader.getProperty("password");
        loginPage.login(username, password);
        loginPage.askButton();

        AskQuestionPage askQuestionPage = new AskQuestionPage(driver);
        askQuestionPage.askQuestion("Заголовок вопроса", "Тело вопросаТело вопроса", "ОжиданияОжиданияОжиданияОжидания", "selenium");
        assertTrue(true);
    }
}
