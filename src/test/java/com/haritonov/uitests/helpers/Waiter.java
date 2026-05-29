package com.haritonov.uitests.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waiter {

    private final WebDriverWait wait;

    public Waiter(WebDriver driver) {
        int explicitTimeout = Integer.parseInt(ParameterProvider.get("explicit.wait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitTimeout));
    }

    public Waiter waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    public String getTextWhenVisible(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }

    public Waiter waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    public Waiter waitForTextPresent(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    public void clickWhenReady(WebElement element) {
        waitForClickable(element);
        element.click();
    }

}
