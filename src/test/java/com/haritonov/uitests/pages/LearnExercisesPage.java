package com.haritonov.uitests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object для страницы упражнений sql-ex.ru.
 * <p>Содержит форму авторизации (логин/пароль) и метод проверки её видимости.
 */
public class LearnExercisesPage extends BasePage {

    @FindBy(name = "login")
    private WebElement loginField;

    @FindBy(name = "psw")
    private WebElement passwordField;

    @FindBy(name = "subm1")
    private WebElement submitButton;

    @FindBy(name = "frmlogin")
    private WebElement loginForm;

    /**
     * Создаёт LearnExercisesPage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public LearnExercisesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Страница считается загруженной, когда видно поле ввода логина.
     */
    @Override
    @Step("Проверить загрузку страницы упражнений")
    public boolean isPageLoaded() {
        waiter.waitForVisibility(loginField);
        return loginField.isDisplayed();
    }

    /**
     * Вводит логин в поле "login".
     *
     * @param login логин пользователя
     * @return текущая страница для цепочечных вызовов
     */
    @Step("Ввести логин '{login}'")
    public LearnExercisesPage enterLogin(String login) {
        loginField.clear();
        loginField.sendKeys(login);
        return this;
    }

    /**
     * Вводит пароль в поле "psw".
     *
     * @param password пароль пользователя
     * @return текущая страница для цепочечных вызовов
     */
    @Step("Ввести пароль")
    public LearnExercisesPage enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    /**
     * Нажимает кнопку "Вход" для отправки формы.
     *
     * @return текущая страница для цепочечных вызовов
     */
    @Step("Нажать кнопку 'Вход'")
    public LearnExercisesPage clickLogin() {
        click(submitButton);
        return this;
    }

    /**
     * Проверяет, отображается ли форма логина.
     *
     * @return {@code true}, если форма видна, иначе {@code false}
     */
    @Step("Проверить видимость формы логина")
    public boolean isLoginFormDisplayed() {
        try {
            return loginForm.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}