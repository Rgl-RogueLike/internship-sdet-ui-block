package com.haritonov.uitests.tests.navigation;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.HomePage;
import com.haritonov.uitests.pages.LifetimeMembershipPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Тесты навигационного меню.
 * <p>Проверяет закрепление меню при скролле и переход на страницу Lifetime Membership.
 */
public class NavigationTest extends BaseTest {

    private HomePage homePage;

    /**
     * Перед каждым тестом открывает главную страницу и создаёт HomePage.
     */
    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        homePage = new HomePage(driver);
    }

    @Test(description = "Навигация: меню должно оставаться видимым после прокрутки страницы вниз")
    public void navigationMenuShouldRemainVisibleAfterScrollingDown() {
        Checker.assertTrue(homePage.isNavigationVisible(), "Navigation menu should be visible at the top the page");
        homePage.scrollToFooter();
        Checker.assertTrue(homePage.isNavigationVisible(), "Navigation menu should be visible after scrolling down");
    }

    @Test(description = "Навигация: переход All Courses -> Lifetime Membership, проверка URL и заголовка страницы")
    public void shouldNavigateToLifetimeMembershipFromMenu() {
        LifetimeMembershipPage lifetimeMembershipPage = homePage.goToLifetimeMembership();
        Checker.assertTrue(lifetimeMembershipPage.isPageLoaded(), "Should be on Lifetime Member page");
        String titleContains = ParameterProvider.get("navigation.lifetime.page.title");
        String titlePage = lifetimeMembershipPage.getTextTitle().toUpperCase();
        Checker.assertTrue(titlePage.contains(titleContains), "Page title should contain " + titleContains + ", but got: " + titlePage);
    }
}
