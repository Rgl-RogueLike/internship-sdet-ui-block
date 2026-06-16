package com.haritonov.uitests.tests.alerts;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.AlertPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import com.haritonov.uitests.utils.TestDataGenerator;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Alerts")
@Feature("Работа с Alert")
public class AlertTest extends BaseTest {

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test(description = "Вызов и подтверждение Simple Alert и Input Alert")
    @Story("Подтверждение Simple Alert и ввод текста в Input Alert")
    @Severity(SeverityLevel.NORMAL)
    public void shouldHandleSimpleAndInputAlerts() {
        driver.get(ParameterProvider.get("alert.url"));
        AlertPage page = new AlertPage(driver);
        Checker.assertTrue(page.isPageLoaded(), "Alert page should be loaded");
        page.switchToSimpleAlertFrame();
        page.clickSimpleAlertButton();
        String alertText = page.getSimpleAlertText();
        Checker.assertFalse(alertText.isEmpty(), "Simple Alert text should not be empty");
        page.acceptSimpleAlert();
        driver.switchTo().defaultContent();
        page.clickInputAlertTab();
        page.switchToInputAlertFrame();
        page.clickInputAlertButton();
        String customText = TestDataGenerator.getRandomFirstName() + " " + TestDataGenerator.getRandomLastName();
        page.enterTextInAlertAndAccept(customText);
        String resultText = page.getResultText();
        Checker.assertTextContains(resultText, customText, "Result text should the entered text");
    }
}
