package com.haritonov.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
}
