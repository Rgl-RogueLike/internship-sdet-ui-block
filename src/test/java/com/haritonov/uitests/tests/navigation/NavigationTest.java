package com.haritonov.uitests.tests.navigation;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.HomePage;
import com.haritonov.uitests.pages.LifetimeMembershipPage;
import com.haritonov.uitests.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NavigationTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        homePage = new HomePage(driver);
    }

    @Test
    public void navigationMenuShouldRemainVisibleAfterScrollingDown() {
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        Assert.assertTrue(homePage.isNavigationVisible(), "Navigation menu should be visible at the top the page");
        homePage.scrollToFooter();
        Assert.assertTrue(homePage.isNavigationVisible(), "Navigation menu should be visible after scrolling down");
    }

    @Test
    public void shouldNavigateToLifetimeMembershipFromMenu() {
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        LifetimeMembershipPage lifetimeMembershipPage = homePage.goToLifetimeMembership();
        Assert.assertTrue(lifetimeMembershipPage.isPageLoaded(), "Should be on Lifetime Member page");
        String titleContains = ParameterProvider.get("navigation.lifetime.page.title");
        String titlePage = lifetimeMembershipPage.getTextTitle().toUpperCase();
        Assert.assertTrue(titlePage.contains(titleContains), "Page title should contain " + titleContains + ", but got: " + titlePage);
    }
}
