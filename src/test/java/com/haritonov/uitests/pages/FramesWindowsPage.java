package com.haritonov.uitests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Set;

/**
 * Page Object для страницы Frames and Windows.
 * <p>Содержит ссылку внутри iframe, открывающую новую вкладку,
 * и методы для переключения между вкладками.
 */
public class FramesWindowsPage extends BasePage {

    @FindBy(css = ".demo-frame")
    private WebElement demoFrame;

    @FindBy(xpath = "//a[contains(text(),'New Browser Tab')]")
    private WebElement openNewTabLink;

    /**
     * Создаёт FramesWindowsPage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public FramesWindowsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Страница считается загруженной, когда виден iframe c содержимым.
     */
    @Override
    @Step("Проверить загрузку страницы Frames and Windows")
    public boolean isPageLoaded() {
        waiter.waitForVisibility(demoFrame);
        return demoFrame.isDisplayed();
    }

    /**
     * Переключается в iframe, содержащий ссылки для открытия вкладок.
     */
    @Step("Переключиться в iframe со ссылками")
    public void switchToFrame() {
        driver.switchTo().frame(demoFrame);
    }

    /**
     * Кликает по ссылке "New Browser Tab" внутри iframe.
     */
    @Step("Нажать ссылку 'New Browser Tab'")
    public void clickNewBrowserTabLink() {
        click(openNewTabLink);
    }

    /**
     * Переключает фокус на последнюю открытую вкладку.
     */
    @Step("Переключиться на новую вкладку")
    public void switchToNewTab() {
        Set<String> windowHandles = driver.getWindowHandles();
        String lastHandle = (String) windowHandles.toArray()[windowHandles.size() - 1];
        driver.switchTo().window(lastHandle);
    }

    /**
     * @return количество открытых вкладок
     */
    @Step("Получить количество открытых вкладок")
    public int getWindowCount() {
        return driver.getWindowHandles().size();
    }

    /**
     * Открывает новую вкладку и сразу переключается на неё.
     * Предполагается, что мы уже находимся внутри iframe.
     */
    @Step("Открыть новую вкладку и переключиться на неё")
    public void openNewTabAndSwitch() {
        clickNewBrowserTabLink();
        switchToNewTab();
    }

    /**
     * Нажимает ссылку "New Browser Tab" на текущей странице.
     */
    @Step("Нажать ссылку 'New Browser Tab' на текущей странице")
    public void clickNewBrowserTabLinkOnCurrentPage() {
        new FramesWindowsPage(driver).clickNewBrowserTabLink();
    }
}