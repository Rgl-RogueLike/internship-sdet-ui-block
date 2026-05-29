package com.haritonov.uitests.pages;

import com.haritonov.uitests.helpers.Waiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final Waiter waiter;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waiter = new Waiter(driver);
        PageFactory.initElements(driver, this);
    }

    protected BasePage click(WebElement element) {
        waiter.clickWhenReady(element);
        return this;
    }

    protected String getText(WebElement element) {
        return waiter.getTextWhenVisible(element);
    }

    public abstract boolean isPageLoaded();
}
