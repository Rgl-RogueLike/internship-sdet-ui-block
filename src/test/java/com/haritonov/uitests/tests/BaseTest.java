package com.haritonov.uitests.tests;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.helpers.Waiter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected WebDriver driver;
    protected Waiter waiter;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-password-manager-reauthentication");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        waiter = new Waiter(driver);
        driver.get(ParameterProvider.get("base.url"));
    }

    @AfterMethod
    public void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
    }
}
