package com.haritonov.uitests.tests.homepage;

import com.haritonov.uitests.pages.HomePage;
import com.haritonov.uitests.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void mainElementsShouldBeVisibleWhenHomePageLoads() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        Assert.assertTrue(homePage.isHeaderVisible(), "Header should be visible");
        Assert.assertTrue(homePage.isNavigationVisible(), "Navigate block should be visible");
        Assert.assertTrue(homePage.isRegistrationVisible(), "Registration button should be visible");
        Assert.assertTrue(homePage.isCourseSectionVisible(), "Courses section should be visible");
        homePage.scrollToFooter();
        Assert.assertTrue(homePage.isFooterVisible(), "Footer should be visible");
    }

    @Test
    public void headerContactsShouldBePresentWhenHomePageLoads() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        Assert.assertTrue(homePage.getHeaderPhoneCount() > 0, "Header should contain phone numbers");
        Assert.assertTrue(homePage.isSkypeLinkPresent(), "Header should contain Skype link");
        Assert.assertTrue(homePage.isEmailLinkPresent(), "Header should contain Email link");
        Assert.assertTrue(homePage.isFacebookLinkPresent(), "Header should contain Facebook link");
        Assert.assertTrue(homePage.isLinkedinLinkPresent(), "Header should contain LinkedId link");
        Assert.assertTrue(homePage.isInstagramLinkPresent(), "Header should contain Instagram link");
        Assert.assertTrue(homePage.isYoutubeLinkPresent(), "Header should contain YouTube link");
    }

    @Test
    public void coursesSliderShouldNotSwitchWhenNavigationButtonsClicked() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        homePage.scrollToCoursesSection();
        Assert.assertTrue(homePage.isCourseSliderVisible(), "Courses section should be visible");
        Assert.assertTrue(homePage.isCoursesPrevButtonVisible(), "Previous button should be visible");
        Assert.assertTrue(homePage.isCoursesNextButtonVisible(), "Next button should be visible");

        String currentSlideTitle = homePage.getActiveSlideTitle();
        homePage.clickCoursesNext();
        String nextSlideTitle = homePage.getActiveSlideTitle();
        homePage.clickCoursesPrev().clickCoursesPrev();
        String prevSlideTitle = homePage.getActiveSlideTitle();

        Assert.assertEquals(currentSlideTitle, nextSlideTitle);
        Assert.assertEquals(currentSlideTitle, prevSlideTitle);
    }

    @Test
    public void testFooter() {
        HomePage homePage = new HomePage(driver);
        homePage.scrollToFooter();
        String s = "s;";
    }
}
