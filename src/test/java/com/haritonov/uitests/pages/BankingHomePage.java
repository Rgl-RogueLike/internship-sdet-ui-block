package com.haritonov.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BankingHomePage extends BasePage {

    @FindBy(linkText = "Sample Form")
    private WebElement sampleFormButton;

    @FindBy(xpath = "//button[text()='Bank Manager Login']")
    private WebElement bankManagerButton;

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
     public BankManagerPage goToBankManagerPage() {
        click(bankManagerButton);
        return new BankManagerPage(driver);
     }
}
