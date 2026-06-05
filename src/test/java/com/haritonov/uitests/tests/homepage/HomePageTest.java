package com.haritonov.uitests.tests.homepage;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.HomePage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Тесты главной страницы http://way2automation.com/.
 * <p>Проверяет видимость основных блоков, контакты в хедере,
 * поведение слайдера курсов и содержимое футера.
 */
public class HomePageTest extends BaseTest {

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

    @Test(description = "Главная страница: все основные блоки (хедер, навигация, регистрация, курсы, футер) видны после загрузки")
    public void mainElementsShouldBeVisibleWhenHomePageLoads() {
        Checker.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        Checker.assertTrue(homePage.isHeaderVisible(), "Header should be visible");
        Checker.assertTrue(homePage.isNavigationVisible(), "Navigate block should be visible");
        Checker.assertTrue(homePage.isRegistrationVisible(), "Registration button should be visible");
        Checker.assertTrue(homePage.isCourseSectionVisible(), "Courses section should be visible");
        homePage.scrollToFooter();
        Checker.assertTrue(homePage.isFooterVisible(), "Footer should be visible");
    }

    @Test(description = "Главная страница: хедер содержит телефоны, Skype, Email и ссылки на соцсети")
    public void headerContactsShouldBePresentWhenHomePageLoads() {
        Checker.assertTrue(homePage.getHeaderPhoneCount() > 0, "Header should contain phone numbers");
        Checker.assertTrue(homePage.isSkypeLinkPresent(), "Header should contain Skype link");
        Checker.assertTrue(homePage.isEmailLinkPresent(), "Header should contain Email link");
        Checker.assertTrue(homePage.isFacebookLinkPresent(), "Header should contain Facebook link");
        Checker.assertTrue(homePage.isLinkedinLinkPresent(), "Header should contain LinkedId link");
        Checker.assertTrue(homePage.isInstagramLinkPresent(), "Header should contain Instagram link");
        Checker.assertTrue(homePage.isYoutubeLinkPresent(), "Header should contain YouTube link");
    }

    @Test(description = "Главная страница: кнопки слайдера курсов не меняют заголовок активного слайда")
    public void coursesSliderShouldNotSwitchWhenNavigationButtonsClicked() {
        homePage.scrollToCoursesSection();
        Checker.assertTrue(homePage.isCourseSliderVisible(), "Courses section should be visible");
        Checker.assertTrue(homePage.isCoursesPrevButtonVisible(), "Previous button should be visible");
        Checker.assertTrue(homePage.isCoursesNextButtonVisible(), "Next button should be visible");

        String currentSlideTitle = homePage.getActiveSlideTitle();
        homePage.clickCoursesNext();
        String nextSlideTitle = homePage.getActiveSlideTitle();
        homePage.clickCoursesPrev().clickCoursesPrev();
        String prevSlideTitle = homePage.getActiveSlideTitle();

        Checker.assertEquals(currentSlideTitle, nextSlideTitle, "After clicking Next, slide title should not change (expected same as initial)");
        Checker.assertEquals(currentSlideTitle, prevSlideTitle, "After clicking Prev twice, slide title should not change (expected same as initial)");
    }

    @Test(description = "Главная страница: футер содержит адрес, телефон и email")
    public void footerShouldBeVisibleAndContainAddressPhoneAndEmails() {
        homePage.scrollToFooter();
        Checker.assertTrue(homePage.isFooterVisible(), "Footer should be visible");
        List<String> contacts = homePage.getAboutUsContactTexts();
        Checker.assertFalse(contacts.isEmpty(), "Footer contact block should not be empty");

        String addressPart = ParameterProvider.get("footer.address.part");
        String phonePrefix = ParameterProvider.get("footer.phone.prefix");
        String emailSign = ParameterProvider.get("footer.email.sign");

        boolean hasAddress = contacts.stream()
                .anyMatch(text -> text.contains(addressPart));
        Checker.assertTrue(hasAddress, "Footer should contain address with + " + addressPart);

        boolean hasPhone = contacts.stream()
                .anyMatch(text -> text.contains(phonePrefix));
        Checker.assertTrue(hasPhone, "Footer should contain phone number starting with " + phonePrefix);

        boolean hasEmail = contacts.stream()
                .anyMatch(text -> text.contains(emailSign));
        Checker.assertTrue(hasEmail, "Footer should contain email address with " + emailSign);
    }
}
