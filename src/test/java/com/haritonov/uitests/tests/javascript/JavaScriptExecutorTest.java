package com.haritonov.uitests.tests.javascript;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.HomePage;
import com.haritonov.uitests.pages.LoginPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import com.haritonov.uitests.utils.JavaScriptUtils;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("JavaScript Executor")
@Feature("Работа с JS")
public class JavaScriptExecutorTest extends BaseTest {

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test(description = "Проверить наличие вертикального скролла на главной странице")
    @Story("Проверка наличия скролла")
    @Severity(SeverityLevel.NORMAL)
    public void shouldDetectVerticalScrollOnHomePage() {
        HomePage homePage = new HomePage(driver);
        Checker.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        boolean hasScroll = JavaScriptUtils.isVerticalScrollPresent(driver);
        Checker.assertTrue(hasScroll, "Page should have vertical scroll");
    }

    @Test(description = "Убрать фокус с поля Username на странице логина")
    @Story("Снятие фокуса с поля ввода")
    @Severity(SeverityLevel.NORMAL)
    public void shouldRemoveFocusFromUsernameFieldOnLoginPage() {
        driver.get(ParameterProvider.get("login.url"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(ParameterProvider.get("login.dataset.valid.username"));
        JavaScriptUtils.removeFocus(driver, loginPage.getUsernameField());
        boolean isFocused = loginPage.getUsernameField().equals(driver.switchTo().activeElement());
        Checker.assertFalse(isFocused, "Username field should not be focused");
    }
}
