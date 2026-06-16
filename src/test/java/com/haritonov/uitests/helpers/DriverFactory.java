package com.haritonov.uitests.helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Фабрика для создания экземпляров WebDriver.
 *
 * <p>Поддерживает локальный и удалённый (Selenium Grid) запуск для браузеров:
 * <ul>
 *   <li>Chrome</li>
 *   <li>Firefox</li>
 *   <li>Edge</li>
 *   <li>Internet Explorer</li>
 * </ul>
 *
 * <p>Конфигурация каждого браузера (аргументы командной строки, capabilities)
 * вынесена в отдельные приватные методы.
 */
public class DriverFactory {

    private DriverFactory() {

    }

    /**
     * Создаёт локальный WebDriver для указанного браузера.
     * Драйвер автоматически загружается через {@link WebDriverManager}.
     *
     * @param browserType тип браузера (chrome, firefox, edge, ie)
     * @return экземпляр WebDriver
     * @throws IllegalArgumentException если тип браузера не поддерживается
     */
    public static WebDriver createLocalDriver(String browserType) {
        return switch (browserType.toLowerCase()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(getChromeOptions());
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(getFirefoxOptions());
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(getEdgeOptions());
            }
            case "ie" -> {
                WebDriverManager.iedriver().setup();
                yield new InternetExplorerDriver(getIEOptions());
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserType);
        };
    }

    /**
     * Создаёт удалённый WebDriver для запуска на Selenium Grid.
     *
     * @param browserType тип браузера (chrome, firefox, edge, ie)
     * @param hubUrl      URL хаба
     * @return экземпляр RemoteWebDriver
     * @throws RuntimeException если URL хаба некорректен
     */
    public static WebDriver createRemoteWebDriver(String browserType, String hubUrl) {
        try {
            return switch (browserType.toLowerCase()) {
                case "chrome" -> new RemoteWebDriver(new URL(hubUrl), getChromeOptions());
                case "firefox" -> new RemoteWebDriver(new URL(hubUrl), getFirefoxOptions());
                case "edge" -> new RemoteWebDriver(new URL(hubUrl), getEdgeOptions());
                case "ie" -> new RemoteWebDriver(new URL(hubUrl), getIEOptions());
                default -> throw new IllegalArgumentException("Unsupported browser: " + browserType);
            };
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Grid hub URL: " + hubUrl, e);
        }
    }

    /**
     * Настройки Chrome: режим инкогнито.
     */
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(ParameterProvider.get("browser.chrome.argument.incognito"));
        return options;
    }

    /**
     * Настройки Firefox: приватный режим.
     */
    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(ParameterProvider.get("browser.firefox.argument.incognito"));          // режим инкогнито
        return options;
    }

    /**
     * Настройки Edge: режим InPrivate.
     */
    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments(ParameterProvider.get("browser.edge.argument.incognito"));
        return options;
    }

    /**
     * Настройки Internet Explorer: обязательные capability для стабильной работы.
     */
    private static InternetExplorerOptions getIEOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        return options;
    }
}
