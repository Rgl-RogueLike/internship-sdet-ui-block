package com.haritonov.uitests.listeners;

import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG-слушатель, автоматически делающий скриншот при падении теста.
 * <p>Работает только для тестов, наследующих {@link BaseTest}, так как получает
 * WebDriver через публичный геттер.
 * Скриншот прикрепляется к Allure-отчёту с помощью {@link ScreenshotUtils}.
 */
public class ScreenshotListener implements ITestListener {

    /**
     * Вызывается TestNG при падении теста.
     * Извлекает WebDriver из упавшего тестового экземпляра и делегирует
     * создание скриншота в {@link ScreenshotUtils#attachScreenshotOnFailure(WebDriver, String)}.
     *
     * @param result результат упавшего теста
     */
    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testInstance).getDriver();
            if (driver != null) {
                ScreenshotUtils.attachScreenshotOnFailure(
                        driver,
                        result.getMethod().getMethodName()
                );
            }
        }
    }
}
