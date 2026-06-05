package com.haritonov.uitests.tests;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.helpers.Waiter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Абстрактный базовый класс для всех тестов.
 * <p>Перед каждым тестом запускает Chrome с нужными опциями,
 * создаёт экземпляр {@link Waiter} и переходит на базовый URL.
 * После теста закрывает браузер.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected Waiter waiter;

    /**
     * Инициализирует WebDriver, Waiter и открывает главную страницу.
     */
    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        String[] args = ParameterProvider.get("browser.chrome.arguments").split("\\s*,\\s*");
        options.addArguments(args);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        waiter = new Waiter(driver);
        driver.get(ParameterProvider.get("base.url"));
    }

    /**
     * Закрывает браузер после каждого теста.
     */
    @AfterMethod
    public void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
    }
}
