package com.haritonov.uitests.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Утилита для выполнения JavaScript-команд через {@link JavascriptExecutor}.
 * <p>Предоставляет методы для снятия фокуса с элементов и проверки наличия скролла на странице.
 */
public class JavaScriptUtils {

    /**
     * Убирает фокус с указанного элемента.
     *
     * @param driver  WebDriver
     * @param element элемент, с которого нужно снять фокус
     */
    @Step("Убрать фокус с элемента")
    public static void removeFocus(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", element);
    }

    /**
     * Проверяет, есть ли вертикальный скролл на странице.
     *
     * @param driver WebDriver
     * @return {@code true}, если высота страницы больше высоты окна
     */
    @Step("Проверить наличие вертикального скролла на странице")
    public static boolean isVerticalScrollPresent(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long pageHeight = (long) js.executeScript("return document.body.scrollHeight;");
        long windowHeight = (long) js.executeScript("return window.innerHeight;");
        return pageHeight > windowHeight;
    }
}
