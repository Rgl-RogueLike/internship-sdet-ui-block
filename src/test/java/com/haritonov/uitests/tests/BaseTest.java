package com.haritonov.uitests.tests;

import com.haritonov.uitests.helpers.DriverFactory;
import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.helpers.Waiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Абстрактный базовый класс для всех UI-тестов.
 *
 * <p>Предоставляет общую настройку WebDriver и {@link Waiter}.
 * Использует {@link ThreadLocal} для thread-safe хранения драйвера и Waiter,
 * что позволяет корректно выполнять тесты в параллельном режиме.
 *
 * <p>Тип браузера определяется параметром {@code browser.type} в конфигурации
 * (chrome, firefox, edge, ie). Создание драйвера делегируется в
 * {@link DriverFactory}, которая поддерживает локальный и удалённый (Grid) запуск.
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
     * <p>Тип браузера определяется методом {@link #getBrowserType()}:
     * сначала проверяется переменная окружения {@code BROWSER_TYPE},
     * при её отсутствии — значение из конфигурации {@code browser.type}.
     *
     * <p>Способ запуска определяется методом {@link #isGridEnabled()}:
     * <ul>
     *   <li><b>Локальный запуск</b> – {@link #initLocalDriver(String)} создаёт
     *       драйвер через {@link DriverFactory#createLocalDriver(String)}.</li>
     *   <li><b>Запуск через Selenium Grid</b> – {@link #initRemoteDriver(String)}
     *       создаёт {@link RemoteWebDriver} через
     *       {@link DriverFactory#createRemoteWebDriver(String, String)}.</li>
     * </ul>
     * Драйвер и Waiter сохраняются в {@link ThreadLocal}, что обеспечивает
     * корректную работу при параллельном выполнении тестов.
     */
    @BeforeMethod
    public void setUp() {
        String browserType = getBrowserType();
        if (isGridEnabled()) {
            initRemoteDriver(browserType);
        } else {
            initLocalDriver(browserType);
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
     * Инициализирует удалённый WebDriver для запуска тестов через Selenium Grid.
     * <p>Адрес хаба берётся из конфигурации {@code grid.hub.url}.
     * Тип браузера передаётся в {@link DriverFactory#createRemoteWebDriver(String, String)}.
     *
     * @param browserType тип браузера (chrome, firefox, edge, ie)
     */
    private void initRemoteDriver(String browserType) {
        String hubUrl = ParameterProvider.get("grid.hub.url");
        WebDriver driver = DriverFactory.createRemoteWebDriver(browserType, hubUrl);
        driver.manage().window().maximize();
        DRIVER.set(driver);
        WAITER.set(new Waiter(driver));
    }

    /**
     * Инициализирует локальный WebDriver.
     * <p>Тип браузера передаётся в {@link DriverFactory#createLocalDriver(String)}.
     *
     * @param browserType тип браузера (chrome, firefox, edge, ie)
     */
    private void initLocalDriver(String browserType) {
        WebDriver driver = DriverFactory.createLocalDriver(browserType);
        driver.manage().window().maximize();
        DRIVER.set(driver);
        WAITER.set(new Waiter(driver));
    }

    /**
     * Определяет тип браузера для запуска тестов.
     * <p>Сначала проверяет переменную окружения {@code BROWSER_TYPE},
     * при её отсутствии — значение из конфигурации {@code browser.type}.
     *
     * @return строку с типом браузера (chrome, firefox, edge, ie)
     */
    private String getBrowserType() {
        String browserEnv = System.getenv("BROWSER_TYPE");
        if (browserEnv != null && !browserEnv.isEmpty()) {
            return browserEnv.toLowerCase();
        }
        return ParameterProvider.get("browser.type");
    }
}