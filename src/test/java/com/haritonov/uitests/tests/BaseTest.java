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
 * Абстрактный базовый класс для всех UI-тестов.
 *
 * <p>Предоставляет общую настройку WebDriver, Chrome браузера и {@link Waiter}.
 * Использует {@link ThreadLocal} для thread-safe хранения драйвера и Waiter,
 * что позволяет корректно выполнять тесты в параллельном режиме.
 *
 * <p>Перед каждым тестом создаётся новый экземпляр {@link ChromeDriver} с
 * аргументами, прочитанными из конфигурации {@code browser.chrome.arguments},
 * и открывается базовый URL {@code base.url}.
 *
 * <p>После каждого теста драйвер завершает работу и ресурсы очищаются.
 */
public abstract class BaseTest {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<Waiter> WAITER = new ThreadLocal<>();

    /**
     * Экземпляр WebDriver, доступный в тестах.
     */
    protected WebDriver driver;

    /**
     * Экземпляр Waiter, доступный в тестах.
     */
    protected Waiter waiter;

    /**
     * Инициализирует браузер и Waiter перед каждым тестом.
     * <p>Конфигурация Chrome загружается из {@code browser.chrome.arguments},
     * драйвер автоматически управляется через {@link WebDriverManager}.
     */
    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        String[] args = ParameterProvider.get("browser.chrome.arguments").split("\\s*,\\s*");
        options.addArguments(args);
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);

        DRIVER.set(driver);
        WAITER.set(new Waiter(driver));

        this.driver = DRIVER.get();
        this.waiter = WAITER.get();
        driver.get(ParameterProvider.get("base.url"));
    }

    /**
     * Закрывает браузер и очищает ресурсы после каждого теста.
     */
    @AfterMethod
    public void tearDown() {
        WebDriver drv = DRIVER.get();
        if (drv != null) {
            drv.quit();
            DRIVER.remove();
            WAITER.remove();
        }
    }

    /**
     * Возвращает текущий WebDriver.
     * <p>Используется слушателями (например, {@code ScreenshotListener}) для
     * доступа к драйверу извне тестового класса.
     *
     * @return экземпляр {@link WebDriver}, связанный с текущим потоком
     */
    public WebDriver getDriver() {
        return DRIVER.get();
    }
}