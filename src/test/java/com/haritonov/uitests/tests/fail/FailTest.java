package com.haritonov.uitests.tests.fail;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.LoginPage;
import com.haritonov.uitests.pages.banking.BankManagerPage;
import com.haritonov.uitests.pages.banking.BankingHomePage;
import com.haritonov.uitests.pages.banking.CustomerAccountPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import com.haritonov.uitests.utils.TestDataGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Демонстрация падения")
@Feature("Скриншоты при ошибках")
public class FailTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        driver.get(ParameterProvider.get("login.url"));
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Падающий тест: всегда Assert.fail")
    @Story("Генерация скриншота при Assert.fail")
    @Severity(SeverityLevel.MINOR)
    public void intentionallyFailingTest() {
        Assert.fail("Этот тест всегда падает для демонстрации снятия скриншота");
    }

    @Test(description = "Падающий тест: попытка входа без заполнения Username*")
    @Story("Генерация скриншотов при ошибке авторизации")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithoutUsernameDescriptionShouldFail() {
        loginPage.enterUsername(ParameterProvider.get("login.username"))
                .enterPassword(ParameterProvider.get("login.password"));
        Checker.assertTrue(loginPage.isLoginButtonEnabled(),
                "Login button should be active");
        loginPage.clickLoginButton();
        Checker.assertTrue(loginPage.isSuccessMessageVisible(),
                "Success message should be visible");
    }

    @Test(description = "Падающий тест: клиент без счёта, кнопка депозита недоступна")
    @Story("Генерация скриншота при отсутствии счёта")
    @Severity(SeverityLevel.NORMAL)
    public void customerWithoutAccountShouldNotSeeDepositButton() {
        driver.get(ParameterProvider.get("banking.url"));
        BankingHomePage home = new BankingHomePage(driver);
        BankManagerPage manager = home.goToBankManagerPage();

        String firstName = TestDataGenerator.getRandomFirstName();
        String lastName = TestDataGenerator.getRandomLastName();
        String fullName = firstName + " " + lastName;

        manager.clickAddCustomerButton()
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostCode(TestDataGenerator.getRandomPostCode())
                .submitAddCustomer();
        manager.acceptAlertAndGetText();

        driver.get(ParameterProvider.get("banking.url"));
        home = new BankingHomePage(driver);
        CustomerAccountPage account = home.goToCustomerLogin().loginAs(fullName);

        Checker.assertTrue(account.isDepositButtonVisible(),
                "Deposit button should be visible, but it's not because account was not opened");
    }

}