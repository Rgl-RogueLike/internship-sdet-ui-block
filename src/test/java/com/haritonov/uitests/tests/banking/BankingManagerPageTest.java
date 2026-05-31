package com.haritonov.uitests.tests.banking;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.BankManagerPage;
import com.haritonov.uitests.pages.BankingHomePage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BankingManagerPageTest extends BaseTest {

    BankManagerPage managerPage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(ParameterProvider.get("banking.url"));
        BankingHomePage bankingHomePage = new BankingHomePage(driver);
        bankingHomePage.isPageLoaded();
        managerPage = bankingHomePage.goToBankManagerPage();
    }

    @Test
    public void shouldDisplaySuccessAlertWhenAddingCustomerWithValidData() {
        managerPage.clickAddCustomerButton()
                .enterFirstName(TestDataGenerator.getRandomFirstName())
                .enterLastName(TestDataGenerator.getRandomLastName())
                .enterPostCode(TestDataGenerator.getRandomPostCode())
                .submitAddCustomer();

        String expectedMessage = ParameterProvider.get("banking.manager.success.message");
        String actualMessage = managerPage.acceptAlertAndGetText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Alert should confirm customer addition, but was: " + actualMessage);
    }

    @Test
    public void shouldDisplayAlertWhenOpeningAccountForLastCreatedCustomer() {
        managerPage.clickOpenCustomerButton()
                .selectLastCustomer()
                .selectCurrency(ParameterProvider.get("banking.manager.currency"))
                .clickProcess();

        String expectedMessage = ParameterProvider.get("banking.manager.account.success.message");
        String actualMessage = managerPage.acceptAlertAndGetText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Alert should confirm account, but was: " + actualMessage);
    }
}
