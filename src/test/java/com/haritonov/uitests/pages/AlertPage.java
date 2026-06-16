package com.haritonov.uitests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object для страницы Alert.
 * <p>Содержит элементы и методы для работы с Simple Alert и Input Alert,
 * которые находятся в разных iframe.
 */
public class AlertPage extends BasePage {

    @FindBy(xpath = "//a[contains(text(),'Input Alert')]")
    private WebElement inputAlertTab;

    @FindBy(css = ".demo-frame")
    private WebElement demoFrame;

    @FindBy(css = "#example-1-tab-2 .demo-frame")
    private WebElement inputAlertFrame;

    @FindBy(css = "#example-1-tab-1 .demo-frame")
    private WebElement simpleAlertFrame;

    @FindBy(xpath = "//button[contains(text(),'display an alert box')]")
    private WebElement simpleAlertButton;

    @FindBy(xpath = "//button[contains(.,'Input box')]")
    private WebElement inputAlertButton;

    @FindBy(id = "demo")
    private WebElement resultText;

    /**
     * Создаёт AlertPage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public AlertPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Страница считается загруженной, когда виден блок навигации.
     */
    @Override
    @Step("Проверить загрузку страницы с алертами")
    public boolean isPageLoaded() {
        waiter.waitForVisibility(demoFrame);
        return demoFrame.isDisplayed();
    }

    /**
     * Нажимает кнопку для вызова Simple Alert.
     */
    @Step("Нажать кнопку для вызова Simple Alert")
    public void clickSimpleAlertButton() {
        click(simpleAlertButton);
    }

    /**
     * Получает текст Simple Alert до его подтверждения.
     *
     * @return текст сообщения в alert
     */
    @Step("Получить текст Simple Alert")
    public String getSimpleAlertText() {
        return driver.switchTo().alert().getText();
    }

    /**
     * Принимает Simple Alert (нажимает OK).
     */
    @Step("Принять Simple Alert")
    public void acceptSimpleAlert() {
        driver.switchTo().alert().accept();
    }

    /**
     * Переключается на вкладку "Input Alert".
     */
    @Step("Переключиться на вкладку 'Input Alert'")
    public void clickInputAlertTab() {
        click(inputAlertTab);
    }

    /**
     * Переключается в iframe, содержащий Simple Alert.
     */
    @Step("Переключиться в iframe для Simple Alert")
    public void switchToSimpleAlertFrame() {
        driver.switchTo().frame(simpleAlertFrame);
    }

    /**
     * Переключается в iframe, содержащий Input Alert.
     */
    @Step("Переключиться в iframe для Input Alert")
    public void switchToInputAlertFrame() {
        driver.switchTo().frame(inputAlertFrame);
    }

    /**
     * Нажимает кнопку для вызова Input Alert.
     */
    @Step("Нажать кнопку для вызова Input Alert")
    public void clickInputAlertButton() {
        waiter.waitForVisibility(inputAlertButton);
        click(inputAlertButton);
    }

    /**
     * Вводит текст в Input Alert и подтверждает.
     *
     * @param text текст для ввода
     */
    @Step("Ввести текст '{text}' в Input Alert и подтвердить")
    public void enterTextInAlertAndAccept(String text) {
        driver.switchTo().alert().sendKeys(text);
        driver.switchTo().alert().accept();
    }

    /**
     * Возвращает текст результата после взаимодействия с alert.
     *
     * @return текст под кнопкой
     */
    @Step("Получить текст результата")
    public String getResultText() {
        waiter.waitForVisibility(resultText);
        return resultText.getText().trim();
    }
}
