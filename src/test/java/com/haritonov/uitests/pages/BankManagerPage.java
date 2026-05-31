package com.haritonov.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class BankManagerPage extends BasePage {

    @FindBy(xpath = "//button[@ng-click='addCust()']")
    private WebElement addCustomerButton;

    @FindBy(xpath = "//input[@ng-model='fName']")
    private WebElement firstNameField;

    @FindBy(xpath = "//input[@ng-model='lName']")
    private WebElement lastNameField;

    @FindBy(xpath = "//input[@ng-model='postCd']")
    private WebElement postCodeField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement addCustomerSubmitButton;

    @FindBy(xpath = "//button[@ng-click='openAccount()']")
    private WebElement openCustomerButton;

    @FindBy(id = "userSelect")
    private WebElement customerSelect;

    @FindBy(id = "currency")
    private WebElement currencySelect;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement processButton;

    public BankManagerPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(addCustomerButton);
        return addCustomerButton.isDisplayed();
    }

    public BankManagerPage clickAddCustomerButton() {
        click(addCustomerButton);
        return this;
    }

    public BankManagerPage enterFirstName(String firstName) {
        waiter.waitForVisibility(firstNameField);
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        return this;
    }

    public BankManagerPage enterLastName(String lastName) {
        waiter.waitForVisibility(lastNameField);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        return this;
    }

    public BankManagerPage enterPostCode(String postCode) {
        waiter.waitForVisibility(postCodeField);
        postCodeField.clear();
        postCodeField.sendKeys(postCode);
        return this;
    }

    public BankManagerPage submitAddCustomer() {
        click(addCustomerSubmitButton);
        return this;
    }

    public String acceptAlertAndGetText() {
        return waiter.waitForAlertAndAccept();
    }

    public BankManagerPage clickOpenCustomerButton() {
        click(openCustomerButton);
        waiter.waitForVisibility(customerSelect);
        return this;
    }

    public BankManagerPage selectLastCustomer() {
        Select select = new Select(customerSelect);
        List<WebElement> options = select.getOptions();
        WebElement lastOption = options.get(options.size() - 1);
        lastOption.click();
        return this;
    }

    public BankManagerPage selectCustomerByName(String name) {
        new Select(customerSelect).selectByVisibleText(name);
        return this;
    }

    public BankManagerPage selectCurrency(String currency) {
        new Select(currencySelect).selectByVisibleText(currency);
        return this;
    }

    public BankManagerPage clickProcess() {
        click(processButton);
        return this;
    }

    public String createCustomerWithAccount(String firstName, String lastName, String postCode, String currency) {
        clickAddCustomerButton()
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostCode(postCode)
                .submitAddCustomer();
        acceptAlertAndGetText();

        clickOpenCustomerButton()
                .selectCustomerByName(firstName + " " + lastName)
                .selectCurrency(currency)
                .clickProcess();
        acceptAlertAndGetText();
        return firstName + " " + lastName;
    }
}
