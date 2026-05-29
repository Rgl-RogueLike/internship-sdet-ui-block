package com.haritonov.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage extends BasePage{

    @FindBy(id = "masthead")
    private WebElement header;

    @FindBy(id = "site-navigation")
    private WebElement mainNavigation;

    @FindBy(css = "a.elementor-button.elementor-slide-button")
    private List<WebElement> registrationButtons;

    @FindBy(css = "h1.elementor-heading-title")
    private WebElement coursesSectionTitle;

    @FindBy(css = ".pp-info-box")
    private List<WebElement> courseCards;

    @FindBy(id = "colophon")
    private WebElement footer;

    public HomePage(WebDriver driver, WebDriverWait waiter) {
        super(driver, waiter);
    }

    @Override
    public boolean isPageLoaded() {
        try {
            waiter.until(WebDriver -> mainNavigation.isDisplayed());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isHeaderVisible() {
        return header.isDisplayed();
    }

    public boolean isNavigationVisible() {
        return mainNavigation.isDisplayed();
    }

    public boolean isRegistrationVisible() {
        return !registrationButtons.isEmpty() && registrationButtons.get(0).isDisplayed();
    }

    public boolean isCourseSectionVisible() {
        return coursesSectionTitle.isDisplayed() && !courseCards.isEmpty();
    }

    public boolean isFooterVisible() {
        return footer.isDisplayed();
    }
}
