package com.haritonov.uitests.pages;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.utils.CookieUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;

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

    private final String cookiesFilePath = ParameterProvider.get("cookies.file.path");
    private final String learnUrl = ParameterProvider.get("sql.ex.learn.url");


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

    /**
     * Авторизует пользователя.
     * Сначала пробует войти через cookies, если не выходит — через логин/пароль.
     *
     * @return {@code true}, если авторизация успешна
     */
    @Step("Выполнить авторизацию")
    public boolean loginUsingCookiesOrCredentials() {
        driver.get(learnUrl);
        if (tryLoginWithCookies()) {
            return true;
        }
        loginWithCredentials();
        return !isLoginFormDisplayed();
    }

    /**
     * Пытается авторизоваться, используя сохранённые cookies.
     *
     * @return {@code true}, если cookies сработали
     */
    @Step("Попытаться войти через cookies")
    private boolean tryLoginWithCookies() {
        File cookieFile = new File(cookiesFilePath);
        if (!cookieFile.exists()) {
            return false;
        }
        driver.manage().deleteAllCookies();
        CookieUtils.loadCookiesFromFile(driver, cookiesFilePath);
        driver.get(learnUrl);
        return !isLoginFormDisplayed();
    }

    /**
     * Выполняет вход через форму логина и пароля, затем сохраняет cookies.
     */
    @Step("Войти через форму логина")
    private void loginWithCredentials() {
        waiter.waitForVisibility(loginField);
        enterLogin(ParameterProvider.get("sql.ex.username"))
                .enterPassword(ParameterProvider.get("sql.ex.password"))
                .clickLogin();
        CookieUtils.saveCookiesToFile(driver.manage().getCookies(), cookiesFilePath);
    }
}