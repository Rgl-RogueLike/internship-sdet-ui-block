package com.haritonov.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = ".btn.btn-danger")
    private WebElement loginButton;

    @FindBy(xpath = "//p[contains(text(),\"You're logged in!!\")]")
    private WebElement successMessage;

    @FindBy(id = "formly_1_input_username_0")
    private WebElement usernameDescriptionField;

    @FindBy(xpath = "//div[contains(text(),\"Username or password is incorrect\")]")
    private WebElement errorMessage;

    @FindBy(xpath = "//a[contains(text(),\"Logout\")]")
    private WebElement logoutLink;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(usernameField);
        return usernameField.isDisplayed();
    }

    public boolean isUsernameFieldVisible() {
        waiter.waitForVisibility(usernameField);
        return usernameField.isDisplayed();
    }

    public boolean isPasswordFieldVisible() {
        waiter.waitForVisibility(passwordField);
        return passwordField.isDisplayed();
    }

    public boolean isLoginButtonDisabled() {
        waiter.waitForVisibility(loginButton);
        return !loginButton.isEnabled();
    }

    public boolean isLoginButtonEnabled() {
        waiter.waitForVisibility(loginButton);
        return loginButton.isEnabled();
    }

    public LoginPage enterUsername(String username) {
        waiter.waitForVisibility(usernameField);
        usernameField.clear();
        usernameField.sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        waiter.waitForVisibility(passwordField);
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public LoginPage clickLoginButton() {
        click(loginButton);
        return this;
    }

    public boolean isSuccessMessageVisible() {
        waiter.waitForVisibility(successMessage);
        return successMessage.isDisplayed();
    }

    public String getSuccessMessageText() {
        waiter.waitForVisibility(successMessage);
        return successMessage.getText();
    }

    public LoginPage enterUsernameDescription(String description) {
        waiter.waitForVisibility(usernameDescriptionField);
        usernameDescriptionField.clear();
        usernameDescriptionField.sendKeys(description);
        return this;
    }

    public boolean isErrorMessageVisible() {
        waiter.waitForVisibility(errorMessage);
        return errorMessage.isDisplayed();
    }

    public String getErrorMessageText() {
        waiter.waitForVisibility(errorMessage);
        return errorMessage.getText();
    }

    public LoginPage clickLogout() {
        click(logoutLink);
        return this;
    }

}
