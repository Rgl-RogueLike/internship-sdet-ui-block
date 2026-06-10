package com.haritonov.uitests.tests.login;

import com.haritonov.uitests.helpers.DataProviders;
import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.LoginPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginDataProviderTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(ParameterProvider.get("login.url"));
        loginPage = new LoginPage(driver);
    }

    @Test(dataProvider = "validLoginCredentials",
        dataProviderClass = DataProviders.class,
        description = "Параметризированный тест успешной авторизации")
    @Story("Авторизация с валидными данными")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginWithDifferentCredentials(String username, String password, String description, String expectedMessageKey) {
        loginPage.enterUsername(username)
                .enterPassword(password)
                .enterUsernameDescription(description);
        Checker.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled after filling the fields");
        loginPage.clickLoginButton();
        String expectedMessage = ParameterProvider.get(expectedMessageKey);

        Checker.assertTrue(loginPage.isSuccessMessageVisible(),
                "Success message should be visible");
        Checker.assertEquals(expectedMessage, loginPage.getSuccessMessageText(),
                "Success message text mismatch");
    }

    @Test(dataProvider = "invalidLoginCredentials",
        dataProviderClass = DataProviders.class,
        description = "Параметризированный тест неуспешной авторизации")
    @Story("Неуспешная авторизация с невалидными данными")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidLogin(String username, String password, String description, String expectedMessageKey) {
        loginPage.enterUsername(username)
                .enterPassword(password)
                .enterUsernameDescription(description);
        Checker.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled after filling the fields");
        loginPage.clickLoginButton();
        String expectedMessage = ParameterProvider.get(expectedMessageKey);
        Checker.assertTrue(loginPage.isErrorMessageVisible(),
                "Error message should be visible");
        Checker.assertEquals(expectedMessage, loginPage.getErrorMessageText(),
                "Error message text mismatch");
    }

}
