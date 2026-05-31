package com.haritonov.uitests.pages;

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
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public CustomerAccountPage goToTransactions() {
        click(transactionsButton);
        if (!transactionRows.isEmpty()) {
            waiter.waitForVisibility(transactionRows.get(0));
        }
        return this;
    }

    public List<String> getTransactionAmount() {
        return transactionRows.stream()
                .map(row -> row.findElement(By.xpath("./td[2]")).getText())
                .toList();
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
        List<String> amounts = getTransactionAmount();
        boolean found = amounts.contains(amount);
        goBackToAccount();
        return found;
    }

    public CustomerAccountPage refreshTransactionList() {
        goToTransactions()
                .goBackToAccount()
                .goToTransactions();
        return this;
    }
}
