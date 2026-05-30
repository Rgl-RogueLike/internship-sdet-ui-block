package com.haritonov.uitests.tests.login;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.LoginPage;
import com.haritonov.uitests.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void fieldsShouldBeVisibleAndLoginButtonDisableWhenEmpty() {
        driver.get(ParameterProvider.get("login.url"));
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page should be loaded");
        Assert.assertTrue(loginPage.isUsernameFieldVisible(), "Username field should be visible");
        Assert.assertTrue(loginPage.isPasswordFieldVisible(), "Password field should be visible");
        Assert.assertTrue(loginPage.isLoginButtonDisabled(), "Login button should be disabled when fields are empty");
    }

    @Test
    public void shouldLoginSuccessfullyWithValidCredentials() {
        driver.get(ParameterProvider.get("login.url"));
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(ParameterProvider.get("login.username"))
                .enterPassword(ParameterProvider.get("login.password"))
                .enterUsernameDescription(ParameterProvider.get("login.username.description"));

        Assert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled after filling required fields");
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isSuccessMessageVisible(), "Success message should be visible after login");
        String expectedMessage = ParameterProvider.get("login.success.message");
        String actualMessage = loginPage.getSuccessMessageText();
        Assert.assertEquals(expectedMessage, actualMessage, "Success message should be " + expectedMessage);
    }
}
