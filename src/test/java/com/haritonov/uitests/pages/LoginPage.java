package com.haritonov.uitests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object для страницы авторизации
 * ({@code https://www.way2automation.com/angularjs-protractor/registeration/#/login}).
 * Содержит элементы и методы для работы с формой логина.
 */
public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = ".btn.btn-danger")
    private WebElement loginButton;

    @FindBy(xpath = "//p[contains(text(),\"You're logged in!!\")]")
    private WebElement successMessage;

    @FindBy(id = "formly_1_input_username_0")
    private WebElement usernameDescriptionField;

    @FindBy(xpath = "//div[contains(text(),\"Username or password is incorrect\")]")
    private WebElement errorMessage;

    @FindBy(xpath = "//a[contains(text(),\"Logout\")]")
    private WebElement logoutLink;

    /**
     * Создаёт LoginPage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Страница считается загруженной, когда видно поле Username.
     */
    @Override
    @Step("Проверить загрузку страницы логина")
    public boolean isPageLoaded() {
        waiter.waitForVisibility(usernameField);
        return usernameField.isDisplayed();
    }

    /**
     * @return {@code true}, если поле Username отображается
     */
    @Step("Проверить видимость поля Username")
    public boolean isUsernameFieldVisible() {
        waiter.waitForVisibility(usernameField);
        return usernameField.isDisplayed();
    }

    /**
     * @return {@code true}, если поле Password отображается
     */
    @Step("Проверить видимость поля Password")
    public boolean isPasswordFieldVisible() {
        waiter.waitForVisibility(passwordField);
        return passwordField.isDisplayed();
    }

    /**
     * @return {@code true}, если кнопка Login отключена
     */
    @Step("Проверить, что кнопка Login отключена")
    public boolean isLoginButtonDisabled() {
        waiter.waitForVisibility(loginButton);
        return !loginButton.isEnabled();
    }

    /**
     * @return {@code true}, если кнопка Login активна
     */
    @Step("Проверить, что кнопка Login активна")
    public boolean isLoginButtonEnabled() {
        waiter.waitForVisibility(loginButton);
        return loginButton.isEnabled();
    }

    /**
     * Вводит имя пользователя в поле Username.
     *
     * @param username имя пользователя
     * @return текущая страница
     */
    @Step("Ввести имя пользователя '{username}'")
    public LoginPage enterUsername(String username) {
        waiter.waitForVisibility(usernameField);
        usernameField.clear();
        usernameField.sendKeys(username);
        return this;
    }

    /**
     * Вводит пароль в поле Password.
     *
     * @param password пароль
     * @return текущая страница
     */
    @Step("Ввести пароль")
    public LoginPage enterPassword(String password) {
        waiter.waitForVisibility(passwordField);
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    /**
     * Кликает по кнопке Login.
     *
     * @return текущая страница
     */
    @Step("Кликнуть кнопку Login")
    public LoginPage clickLoginButton() {
        click(loginButton);
        return this;
    }

    /**
     * @return {@code true}, если сообщение об успешном входе отображается
     */
    @Step("Проверить видимость сообщения об успешном входе")
    public boolean isSuccessMessageVisible() {
        waiter.waitForVisibility(successMessage);
        return successMessage.isDisplayed();
    }

    /**
     * @return текст сообщения об успешном входе
     */
    @Step("Получить текст сообщения об успешном входе")
    public String getSuccessMessageText() {
        waiter.waitForVisibility(successMessage);
        return successMessage.getText();
    }

    /**
     * Вводит текст в дополнительное поле Username*.
     *
     * @param description описание / значение
     * @return текущая страница
     */
    @Step("Ввести значение в поле Username*")
    public LoginPage enterUsernameDescription(String description) {
        waiter.waitForVisibility(usernameDescriptionField);
        usernameDescriptionField.clear();
        usernameDescriptionField.sendKeys(description);
        return this;
    }

    /**
     * @return {@code true}, если сообщение об ошибке отображается
     */
    @Step("Проверить видимость сообщения об ошибке")
    public boolean isErrorMessageVisible() {
        waiter.waitForVisibility(errorMessage);
        return errorMessage.isDisplayed();
    }

    /**
     * @return текст сообщения об ошибке
     */
    @Step("Получить текст сообщения об ошибке")
    public String getErrorMessageText() {
        waiter.waitForVisibility(errorMessage);
        return errorMessage.getText();
    }

    /**
     * Кликает по ссылке Logout.
     *
     * @return текущая страница
     */
    @Step("Кликнуть ссылку Logout")
    public LoginPage clickLogout() {
        click(logoutLink);
        return this;
    }

    /**
     * Возвращает веб-элемент поля Username, ожидая его видимости.
     *
     * @return поле ввода Username
     */
    @Step("Получить поле Username")
    public WebElement getUsernameField() {
        waiter.waitForVisibility(usernameField);
        return usernameField;
    }

}
