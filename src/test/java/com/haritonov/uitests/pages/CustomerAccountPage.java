package com.haritonov.uitests.pages;

import com.haritonov.uitests.helpers.ParameterProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CustomerAccountPage extends BasePage{

    @FindBy(xpath = "//strong[contains(text(),'Welcome')]")
    private WebElement welcomeMessage;

    @FindBy(xpath = "//button[@ng-click='deposit()']")
    private WebElement depositButton;

    @FindBy(xpath = "//button[@ng-click='transactions()']")
    private WebElement transactionsButton;

    @FindBy(xpath = "//input[@type='number']")
    private WebElement amountInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement depositSubmitButton;

    @FindBy(xpath = "//span[contains(text(),'Deposit Successful')]")
    private WebElement depositSuccessMessage;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped']/tbody/tr")
    private List<WebElement> transactionRows;

    @FindBy(xpath = "//button[@ng-click='back()']")
    private WebElement backButton;

    @FindBy(xpath = "//button[@ng-click='withdrawl()']")
    private WebElement withdrawlButton;

    @FindBy(xpath = "//span[contains(text(),'Transaction successful')]")
    private WebElement withdrawlSuccessMessage;

    @FindBy(xpath = "(//div[@class='center']/strong)[2]")
    private WebElement balanceValue;

    @FindBy(xpath = "//span[contains(text(), 'Transaction Failed')]")
    private WebElement withdrawalErrorMessage;

    private static final By TRANSACTION_ROWS = By.xpath("//table[@class='table table-bordered table-striped']/tbody/tr");

    public CustomerAccountPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(welcomeMessage);
        return welcomeMessage.isDisplayed();
    }

    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }

    public CustomerAccountPage clickDepositButton() {
        click(depositButton);
        return this;
    }

    public CustomerAccountPage enterAmount(String amount) {
        waiter.waitForVisibility(amountInput);
        amountInput.clear();
        amountInput.sendKeys(amount);
        return this;
    }

    public CustomerAccountPage submitTransaction() {
        click(depositSubmitButton);
        return this;
    }

    public boolean isDepositSuccessMessageVisible() {
        try {
            waiter.waitForVisibility(depositSuccessMessage);
            waiter.waitForTransactionToProcess(Long.parseLong(ParameterProvider.get("banking.customer.transaction.pause.millis")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public CustomerAccountPage goToTransactions() {
        click(transactionsButton);
        waiter.waitForVisibility(backButton);                                 // вкладка загрузилась
        return this;
    }

    public CustomerAccountPage goBackToAccount() {
        click(backButton);
        return this;
    }

    public CustomerAccountPage depositAmount(String amount) {
        clickDepositButton()
                .enterAmount(amount)
                .submitTransaction();
        return this;
    }

    public boolean hasTransactionWithAmount(String amount) {
        goToTransactions();
        List<String> amounts = getTransactionAmount();
        boolean found = amounts.contains(amount);
        goBackToAccount();
        return found;
    }

    public List<String> getTransactionAmount() {
        return driver.findElements(TRANSACTION_ROWS).stream()
                .map(row -> row.findElement(By.xpath("./td[2]")).getText())
                .toList();
    }

    public CustomerAccountPage clickWithdrawalButton() {
        click(withdrawlButton);
        return this;
    }

    public boolean isWithdrawalSuccessMessageVisible() {
        try {
            waiter.waitForVisibility(withdrawlSuccessMessage);
            waiter.waitForTransactionToProcess(Integer.parseInt(ParameterProvider.get("banking.customer.transaction.pause.millis")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getBalanceNumeric() {
        String balanceText = getText(balanceValue);
        return Integer.parseInt(balanceText.trim());
    }

    public CustomerAccountPage withdrawlAmount(String amount) {
        clickWithdrawalButton()
                .enterAmount(amount)
                .submitTransaction();
        return this;
    }

    public boolean isWithdrawalErrorMessageVisible() {
        try {
            waiter.waitForVisibility(withdrawalErrorMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
