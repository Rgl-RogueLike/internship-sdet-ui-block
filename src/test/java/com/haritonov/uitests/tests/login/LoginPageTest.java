package com.haritonov.uitests.tests.login;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.LoginPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Тесты страницы авторизации.
 * <p>Проверяет состояние полей, успешный вход с валидными данными,
 * ошибку при невалидных данных и выход из системы.
 */
@Epic("Авторизация")
@Feature("Форма логина")
public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;

    /**
     * Перед каждым тестом открывает страницу авторизации и создаёт LoginPage.
     */
    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(ParameterProvider.get("login.url"));
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Логин: поля Username и Password видны, кнопка Login заблокирована при пустых полях")
    @Story("Проверка полей ввода")
    @Severity(SeverityLevel.BLOCKER)
    public void fieldsShouldBeVisibleAndLoginButtonDisableWhenEmpty() {
        Checker.assertTrue(loginPage.isPageLoaded(), "Login page should be loaded");
        Checker.assertTrue(loginPage.isUsernameFieldVisible(), "Username field should be visible");
        Checker.assertTrue(loginPage.isPasswordFieldVisible(), "Password field should be visible");
        Checker.assertTrue(loginPage.isLoginButtonDisabled(), "Login button should be disabled when fields are empty");
    }

    @Test(description = "Логин: успешная авторизация с валидными данными (angular/password)")
    @Story("Успешная авторизация")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldLoginSuccessfullyWithValidCredentials() {
        loginPage.enterUsername(ParameterProvider.get("login.username"))
                .enterPassword(ParameterProvider.get("login.password"))
                .enterUsernameDescription(ParameterProvider.get("login.username.description"));

        Checker.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled after filling required fields");
        loginPage.clickLoginButton();

        Checker.assertTrue(loginPage.isSuccessMessageVisible(), "Success message should be visible after login");
        String expectedMessage = ParameterProvider.get("login.success.message");
        String actualMessage = loginPage.getSuccessMessageText();
        Checker.assertEquals(expectedMessage, actualMessage, "Success message should be " + expectedMessage);
    }

    @Test(description = "Логин: ошибка авторизации при неверных данных, сообщение 'Username or password is incorrect'")
    @Story("Невалидная авторизация")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldDisplayErrorWhenUsingInvalidCredentials() {
        loginPage.enterUsername(ParameterProvider.get("login.invalid.username"))
                .enterPassword(ParameterProvider.get("login.invalid.password"))
                .enterUsernameDescription(ParameterProvider.get("login.username.description"));

        Checker.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled after filling required fields");
        loginPage.clickLoginButton();

        Checker.assertTrue(loginPage.isErrorMessageVisible(), "Error message should apper for invalid credentials");
        String expectedMessage = ParameterProvider.get("login.error.message");
        String actualMessage = loginPage.getErrorMessageText();
        Checker.assertEquals(expectedMessage, actualMessage, "Error message should be " + expectedMessage);
    }

    @Test(description = "Логин: после выхода из системы отображается форма входа")
    @Story("Выход из системы")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldLogoutSuccessfullyAndReturnToLoginPage() {
        loginPage.enterUsername(ParameterProvider.get("login.username"))
                .enterPassword(ParameterProvider.get("login.password"))
                .enterUsernameDescription(ParameterProvider.get("login.username.description"));

        Checker.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled before logging in");
        loginPage.clickLoginButton();
        Checker.assertTrue(loginPage.isSuccessMessageVisible(), "Should be logged in successfully before logout");
        loginPage.clickLogout();

        Checker.assertTrue(loginPage.isPageLoaded(), "Login page should be loaded after logout");
        Checker.assertTrue(loginPage.isUsernameFieldVisible(), "Username field should be visible after logout");
        Checker.assertTrue(loginPage.isPasswordFieldVisible(), "Password field should be visible after logout");
    }
}
