package com.haritonov.uitests.pages.banking;

import com.haritonov.uitests.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Page Object, представляющий страницу входа клиента (выбор имени из выпадающего списка и кнопка Login).
 */
public class CustomerLoginPage extends BasePage {

    @FindBy(id = "userSelect")
    private WebElement userSelect;

    @FindBy(xpath = "//button[text()='Login']")
    private WebElement loginButton;

    /**
     * Создаёт CustomerLoginPage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public CustomerLoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * @return true, если выпадающий список виден.
     */
    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(userSelect);
        return userSelect.isDisplayed();
    }

    /**
     * Выбирает клиента по полному имени.
     *
     * @param fullName имя + фамилия
     */
    public CustomerLoginPage selectCustomer(String fullName) {
        waiter.waitForVisibility(userSelect);
        new Select(userSelect).selectByVisibleText(fullName);
        return this;
    }

    /**
     * Нажимает кнопку Login и возвращает страницу аккаунта.
     *
     * @return новый объект {@link CustomerAccountPage}
     */
    public CustomerAccountPage clickLogin() {
        waiter.waitForVisibility(loginButton);
        click(loginButton);
        CustomerAccountPage accountPage = new CustomerAccountPage(driver);
        accountPage.isPageLoaded();
        return accountPage;
    }

    /**
     * Быстрый вход: выбирает клиента и сразу нажимает Login.
     *
     * @param fullName полное имя
     * @return {@link CustomerAccountPage}
     */
    public CustomerAccountPage loginAs(String fullName) {
        selectCustomer(fullName);
        return clickLogin();
    }
}
