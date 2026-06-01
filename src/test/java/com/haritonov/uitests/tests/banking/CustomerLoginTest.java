package com.haritonov.uitests.tests.banking;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.BankManagerPage;
import com.haritonov.uitests.pages.BankingHomePage;
import com.haritonov.uitests.pages.CustomerAccountPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CustomerLoginTest extends BaseTest {

    private CustomerAccountPage accountPage;
    private String customerFullName;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(ParameterProvider.get("banking.url"));
        BankingHomePage bankingHomePage = new BankingHomePage(driver);
        Assert.assertTrue(bankingHomePage.isPageLoaded(), "Banking home page should be loaded");
        BankManagerPage bankManagerPage = bankingHomePage.goToBankManagerPage();
        customerFullName = bankManagerPage.createCustomerWithAccount(
                TestDataGenerator.getRandomFirstName(),
                TestDataGenerator.getRandomLastName(),
                TestDataGenerator.getRandomPostCode(),
                ParameterProvider.get("banking.manager.currency")
        );
        driver.get(ParameterProvider.get("banking.url"));
        bankingHomePage = new BankingHomePage(driver);
        accountPage = bankingHomePage.goToCustomerLogin().loginAs(customerFullName);
    }

    @Test
    public void shouldDisplayWelcomeMessageAfterCustomerLogin() {
        Assert.assertTrue(accountPage.isPageLoaded(), "Customer account page should be loaded");
        String expectedGreeting = "Welcome " + customerFullName;
        Assert.assertTrue(accountPage.getWelcomeMessage().contains(expectedGreeting), "Welcome message should contain the customer full name");

        String amount = TestDataGenerator.getRandomAmount();
        accountPage.depositAmount(amount);
        Assert.assertTrue(accountPage.isDepositSuccessMessageVisible(), "Deposit success message should appear after depositing");
        Assert.assertTrue(accountPage.hasTransactionWithAmount(amount), "Transactions should contain the deposit of " + amount);

        accountPage.depositAmount(ParameterProvider.get("banking.customer.zero.amount"));
        Assert.assertFalse(accountPage.isDepositSuccessMessageVisible(),"Deposit success message should not appear when depositing 0");
        Assert.assertFalse(accountPage.hasTransactionWithAmount(
                ParameterProvider.get("banking.customer.zero.amount")),
                "Transactions should not contain a deposit of 0"
        );

        int balanceBefore = accountPage.getBalanceNumeric();
        Assert.assertTrue(balanceBefore > 0, "Balance should be > 0 before withdrawal");
        String withdrawAmount = TestDataGenerator.getRandomAmount(balanceBefore);
        accountPage.withdrawlAmount(withdrawAmount);
        Assert.assertTrue(accountPage.isWithdrawalSuccessMessageVisible(), "Transaction successful message should be appear after withdrawing " + withdrawAmount);
        Assert.assertTrue(accountPage.hasTransactionWithAmount(withdrawAmount), "Transactions should contain withdrawal of " + withdrawAmount);
    }
}
