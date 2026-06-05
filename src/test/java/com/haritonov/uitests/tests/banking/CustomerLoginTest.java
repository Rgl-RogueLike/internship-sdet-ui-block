package com.haritonov.uitests.tests.banking;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.banking.BankManagerPage;
import com.haritonov.uitests.pages.banking.BankingHomePage;
import com.haritonov.uitests.pages.banking.CustomerAccountPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import com.haritonov.uitests.utils.TestDataGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Тест полного жизненного цикла клиента.
 * <p>Один тест, который последовательно проверяет:
 * вход, пополнение (успешное и неуспешное), снятие (успешное и неуспешное),
 * сверку баланса, снятие всех средств и очистку истории транзакций.
 */
public class CustomerLoginTest extends BaseTest {

    private CustomerAccountPage accountPage;
    private String customerFullName;

    /**
     * Предусловие для теста:
     * Открывает домашнюю страницу банка;
     * Через Bank Manager создаёт нового клиента со счётом в указанной валюте;
     * Возвращается на домашнюю страницу и выполняет вход под созданным клиентом.
     */
    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(ParameterProvider.get("banking.url"));
        BankingHomePage bankingHomePage = new BankingHomePage(driver);
        Checker.assertTrue(bankingHomePage.isPageLoaded(), "Banking home page should be loaded");
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

    @Test(description = "Жизненный цикл клиента: вход, успешное/неуспешное пополнение и снятие, сверка баланса, " +
            "снятие всех средств, очистка истории и финальный нулевой баланс")
    public void shouldPerformFulAccountLifecycleAndEndWithZeroBalance() {
        Checker.assertTrue(accountPage.isPageLoaded(), "Customer account page should be loaded");
        String expectedGreeting = "Welcome " + customerFullName;
        Checker.assertTextContains(accountPage.getWelcomeMessage(), expectedGreeting, "Welcome message should contain the customer full name");

        String amount = TestDataGenerator.getRandomAmount();
        accountPage.depositAmount(amount);
        Checker.assertTrue(accountPage.isDepositSuccessMessageVisible(), "Deposit success message should appear after depositing");
        Checker.assertTransactionPresent(accountPage, amount, "Transactions should contain the deposit of " + amount);

        accountPage.depositAmount(ParameterProvider.get("banking.customer.zero.amount"));
        Checker.assertFalse(accountPage.isDepositSuccessMessageVisible(), "Deposit success message should not appear when depositing 0");
        Checker.assertTransactionNotPresent(accountPage,
                ParameterProvider.get("banking.customer.zero.amount"),
                "Transactions should not contain a deposit of 0");

        int balanceBefore = accountPage.getBalanceNumeric();
        Checker.assertPositive(balanceBefore, "Balance should be > 0 before withdrawal");
        String withdrawAmount = TestDataGenerator.getRandomAmount(balanceBefore);
        accountPage.withdrawlAmount(withdrawAmount);
        Checker.assertTrue(accountPage.isWithdrawalSuccessMessageVisible(), "Transaction successful message should be appear after withdrawing " + withdrawAmount);
        Checker.assertTransactionPresent(accountPage, withdrawAmount,
                "Transactions should contain withdrawal of " + withdrawAmount);

        balanceBefore = accountPage.getBalanceNumeric();
        String balanceBeforeStr = String.valueOf((balanceBefore + 1));
        accountPage.withdrawlAmount(balanceBeforeStr);
        Checker.assertTrue(accountPage.isWithdrawalErrorMessageVisible(), "Error message should appear when withdrawing more than balance");
        Checker.assertTransactionNotPresent(accountPage,
                balanceBeforeStr,
                "Transactions should not contain a withdrawal of " + balanceBeforeStr);

        int displayBalance = accountPage.getBalanceNumeric();
        int calculatedBalance = accountPage.calculateBalanceFromTransactions();
        Checker.assertEquals(displayBalance, calculatedBalance, "Displayed balance should equals the sum of transactions");

        int remainingBalance = accountPage.getBalanceNumeric();
        Checker.assertPositive(remainingBalance, "Balance should be > 0 before final withdrawal");
        accountPage.withdrawlAmount(String.valueOf(remainingBalance));
        Checker.assertTrue(accountPage.isWithdrawalSuccessMessageVisible(), "Transaction successful message should appear after withdrawing remaining balance");
        int balanceAfterWithdrawAll = accountPage.getBalanceNumeric();
        Checker.assertEquals(balanceAfterWithdrawAll, 0, "Balance should be 0 after withdrawing all funds");

        accountPage.goToTransactions();
        int transactionsBeforeReset = accountPage.getTransactionCount();
        Checker.assertPositive(transactionsBeforeReset, "Should have transactions before reset");
        accountPage.clickResetButton();
        int transactionsAfterReset = accountPage.getTransactionCount();
        Checker.assertEquals(0, transactionsAfterReset, "Transaction history should be empty after reset");
        accountPage.goBackToAccount();
        int finalBalance = accountPage.getBalanceNumeric();
        Checker.assertEquals(0, finalBalance, "Balance should remain 0 after transaction reset");
    }
}
