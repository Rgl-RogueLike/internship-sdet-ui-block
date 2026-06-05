package com.haritonov.uitests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page Object, представляющий главную страницу сайта {@code http://way2automation.com/}.
 * <p>Содержит элементы и методы для взаимодействия с хедером, навигацией,
 * секцией курсов и футером.
 */
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

    @FindBy(xpath = "//h4[contains(text(),'ABOUT US')]/ancestor::div[contains(@class,'elementor-column')]//span[@class='elementor-icon-list-text']")
    private List<WebElement> aboutUsTextElements;

    @FindBy(xpath = "//a[span[text()='All Courses']]")
    private WebElement allCoursesMenu;

    @FindBy(xpath = "//a[span[text()='Lifetime Membership']]")
    private WebElement lifetimeMembershipLink;

    /**
     * Создаёт HomePage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public HomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Страница считается загруженной, когда виден блок навигации.
     */
    @Override
    @Step("Проверить загрузку главной страницы")
    public boolean isPageLoaded() {
        try {
            waiter.waitForVisibility(mainNavigation);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @return {@code true}, если хедер отображается
     */
    @Step("Проверить видимость хедера")
    public boolean isHeaderVisible() {
        waiter.waitForVisibility(header);
        return header.isDisplayed();
    }

    /**
     * @return {@code true}, если блок навигации отображается
     */
    @Step("Проверить видимость навигации")
    public boolean isNavigationVisible() {
        waiter.waitForVisibility(mainNavigation);
        return mainNavigation.isDisplayed();
    }

    /**
     * @return {@code true}, если на странице видна хотя бы одна кнопка регистрации
     */
    @Step("Проверить видимость кнопки регистрации")
    public boolean isRegistrationVisible() {
        if (registrationButtons.isEmpty()) {
            return false;
        }
        waiter.waitForVisibility(registrationButtons.get(0));
        return !registrationButtons.isEmpty() && registrationButtons.get(0).isDisplayed();
    }

    /**
     * @return {@code true}, если секция курсов отображается и содержит карточки курсов
     */
    @Step("Проверить видимость секции курсов")
    public boolean isCourseSectionVisible() {
        waiter.waitForVisibility(coursesSectionTitle);
        return coursesSectionTitle.isDisplayed() && !courseCards.isEmpty();
    }

    /**
     * @return {@code true}, если футер отображается
     */
    @Step("Проверить видимость футера")
    public boolean isFooterVisible() {
        waiter.waitForVisibility(footer);
        return footer.isDisplayed();
    }

    /**
     * @return количество телефонных номеров в хедере
     */
    @Step("Получить количество телефонов в хедере")
    public int getHeaderPhoneCount() {
        if (!headerPhones.isEmpty()) {
            waiter.waitForVisibility(headerPhones.get(0));
        }
        return headerPhones.size();
    }

    /**
     * @return {@code true} если Skype-ссылка видна
     */
    @Step("Проверить видимость Skype-ссылки")
    public boolean isSkypeLinkPresent() {
        waiter.waitForVisibility(headerSkypeLink);
        return headerSkypeLink.isDisplayed();
    }

    /**
     * @return {@code true} если Email-ссылка видна
     */
    @Step("Проверить видимость Email-ссылки")
    public boolean isEmailLinkPresent() {
        waiter.waitForVisibility(headerEmailLink);
        return headerEmailLink.isDisplayed();
    }

    /**
     * @return {@code true} если ссылка на Facebook видна
     */
    @Step("Проверить видимость Facebook-ссылки")
    public boolean isFacebookLinkPresent() {
        waiter.waitForVisibility(headerFacebookLink);
        return headerFacebookLink.isDisplayed();
    }

    /**
     * @return {@code true} если ссылка на LinkedIn видна
     */
    @Step("Проверить видимость LinkedIn-ссылки")
    public boolean isLinkedinLinkPresent() {
        waiter.waitForVisibility(headerLinkedinLink);
        return headerLinkedinLink.isDisplayed();
    }

    /**
     * @return {@code true} если ссылка на Instagram видна
     */
    @Step("Проверить видимость Instagram-ссылки")
    public boolean isInstagramLinkPresent() {
        waiter.waitForVisibility(headerInstagramLink);
        return headerInstagramLink.isDisplayed();
    }

    /**
     * @return {@code true} если ссылка на YouTube видна
     */
    @Step("Проверить видимость YouTube-ссылки")
    public boolean isYoutubeLinkPresent() {
        waiter.waitForVisibility(headerYoutubeLink);
        return headerYoutubeLink.isDisplayed();
    }

    /**
     * @return {@code true}, если кнопка "предыдущий слайд" в карусели курсов видна
     */
    @Step("Проверить видимость кнопки 'Предыдущий слайд'")
    public boolean isCoursesPrevButtonVisible() {
        waiter.waitForVisibility(coursesPrevButton);
        return coursesPrevButton.isDisplayed();
    }

    /**
     * @return {@code true}, если кнопка "следующий слайд" в карусели курсов видна
     */
    @Step("Проверить видимость кнопки 'Следующий слайд'")
    public boolean isCoursesNextButtonVisible() {
        waiter.waitForVisibility(coursesNextButton);
        return coursesNextButton.isDisplayed();
    }

    /**
     * @return {@code true}, если слайдер курсов отображается
     */
    @Step("Проверить видимость слайдера курсов")
    public boolean isCourseSliderVisible() {
        waiter.waitForVisibility(coursesSlider);
        return coursesSlider.isDisplayed();
    }

    /**
     * Кликает по кнопке "Previous" в слайдере курсов.
     *
     * @return текущая страница для цепочек вызовов
     */
    @Step("Кликнуть 'Предыдущий слайд'")
    public HomePage clickCoursesPrev() {
        click(coursesPrevButton);
        return this;
    }

    /**
     * Кликает по кнопке "Next" в слайдере курсов.
     *
     * @return текущая страница для цепочек вызовов
     */
    @Step("Кликнуть 'Следующий слайд'")
    public HomePage clickCoursesNext() {
        click(coursesNextButton);
        return this;
    }

    /**
     * @return текст заголовка активного слайда
     */
    @Step("Получить заголовок активного слайда")
    public String getActiveSlideTitle() {
        return getText(activeCourseTitle).trim();
    }

    /**
     * Прокручивает страницу до секции курсов.
     */
    @Step("Прокрутить до секции курсов")
    public void scrollToCoursesSection() {
        waiter.waitForVisibility(coursesNextButton);
        new Actions(driver)
                .moveToElement(coursesNextButton)
                .perform();
    }

    /**
     * Прокручивает страницу до футера.
     */
    @Step("Прокрутить до футера")
    public void scrollToFooter() {
        waiter.waitForVisibility(footer);
        new Actions(driver)
                .moveToElement(footer)
                .perform();
    }

    /**
     * Возвращает список текстовых данных из секции ABOUT US футера.
     *
     * @return список строк (адрес, телефоны, email)
     */
    @Step("Получить контактные тексты из секции ABOUT US")
    public List<String> getAboutUsContactTexts() {
        waiter.waitForVisibility(aboutUsTextElements.get(0));
        return aboutUsTextElements.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    /**
     * Наводит на пункт меню "All Courses" и кликает по "Lifetime Membership",
     * после чего возвращает страницу {@link LifetimeMembershipPage}.
     *
     * @return объект страницы LifetimeMembershipPage
     */
    @Step("Перейти на страницу Lifetime Membership через меню")
    public LifetimeMembershipPage goToLifetimeMembership() {
        new Actions(driver)
                .moveToElement(allCoursesMenu)
                .perform();
        waiter.waitForVisibility(lifetimeMembershipLink);
        lifetimeMembershipLink.click();
        return new LifetimeMembershipPage(driver);
    }
}