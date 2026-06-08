package com.haritonov.uitests.utils;

import com.haritonov.uitests.helpers.ParameterProvider;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Утилита для создания скриншотов с помощью библиотеки aShot.
 * <p>Содержит статический метод, который делает скриншот
 * всей страницы и прикрепляет его к текущему Allure-отчёту.
 */
public class ScreenshotUtils {

    /**
     * Делает скриншот всей страницы и прикрепляет его к Allure-отчёту.
     * <p>Используется при падении теста для упрощения анализа ошибки.
     * Скриншот делается с прокруткой (viewportPasting), время задержки
     * между скроллами берётся из конфигурации {@code screenshot.scroll.timeout}.
     *
     * @param driver   активный WebDriver
     * @param testName имя теста
     */
    public static void attachScreenshotOnFailure(WebDriver driver, String testName) {
        try {
            int scrollTimeout = Integer.parseInt(ParameterProvider.get("screenshot.scroll.timeout"));
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(scrollTimeout))
                    .takeScreenshot(driver);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "png", baos);
            Allure.getLifecycle().addAttachment(
                    "Скриншот при падении: " + testName,
                    "image/png",
                    "png",
                    baos.toByteArray()
            );
        } catch (IOException e) {

        }
    }
}
