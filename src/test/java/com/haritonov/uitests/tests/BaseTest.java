package com.haritonov.uitests.tests;

import com.haritonov.uitests.helpers.ParameterProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait waiter;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Integer.parseInt(ParameterProvider.get("implicit.wait")))
        );
        waiter = new WebDriverWait(
                driver,
                Duration.ofSeconds(Integer.parseInt(ParameterProvider.get("explicit.wait")))
        );
        driver.get(ParameterProvider.get("base.url"));
    }

    @AfterSuite
    public void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
    }
}
