package com.haritonov.uitests.tests.homepage;

import com.haritonov.uitests.pages.HomePage;
import com.haritonov.uitests.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    protected void mainElementsShouldBeVisibleWhenHomePageLoads() {
        HomePage homePage = new HomePage(driver, waiter);
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        Assert.assertTrue(homePage.isHeaderVisible(), "Header should be visible");
        Assert.assertTrue(homePage.isNavigationVisible(), "Navigate block should be visible");
        Assert.assertTrue(homePage.isRegistrationVisible(), "Registration button should be visible");
        Assert.assertTrue(homePage.isCourseSectionVisible(), "Courses section should be visible");
        Assert.assertTrue(homePage.isFooterVisible(), "Footer should be visible");
    }
}
