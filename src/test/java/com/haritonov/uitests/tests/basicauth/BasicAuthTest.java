package com.haritonov.uitests.tests.basicauth;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.BasicAuthPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import io.qameta.allure.*;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Basic Authentication")
public class BasicAuthTest extends BaseTest {

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        String username = ParameterProvider.get("basic.auth.username");
        String password = ParameterProvider.get("basic.auth.password");
        ((HasAuthentication) driver).register(
                UsernameAndPassword.of(username, password)
        );
    }

    @Test(description = "Прохождение Basic Authentication и проверка загрузки изображения")
    @Story("Успешная Basic Auth через HasAuthentification")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldAuthenticateAndDisplayImage() {
        driver.get(ParameterProvider.get("basic.auth.url"));
        BasicAuthPage page = new BasicAuthPage(driver);
        Checker.assertTrue(page.isPageLoaded(), "Basic Auth page should be loaded");
        page.clickDisplayImage();
        Checker.assertTrue(page.isImageLoaded(), "Image should be loaded after successful authentification");
    }
}