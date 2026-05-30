package com.haritonov.uitests.tests.banking;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.BankingHomePage;
import com.haritonov.uitests.pages.SampleFormPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SampleFormTest extends BaseTest {

    private BankingHomePage bankingHomePage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(ParameterProvider.get("banking.url"));
        bankingHomePage = new BankingHomePage(driver);
    }

    @Test
    public void shouldBeRegisterWithLongestHobbyMessage() {
        Assert.assertTrue(bankingHomePage.isPageLoaded());
        SampleFormPage sampleFormPage = bankingHomePage.goToSampleFormPage();
        Assert.assertTrue(sampleFormPage.isPageLoaded(), "Sample form page should be loaded");
        String longestHobby = sampleFormPage.getLongestHobbyWord();
        String aboutText = ParameterProvider.get("sample.form.about.text") + longestHobby;

        sampleFormPage.enterFirstName(TestDataGenerator.getRandomFirstName())
                .enterLastname(TestDataGenerator.getRandomLastName())
                .enterEmail(TestDataGenerator.getRandomEmail())
                .enterPassword(TestDataGenerator.getRandomPassword())
                .selectGender(TestDataGenerator.getRandomGender())
                .selectHobby(ParameterProvider.get("sample.form.hobby"))
                .enterAboutYourself(aboutText);

        sampleFormPage.clickRegister();
        Assert.assertTrue(sampleFormPage.isSuccessMessageVisible(), "Success message should be visible after registration");
        String expectedMessage = ParameterProvider.get("sample.form.success.message");
        String actualMessage = sampleFormPage.getSuccessMessageText();
        Assert.assertEquals(actualMessage, expectedMessage, "Success message text should match expected");
    }
}
