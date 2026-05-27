package com.haritonov.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait waiter;

    public BasePage(WebDriver driver, WebDriverWait waiter) {
        this.driver = driver;
        this.waiter = waiter;
        PageFactory.initElements(driver, this);
    }

    protected BasePage click(WebElement element) {
        element.click();
        return this;
    }

    protected String getText(WebElement element) {
        return element.getText();
    }

    public abstract boolean isPageLoaded();
}
