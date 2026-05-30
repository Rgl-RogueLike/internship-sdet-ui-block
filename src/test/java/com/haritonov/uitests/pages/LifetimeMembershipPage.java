package com.haritonov.uitests.pages;

import com.haritonov.uitests.helpers.ParameterProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LifetimeMembershipPage extends BasePage{

    @FindBy(tagName = "h1")
    private WebElement pageTitle;

    public LifetimeMembershipPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        waiter.waitForUrlContains("lifetime-membership-club");
        return driver.getCurrentUrl().equals(ParameterProvider.get("lifetime.membership.url"));
    }

    public String getTextTitle() {
        return getText(pageTitle);
    }
}
