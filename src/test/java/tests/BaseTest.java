package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

public class BaseTest {
    protected WebDriver chromeDriver;
    protected WebDriver firefoxDriver;

    @BeforeEach
    public void setUp() {
        chromeDriver = setUpBrowser("chrome");
        firefoxDriver = setUpBrowser("firefox");
    }

    public WebDriver setUpBrowser(String browser) {
        WebDriver driver;
        if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
            options.setExperimentalOption("useAutomationExtension", false);
            driver = new ChromeDriver(options);
            ((JavascriptExecutor) driver).executeScript(
                    "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"
            );
        } else if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--disable-notifications");
            driver = new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("Браузер не поддерживается: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    @AfterEach
    public void tearDown() {
        if (chromeDriver != null) {
            chromeDriver.quit();
        }
        if (firefoxDriver != null) {
            firefoxDriver.quit();
        }
    }

    public void closeBanner(WebDriver driver) {
        try {
            WebElement closeButton = driver.findElement(By.xpath("//button[contains(text(), 'Accept')]"));
            closeButton.click();
        } catch (Exception e) {
        }
    }
}
