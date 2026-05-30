package com.haritonov.uitests.tests.login;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.LoginPage;
import com.haritonov.uitests.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(ParameterProvider.get("login.url"));
        loginPage = new LoginPage(driver);
    }

    @Test
    public void fieldsShouldBeVisibleAndLoginButtonDisableWhenEmpty() {
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page should be loaded");
        Assert.assertTrue(loginPage.isUsernameFieldVisible(), "Username field should be visible");
        Assert.assertTrue(loginPage.isPasswordFieldVisible(), "Password field should be visible");
        Assert.assertTrue(loginPage.isLoginButtonDisabled(), "Login button should be disabled when fields are empty");
    }

    @Test
    public void shouldLoginSuccessfullyWithValidCredentials() {
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

    @Test
    public void shouldDisplayErrorWhenUsingInvalidCredentials() {
        loginPage.enterUsername(ParameterProvider.get("login.invalid.username"))
                .enterPassword(ParameterProvider.get("login.invalid.password"))
                .enterUsernameDescription(ParameterProvider.get("login.username.description"));

        Assert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled after filling required fields");
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isErrorMessageVisible(), "Error message should apper for invalid credentials");
        String expectedMessage = ParameterProvider.get("login.error.message");
        String actualMessage = loginPage.getErrorMessageText();
        Assert.assertEquals(expectedMessage, actualMessage, "Error message should be " + expectedMessage);
    }

    @Test
    public void shouldLogoutSuccessfullyAndReturnToLoginPage() {
        loginPage.enterUsername(ParameterProvider.get("login.username"))
                .enterPassword(ParameterProvider.get("login.password"))
                .enterUsernameDescription(ParameterProvider.get("login.username.description"));

        Assert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled before logging in");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isSuccessMessageVisible(), "Should be logged in successfully before logout");
        loginPage.clickLogout();

        Assert.assertTrue(loginPage.isPageLoaded(), "Login page should be loaded after logout");
        Assert.assertTrue(loginPage.isUsernameFieldVisible(), "Username field should be visible after logout");
        Assert.assertTrue(loginPage.isPasswordFieldVisible(), "Password field should be visible after logout");
    }
}
