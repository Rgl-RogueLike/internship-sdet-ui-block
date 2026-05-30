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
}
