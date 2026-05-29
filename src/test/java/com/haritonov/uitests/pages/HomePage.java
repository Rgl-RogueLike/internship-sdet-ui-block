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

    @FindBy(css = "header a[href^='https://wa.me/'], header a[href^='tel:']")
    private List<WebElement> headerPhones;

    @FindBy(css = "header a[href^='skype:']")
    private WebElement headerSkypeLink;

    @FindBy(css = "header a[href^='mailto:']")
    private WebElement headerEmailLink;

    @FindBy(className = "ast-facebook")
    private WebElement headerFacebookLink;

    @FindBy(className = "ast-twitter")
    private WebElement headerLinkedinLink;

    @FindBy(className = "ast-instagram")
    private WebElement headerInstagramLink;

    @FindBy(className = "ast-youtube")
    private WebElement headerYoutubeLink;

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

    public int getHeaderPhoneCount() {
        return headerPhones.size();
    }

    public boolean isSkypeLinkPresent() {
        return headerSkypeLink.isDisplayed();
    }

    public boolean isEmailLinkPresent() {
        return headerEmailLink.isDisplayed();
    }

    public boolean isFacebookLinkPresent() {
        return headerFacebookLink.isDisplayed();
    }

    public boolean isLinkedinLinkPresent() {
        return headerLinkedinLink.isDisplayed();
    }

    public boolean isInstagramLinkPresent() {
        return headerInstagramLink.isDisplayed();
    }

    public boolean isYoutubeLinkPresent() {
        return headerYoutubeLink.isDisplayed();
    }
}
