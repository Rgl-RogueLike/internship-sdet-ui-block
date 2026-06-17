package com.haritonov.uitests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object для страницы Basic Authentication.
 * <p>Содержит кнопку для отображения защищённого изображения.
 */
public class BasicAuthPage extends BasePage {

    @FindBy(id = "displayImage")
    private WebElement displayImageButton;

    @FindBy(id = "downloadImg")
    private WebElement downloadImg;

    public BasicAuthPage(WebDriver driver) {
        super(driver);
    }

    /**
     * <p>Страница считается загруженной, когда видна кнопка "Display Image".
     */
    @Override
    @Step("Проверить загрузку страницы Basic Auth")
    public boolean isPageLoaded() {
        waiter.waitForVisibility(displayImageButton);
        return displayImageButton.isDisplayed();
    }

    /**
     * Нажимает кнопку "Display Image".
     */
    @Step("Нажать кнопку 'Display Image'")
    public void clickDisplayImage() {
        click(displayImageButton);
    }

    /**
     * Проверяет, что изображение успешно загружено после авторизации.
     *
     * @return {@code true}, если изображение имеет ненулевую ширину
     */
    @Step("Проверить, что изображение загружено")
    public boolean isImageLoaded() {
        waiter.waitForVisibility(downloadImg);
        String naturalWidth = downloadImg.getAttribute("naturalWidth");
        return naturalWidth != null && !naturalWidth.equals("0");
    }
}
