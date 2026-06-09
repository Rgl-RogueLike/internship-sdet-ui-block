package com.haritonov.uitests.tests.cookies;

import com.haritonov.uitests.pages.LearnExercisesPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("Авторизация")
@Feature("Работа с cookies")
public class CookieTest extends BaseTest {

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test(description = "Авторизация на странице упражнений: через cookie или форму логина")
    @Story("Автоматическая авторизация с повторным использованием cookies")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldLoginUsingCookiesOrCredentials() {
        LearnExercisesPage exercisesPage = new LearnExercisesPage(driver);
        Checker.assertTrue(exercisesPage.loginUsingCookiesOrCredentials(),
                "Should be logged in");
    }
}