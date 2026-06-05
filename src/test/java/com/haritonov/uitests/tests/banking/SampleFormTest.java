package com.haritonov.uitests.tests.banking;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.SampleFormPage;
import com.haritonov.uitests.pages.banking.BankingHomePage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import com.haritonov.uitests.utils.TestDataGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Тест Sample Form (регистрационная форма).
 * <p>Проверяет заполнение формы случайными данными, выбор хобби,
 * вставку самого длинного слова из списка хобби и появление сообщения об успехе.
 */
public class SampleFormTest extends BaseTest {

    private BankingHomePage bankingHomePage;

    /**
     * Открывает домашнюю страницу банка и инициализирует {@link BankingHomePage}.
     */
    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(ParameterProvider.get("banking.url"));
        bankingHomePage = new BankingHomePage(driver);
    }

    @Test(description = "Sample Form: заполнение формы случайными данными, выбор хобби Sports, " +
            "ввод самого длинного слова из хобби и проверка сообщения об успешной регистрации")
    public void shouldBeRegisterWithLongestHobbyMessage() {
        Checker.assertTrue(bankingHomePage.isPageLoaded(), "Banking home page should be loaded");
        SampleFormPage sampleFormPage = bankingHomePage.goToSampleFormPage();
        Checker.assertTrue(sampleFormPage.isPageLoaded(), "Sample form page should be loaded");
        String longestHobby = sampleFormPage.getLongestHobbyWord();
        String aboutText = ParameterProvider.get("sample.form.about.text") + longestHobby;

        sampleFormPage.enterFirstName(TestDataGenerator.getRandomFirstName())
                .enterLastname(TestDataGenerator.getRandomLastName())
                .enterEmail(TestDataGenerator.getRandomEmail())
                .enterPassword(TestDataGenerator.getRandomPassword())
                .selectGender(TestDataGenerator.getRandomGender())
                .selectHobby(ParameterProvider.get("sample.form.hobby"))
                .enterAboutYourself(aboutText)
                .clickRegister();

        Checker.assertTrue(sampleFormPage.isSuccessMessageVisible(), "Success message should be visible after registration");
        String expectedMessage = ParameterProvider.get("sample.form.success.message");
        String actualMessage = sampleFormPage.getSuccessMessageText();
        Checker.assertEquals(actualMessage, expectedMessage, "Success message text should match expected");
    }
}
