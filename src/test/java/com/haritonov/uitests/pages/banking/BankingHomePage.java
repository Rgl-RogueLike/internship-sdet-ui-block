package com.haritonov.uitests.pages.banking;

import com.haritonov.uitests.pages.BasePage;
import com.haritonov.uitests.pages.SampleFormPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object, представляющий домашнюю страница банковского приложения (Way2Automation Banking App).
 */
public class BankingHomePage extends BasePage {

    @FindBy(linkText = "Sample Form")
    private WebElement sampleFormButton;

    @FindBy(xpath = "//button[text()='Bank Manager Login']")
    private WebElement bankManagerButton;

    @FindBy(xpath = "//button[text()='Customer Login']")
    private WebElement customerLoginButton;

    /**
     * Создаёт BankingHomePage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public BankingHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * @return true, если кнопка "Sample Form" видна.
     */
    @Step("Проверить загрузку домашней страницы банка")
    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(sampleFormButton);
        return sampleFormButton.isDisplayed();
    }

    /**
     * Переходит на страницу Sample Form.
     *
     * @return новый объект {@link SampleFormPage}
     */
    @Step("Перейти на Sample Form")
    public SampleFormPage goToSampleFormPage() {
        click(sampleFormButton);
        return new SampleFormPage(driver);
    }

    /**
     * Переходит в интерфейс Bank Manager.
     *
     * @return новый объект {@link BankManagerPage}
     */
    @Step("Перейти на Bank Manager Login")
    public BankManagerPage goToBankManagerPage() {
        click(bankManagerButton);
        return new BankManagerPage(driver);
    }

    /**
     * Переходит на страницу выбора клиента.
     *
     * @return новый объект {@link CustomerLoginPage}
     */
    @Step("Перейти на Customer Login")
    public CustomerLoginPage goToCustomerLogin() {
        click(customerLoginButton);
        return new CustomerLoginPage(driver);
    }
}
