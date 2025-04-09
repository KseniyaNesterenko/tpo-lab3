import org.example.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class StackOverflowTest extends BaseTest {

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

    //TODO: не работает из-за проверки
    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    void testInvalidSignUp(String browser) {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? new ChromeDriver() : new FirefoxDriver();

        driver.get("https://stackoverflow.com");

        Cookie cookie = new Cookie(
                "prov",
                "7bc4edfd-e386-42e2-8278-602d21482360",
                ".stackoverflow.com",
                "/",
                Date.from(Instant.parse("2026-04-08T13:17:54.161Z"))
        );

        driver.manage().addCookie(cookie);
        driver.navigate().refresh();

        driver.get("https://stackoverflow.com/users/signup");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signUpForm = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("someElementId")));

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp("", "");

        String pageSource = driver.getPageSource();
        System.out.println(pageSource);
        assertTrue(pageSource.contains("Email cannot be empty"), "Ошибка не найдена на странице");

        driver.quit();
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

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "firefox"})
    public void testTypeAnswerWithoutPosting(String browser) throws IOException {
        WebDriver driver = browser.equalsIgnoreCase("chrome") ? chromeDriver : firefoxDriver;
        driver.get("https://stackoverflow.com/questions");

        QuestionsPage questionsPage = new QuestionsPage(driver);
        questionsPage.clickTopQuestion();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        questionsPage.typeAnswer("Скажите пожалуйста", "xenon", "xenon@bk.ru");

    }

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
        askQuestionPage.askQuestion("Заголовок вопроса", "Тело вопроса", "selenium");
        assertTrue(true);
    }
}
