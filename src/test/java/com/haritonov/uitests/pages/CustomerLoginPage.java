package com.haritonov.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CustomerLoginPage extends BasePage{

    @FindBy(id = "userSelect")
    private WebElement userSelect;

    @FindBy(xpath = "//button[text()='Login']")
    private WebElement loginButton;

    public CustomerLoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(userSelect);
        return userSelect.isDisplayed();
    }

    public CustomerLoginPage selectCustomer(String fullName) {
        waiter.waitForVisibility(userSelect);
        new Select(userSelect).selectByVisibleText(fullName);
        return this;
    }

    public CustomerAccountPage clickLogin() {
        waiter.waitForVisibility(loginButton);
        click(loginButton);
        CustomerAccountPage accountPage = new CustomerAccountPage(driver);
        accountPage.isPageLoaded();
        return accountPage;
    }

    public CustomerAccountPage loginAs(String fullName) {
        selectCustomer(fullName);
        return clickLogin();
    }
}
