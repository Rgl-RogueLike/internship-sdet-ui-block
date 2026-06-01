package com.haritonov.uitests.helpers;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Централизованный помощник для явных ожиданий Selenium.
 * <p>Обёртка над {@link WebDriverWait}, которая предоставляет часто используемые
 * методы ожидания и специальную паузу {@link #waitForTransactionToProcess(long)}.
 * Таймаут по умолчанию читается из конфигурации {@code explicit.wait}.
 */
public class Waiter {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /**
     * Создаёт экземпляр Waiter с таймаутом из конфигурации.
     *
     * @param driver экземпляр WebDriver, для которого будут выполняться ожидания
     */
    public Waiter(WebDriver driver) {
        this.driver = driver;
        int explicitTimeout = Integer.parseInt(ParameterProvider.get("explicit.wait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitTimeout));
    }

    /**
     * Ожидает, пока элемент станет видимым.
     *
     * @param element веб-элемент, видимость которого требуется дождаться
     * @return текущий экземпляр Waiter для цепочечных вызовов
     */
    public Waiter waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    /**
     * Возвращает текст элемента после того, как он стал видимым.
     *
     * @param element целевой веб-элемент
     * @return видимый текст элемента
     */
    public String getTextWhenVisible(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }

    /**
     * Ожидает, пока элемент станет кликабельным.
     *
     * @param element веб-элемент, который должен стать кликабельным
     * @return текущий экземпляр Waiter для цепочечных вызовов
     */
    public Waiter waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    /**
     * Кликает по элементу, предварительно дождавшись его кликабельности.
     *
     * @param element элемент для клика
     */
    public void clickWhenReady(WebElement element) {
        waitForClickable(element);
        element.click();
    }

    /**
     * Ожидает, пока текущий URL не будет содержать заданный фрагмент.
     *
     * @param urlFragment подстрока, которая должна присутствовать в URL
     */
    public void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    /**
     * Ожидает появления алерта, возвращает его текст и принимает алерт.
     *
     * @return текст алерта
     */
    public String waitForAlertAndAccept() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }

    /**
     * Приостанавливает выполнение на заданное количество миллисекунд
     * с помощью механизма {@link WebDriverWait}.
     *
     * @param millis время паузы в миллисекундах
     */
    public void waitForTransactionToProcess(long millis) {
        long start = System.currentTimeMillis();
        new WebDriverWait(driver, Duration.ofMillis(millis), Duration.ofMillis(millis))
                .until(d -> (System.currentTimeMillis() - start) >= millis);
    }

}
