package com.haritonov.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BankingHomePage extends BasePage {

    @FindBy(linkText = "Sample Form")
    private WebElement sampleFormButton;

    public BankingHomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(sampleFormButton);
        return sampleFormButton.isDisplayed();
    }

    public SampleFormPage goToSampleFormPage() {
        click(sampleFormButton);
        return new SampleFormPage(driver);
    }
}
