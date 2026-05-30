package com.haritonov.uitests.tests.navigation;

import com.haritonov.uitests.pages.HomePage;
import com.haritonov.uitests.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationTest extends BaseTest {

    @Test
    public void navigationMenuShouldRemainVisibleAfterScrollingDown() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        Assert.assertTrue(homePage.isNavigationVisible(), "Navigation menu should be visible at the top the page");
        homePage.scrollToFooter();
        Assert.assertTrue(homePage.isNavigationVisible(), "Navigation menu should be visible after scrolling down");
    }
}
