package com.haritonov.uitests.tests.banking;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.banking.BankManagerPage;
import com.haritonov.uitests.pages.banking.BankingHomePage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import com.haritonov.uitests.utils.TestDataGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Тесты интерфейса Bank Manager.
 * <p>Включает создание клиента, открытие счёта для последнего клиента
 * и удаление клиента после создания счёта.
 */
public class BankingManagerPageTest extends BaseTest {

    BankManagerPage managerPage;

    /**
     * Перед каждым тестом открывает страницу работы с клиентами банка и создаёт BankingHomePage.
     */
    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(ParameterProvider.get("banking.url"));
        BankingHomePage bankingHomePage = new BankingHomePage(driver);
        bankingHomePage.isPageLoaded();
        managerPage = bankingHomePage.goToBankManagerPage();
    }

    @Test(description = "Bank Manager: добавление нового клиента – появляется alert об успехе")
    public void shouldDisplaySuccessAlertWhenAddingCustomerWithValidData() {
        managerPage.clickAddCustomerButton()
                .enterFirstName(TestDataGenerator.getRandomFirstName())
                .enterLastName(TestDataGenerator.getRandomLastName())
                .enterPostCode(TestDataGenerator.getRandomPostCode())
                .submitAddCustomer();

        String expectedMessage = ParameterProvider.get("banking.manager.success.message");
        String actualMessage = managerPage.acceptAlertAndGetText();
        Checker.assertTextContains(actualMessage, expectedMessage, "Alert should confirm customer addition, but was: " + actualMessage);
    }

    @Test(description = "Bank Manager: открытие счёта для последнего клиента в списке – alert об успехе")
    public void shouldDisplayAlertWhenOpeningAccountForLastCreatedCustomer() {
        managerPage.clickOpenCustomerButton()
                .selectLastCustomer()
                .selectCurrency(ParameterProvider.get("banking.manager.currency"))
                .clickProcess();

        String expectedMessage = ParameterProvider.get("banking.manager.account.success.message");
        String actualMessage = managerPage.acceptAlertAndGetText();
        Checker.assertTextContains(actualMessage, expectedMessage, "Alert should confirm account, but was: " + actualMessage);
    }

    @Test(description = "Bank Manager: создание клиента со счётом, последующее удаление и проверка отсутствия в таблице")
    public void shouldDeleteCustomerAfterCreatingAccount() {
        String firstName = TestDataGenerator.getRandomFirstName();
        managerPage.createCustomerWithAccount(
                firstName,
                TestDataGenerator.getRandomLastName(),
                TestDataGenerator.getRandomPostCode(),
                ParameterProvider.get("banking.manager.currency")
        );
        managerPage.clickCustomersButton();
        managerPage.searchCustomer(firstName);
        Checker.assertTrue(managerPage.getCustomerCount() > 0, "Customer should be present in the table after search");
        managerPage.deleteFirstCustomerInTable();
        managerPage.clearSearchField();
        List<String> firstNames = managerPage.getCustomerFirstNames();
        Checker.assertFalse(firstNames.contains(firstName), "Deleted customer should not appear in the customer list");
    }
}
