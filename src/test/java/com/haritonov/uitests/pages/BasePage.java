package com.haritonov.uitests.pages;

import com.haritonov.uitests.helpers.Waiter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Абстрактный базовый класс для всех Page Object'ов.
 * Предоставляет общие методы для клика и получения текста с автоматическими
 * ожиданиями через {@link Waiter}.
 * Каждый наследник должен реализовать {@link #isPageLoaded()}.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final Waiter waiter;

    /**
     * Конструктор, инициализирующий драйвер, Waiter и прокси-элементы страницы.
     *
     * @param driver веб-драйвер, управляющий текущим браузером
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waiter = new Waiter(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Кликает по элементу, предварительно дождавшись его кликабельности.
     *
     * @param element для клика
     * @return текущую страницу
     */
    protected BasePage click(WebElement element) {
        waiter.clickWhenReady(element);
        return this;
    }

    /**
     * Возвращает текст элемента после того, как он стал видимым.
     *
     * @param element целевой веб-элемент
     * @return видимый текст элемента
     */
    protected String getText(WebElement element) {
        return waiter.getTextWhenVisible(element);
    }

    /**
     * Проверяет, загружена ли страница.
     * Должен быть реализован в каждом конкретном Page Object.
     *
     * @return {@code true}, если страница загружена
     */
    public abstract boolean isPageLoaded();
}
