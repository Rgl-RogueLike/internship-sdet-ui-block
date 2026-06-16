package com.haritonov.uitests.tests.tabs;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.FramesWindowsPage;
import com.haritonov.uitests.tests.BaseTest;
import com.haritonov.uitests.utils.Checker;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

public class TabsTest extends BaseTest {

    @Test(description = "Открытие новой вкладки, переключение на нее, открытие еще одной вкладки и проверка количества вкладок")
    @Story("Переключение между вкладками")
    @Severity(SeverityLevel.NORMAL)
    public void shouldOpenNewTabAndVerifyWindowCount() {
        driver.get(ParameterProvider.get("frames.windows.url"));
        FramesWindowsPage page = new FramesWindowsPage(driver);
        Checker.assertTrue(page.isPageLoaded(), "Frames and Windows page should be loaded");
        int initialWindowCount = page.getWindowCount();
        page.switchToFrame();
        page.openNewTabAndSwitch();
        page.clickNewBrowserTabLinkOnCurrentPage();
        int finalWindowCount = page.getWindowCount();
        Checker.assertEquals(initialWindowCount + 2, finalWindowCount, "Two new tabs should be opened, total = " + (initialWindowCount + 2));
    }
}
