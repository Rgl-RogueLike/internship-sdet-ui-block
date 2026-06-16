package com.haritonov.uitests.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object для страницы Droppable (Drag and Drop).
 * <p>Содержит элементы внутри iframe и методы для перетаскивания.
 */
public class DragDropPage extends BasePage {

    @FindBy(id = "draggable")
    private WebElement draggableElement;

    @FindBy(id = "droppable")
    private WebElement droppableArea;

    @FindBy(css = ".demo-frame")
    private WebElement demoFrame;

    /**
     * Создаёт DragDropPage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public DragDropPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Страница считается загруженной, когда виден блок с элементами.
     */
    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(demoFrame);
        return demoFrame.isDisplayed();
    }

    /**
     * Переключается в iframe, содержащий элементы для перетаскивания.
     */
    @Step("Переключиться в iframe с элементами")
    public void switchToFrame() {
        driver.switchTo().frame(demoFrame);
    }

    /**
     * Перетаскивает draggable элемент в droppable область.
     */
    @Step("Перетащить элемент в принимающую область")
    public void dragAndDrop() {
        new Actions(driver)
                .dragAndDrop(draggableElement, droppableArea)
                .perform();
    }

    /**
     * Возвращает текст принимающего элемента после перетаскивания.
     *
     * @return текст внутри droppable области
     */
    @Step("Получить текст принимающего элемента")
    public String getDroppableText() {
        return droppableArea.getText().trim();
    }
}
