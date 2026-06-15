package com.haritonov.uitests.tests.dragdrop;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.DragDropPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("Drag and Drop")
@Feature("Перетаскивание элементов")
public class DragDropTest extends BaseTest {

    @Test(description = "Перетаскивание элемента в принимающую область и проверка текста")
    @Story("Успешный Drag and Drop внутри iframe")
    @Severity(SeverityLevel.NORMAL)
    public void shouldDragAndDropElementAndVerifyText() {
        driver.get(ParameterProvider.get("drag.drop.url"));
        DragDropPage page = new DragDropPage(driver);
        Checker.assertTrue(page.isPageLoaded(), "Drag and Drop page should be loaded");
        page.switchToFrame();
        page.dragAndDrop();
        String expectedText = ParameterProvider.get("drag.drop.success.message");
        String actualText = page.getDroppableText();
        Checker.assertEquals(expectedText, actualText, "Droppable text should match expected");
    }
}
