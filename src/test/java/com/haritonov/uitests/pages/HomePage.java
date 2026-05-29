package com.haritonov.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

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

    @FindBy(css = ".elementor-location-footer")
    private WebElement footer;

    @FindBy(css = ".elementor-location-footer .elementor-icon-list-text")
    private List<WebElement> footerTextItems;

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

    @FindBy(css = ".pp-slider-arrow.elementor-swiper-button-prev")
    private WebElement coursesPrevButton;

    @FindBy(css = ".pp-slider-arrow.elementor-swiper-button-next")
    private WebElement coursesNextButton;

    @FindBy(className = "swiper-wrapper")
    private WebElement coursesSlider;

    @FindBy(css = "h4.pp-info-box-title")
    private WebElement activeCourseTitle;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        try {
            waiter.waitForVisibility(mainNavigation);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isHeaderVisible() {
        waiter.waitForVisibility(header);
        return header.isDisplayed();
    }

    public boolean isNavigationVisible() {
        waiter.waitForVisibility(mainNavigation);
        return mainNavigation.isDisplayed();
    }

    public boolean isRegistrationVisible() {
        if (registrationButtons.isEmpty()) {
            return false;
        }
        waiter.waitForVisibility(registrationButtons.get(0));
        return !registrationButtons.isEmpty() && registrationButtons.get(0).isDisplayed();
    }

    public boolean isCourseSectionVisible() {
        waiter.waitForVisibility(coursesSectionTitle);
        return coursesSectionTitle.isDisplayed() && !courseCards.isEmpty();
    }

    public boolean isFooterVisible() {
        waiter.waitForVisibility(footer);
        return footer.isDisplayed();
    }

    public int getHeaderPhoneCount() {
        if (!headerPhones.isEmpty()) {
            waiter.waitForVisibility(headerPhones.get(0));
        }
        return headerPhones.size();
    }

    public boolean isSkypeLinkPresent() {
        waiter.waitForVisibility(headerSkypeLink);
        return headerSkypeLink.isDisplayed();
    }

    public boolean isEmailLinkPresent() {
        waiter.waitForVisibility(headerEmailLink);
        return headerEmailLink.isDisplayed();
    }

    public boolean isFacebookLinkPresent() {
        waiter.waitForVisibility(headerFacebookLink);
        return headerFacebookLink.isDisplayed();
    }

    public boolean isLinkedinLinkPresent() {
        waiter.waitForVisibility(headerLinkedinLink);
        return headerLinkedinLink.isDisplayed();
    }

    public boolean isInstagramLinkPresent() {
        waiter.waitForVisibility(headerInstagramLink);
        return headerInstagramLink.isDisplayed();
    }

    public boolean isYoutubeLinkPresent() {
        waiter.waitForVisibility(headerYoutubeLink);
        return headerYoutubeLink.isDisplayed();
    }

    public boolean isCoursesPrevButtonVisible() {
        waiter.waitForVisibility(coursesPrevButton);
        return coursesPrevButton.isDisplayed();
    }

    public boolean isCoursesNextButtonVisible() {
        waiter.waitForVisibility(coursesNextButton);
        return coursesNextButton.isDisplayed();
    }

    public boolean isCourseSliderVisible() {
        waiter.waitForVisibility(coursesSlider);
        return coursesSlider.isDisplayed();
    }

    public HomePage clickCoursesPrev() {
        click(coursesPrevButton);
        return this;
    }

    public HomePage clickCoursesNext() {
        click(coursesNextButton);
        return this;
    }

    public String getActiveSlideTitle() {
        return getText(activeCourseTitle).trim();
    }

    public void scrollToCoursesSection() {
        waiter.waitForVisibility(coursesNextButton);
        new Actions(driver)
                .moveToElement(coursesNextButton)
                .perform();
    }

    public void scrollToFooter() {
        waiter.waitForVisibility(footer);
        new Actions(driver)
                .moveToElement(footer)
                .perform();
    }
}