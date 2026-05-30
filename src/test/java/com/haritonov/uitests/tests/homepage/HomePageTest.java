package com.haritonov.uitests.tests.homepage;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.HomePage;
import com.haritonov.uitests.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class HomePageTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        homePage = new HomePage(driver);
    }

    @Test
    public void mainElementsShouldBeVisibleWhenHomePageLoads() {
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

        Assert.assertEquals(currentSlideTitle, nextSlideTitle, "After clicking Next, slide title should not change (expected same as initial)");
        Assert.assertEquals(currentSlideTitle, prevSlideTitle, "After clicking Prev twice, slide title should not change (expected same as initial)");
    }

    @Test
    public void footerShouldBeVisibleAndContainAddressPhoneAndEmails() {
        homePage.scrollToFooter();
        Assert.assertTrue(homePage.isFooterVisible(), "Footer should be visible");
        List<String> contacts = homePage.getAboutUsContactTexts();
        Assert.assertFalse(contacts.isEmpty(), "Footer contact block should not be empty");

        String addressPart = ParameterProvider.get("footer.address.part");
        String phonePrefix = ParameterProvider.get("footer.phone.prefix");
        String emailSign = ParameterProvider.get("footer.email.sign");

        boolean hasAddress = contacts.stream()
                .anyMatch(text -> text.contains(addressPart));
        Assert.assertTrue(hasAddress, "Footer should contain address with + " + addressPart);

        boolean hasPhone = contacts.stream()
                .anyMatch(text -> text.contains(phonePrefix));
        Assert.assertTrue(hasPhone, "Footer should contain phone number starting with " + phonePrefix);

        boolean hasEmail = contacts.stream()
                .anyMatch(text -> text.contains(emailSign));
        Assert.assertTrue(hasEmail, "Footer should contain email address with " + emailSign);
    }
}
