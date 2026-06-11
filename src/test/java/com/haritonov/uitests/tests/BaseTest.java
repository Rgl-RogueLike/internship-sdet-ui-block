package com.haritonov.uitests.tests;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.helpers.Waiter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;

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
     * Инициализирует браузер и {@link Waiter} перед каждым тестом.
     *
     * <p>Способ запуска определяется методом {@link #isGridEnabled()}:
     * <ul>
     *   <li><b>Локальный запуск</b> – {@link #initLocalDriver()} создаёт
     *       {@link ChromeDriver} с настройками из {@code browser.chrome.arguments}.</li>
     *   <li><b>Запуск через Selenium Grid</b> – {@link #initRemoteDriver()} создаёт
     *       {@link RemoteWebDriver}, подключающийся к хабу по адресу
     *       {@code grid.hub.url}.</li>
     * </ul>
     * Драйвер и Waiter сохраняются в {@link ThreadLocal}, что обеспечивает
     * корректную работу при параллельном выполнении тестов.
     */
    @BeforeMethod
    public void setUp() {
        if (isGridEnabled()) {
            initRemoteDriver();
        } else {
            initLocalDriver();
        }
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

    /**
     * Определяет, нужно ли использовать Selenium Grid.
     * <p>Сначала проверяет переменную окружения {@code GRID_ENABLED},
     * при её отсутствии — значение из конфигурации {@code grid.enabled}.
     *
     * @return {@code true}, если должен использоваться Grid
     */
    private boolean isGridEnabled() {
        String gridEnv = System.getenv("GRID_ENABLED");
        if (gridEnv != null) {
            return Boolean.parseBoolean(gridEnv);
        }
        return Boolean.parseBoolean(ParameterProvider.get("grid.enabled"));
    }

    /**
     * Инициализирует RemoteWebDriver для запуска тестов через Selenium Grid.
     * <p>Адрес хаба берётся из конфигурации {@code grid.hub.url}.
     * В случае некорректного URL выбрасывается RuntimeException.
     */
    private void initRemoteDriver() {
        String hubUrl = ParameterProvider.get("grid.hub.url");
        ChromeOptions options = new ChromeOptions();
        String[] args = ParameterProvider.get("browser.chrome.arguments").split("\\s*,\\s*");
        options.addArguments(args);
        try {
            WebDriver driver = new RemoteWebDriver(new URL(hubUrl), options);
            DRIVER.set(driver);
            WAITER.set(new Waiter(driver));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Grid hub URL: " + hubUrl, e);
        }
    }

    /**
     * Инициализирует локальный ChromeDriver.
     * <p>Драйвер автоматически загружается через {@link WebDriverManager}.
     */
    private void initLocalDriver() {
        ChromeOptions options = new ChromeOptions();
        String[] args = ParameterProvider.get("browser.chrome.arguments").split("\\s*,\\s*");
        options.addArguments(args);
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
        DRIVER.set(driver);
        WAITER.set(new Waiter(driver));
    }
}