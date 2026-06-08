package com.haritonov.uitests.tests.cookies;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.LearnExercisesPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import com.haritonov.uitests.utils.CookieUtils;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

@Epic("Авторизация")
@Feature("Работа с cookies")
public class CookieTest extends BaseTest {

    private final String cookiesFilePath = ParameterProvider.get("cookies.file.path");
    private final String sqlExUrl = ParameterProvider.get("sql.ex.home.url");
    private final String learnUrl = ParameterProvider.get("sql.ex.learn.url");

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(sqlExUrl);
    }

    @Test(description = "Первый запуск: успешная авторизация на странице упражнений и сохранение cookies")
    @Story("Сохранение cookies после логина")
    @Severity(SeverityLevel.CRITICAL)
    public void loginAndSaveCookies() {
        CookieUtils.deleteFile(cookiesFilePath);
        driver.get(learnUrl);
        LearnExercisesPage exercisesPage = new LearnExercisesPage(driver);
        exercisesPage.enterLogin(ParameterProvider.get("sql.ex.username"))
                .enterPassword(ParameterProvider.get("sql.ex.password"))
                .clickLogin();

        Checker.assertFalse(exercisesPage.isLoginFormDisplayed(),
                "Login form should not be displayed after authorization");
        CookieUtils.saveCookiesToFile(driver.manage().getCookies(), cookiesFilePath);
    }

    @Test(description = "Второй запуск: пропуск логина через загрузку cookies",
            dependsOnMethods = "loginAndSaveCookies")
    @Story("Загрузка cookies и автоматическая авторизация")
    @Severity(SeverityLevel.CRITICAL)
    public void loadCookiesAndSkipLogin() {
        File cookieFile = new File(cookiesFilePath);
        Checker.assertTrue(cookieFile.exists(), "Cookies file should exist");
        driver.manage().deleteAllCookies();
        CookieUtils.loadCookiesFromFile(driver, cookiesFilePath);
        driver.get(learnUrl);
        LearnExercisesPage exercisesPage = new LearnExercisesPage(driver);
        Checker.assertFalse(exercisesPage.isLoginFormDisplayed(),
                "Login form should not be displayed after loading cookies");
    }

}