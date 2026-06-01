package com.haritonov.uitests.pages;

import com.haritonov.uitests.helpers.ParameterProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object для страницы Lifetime Membership Club
 * ({@code https://www.way2automation.com/lifetime-membership-club/}).
 */
public class LifetimeMembershipPage extends BasePage{

    @FindBy(tagName = "h1")
    private WebElement pageTitle;

    /**
     * Создаёт LifetimeMembershipPage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public LifetimeMembershipPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Страница считается загруженной, когда URL содержит
     * "lifetime-membership-club" и совпадает с ожидаемым из конфигурации.
     */
    @Override
    public boolean isPageLoaded() {
        waiter.waitForUrlContains("lifetime-membership-club");
        return driver.getCurrentUrl().equals(ParameterProvider.get("lifetime.membership.url"));
    }

    /**
     * @return текст заголовка страницы
     */
    public String getTextTitle() {
        return getText(pageTitle);
    }
}
