package com.haritonov.uitests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Page Object, представляющий страницу Bank Manager.
 */
public class BankManagerPage extends BasePage {

    @FindBy(xpath = "//button[@ng-click='addCust()']")
    private WebElement addCustomerButton;

    @FindBy(xpath = "//input[@ng-model='fName']")
    private WebElement firstNameField;

    @FindBy(xpath = "//input[@ng-model='lName']")
    private WebElement lastNameField;

    @FindBy(xpath = "//input[@ng-model='postCd']")
    private WebElement postCodeField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement addCustomerSubmitButton;

    @FindBy(xpath = "//button[@ng-click='openAccount()']")
    private WebElement openCustomerButton;

    @FindBy(id = "userSelect")
    private WebElement customerSelect;

    @FindBy(id = "currency")
    private WebElement currencySelect;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement processButton;

    @FindBy(xpath = "//button[@ng-click='showCust()']")
    private WebElement customersButton;

    @FindBy(css = "input[ng-model='searchCustomer']")
    private WebElement searchCustomerInput;

    private static final By CUSTOMER_ROWS = By.xpath("//table[@class='table table-bordered table-striped']/tbody/tr");

    /**
     * Создаёт BankManagerPage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public BankManagerPage(WebDriver driver) {
        super(driver);
    }

    /**
     * @return true, если кнопка "Add Customer" видна.
     */
    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(addCustomerButton);
        return addCustomerButton.isDisplayed();
    }

    /**
     * Открывает вкладку Add Customer.
     */
    public BankManagerPage clickAddCustomerButton() {
        click(addCustomerButton);
        return this;
    }

    /**
     * @param firstName имя
     */
    public BankManagerPage enterFirstName(String firstName) {
        waiter.waitForVisibility(firstNameField);
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        return this;
    }

    /**
     * @param lastName фамилия
     */
    public BankManagerPage enterLastName(String lastName) {
        waiter.waitForVisibility(lastNameField);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        return this;
    }

    /**
     * @param postCode почтовый индекс
     */
    public BankManagerPage enterPostCode(String postCode) {
        waiter.waitForVisibility(postCodeField);
        postCodeField.clear();
        postCodeField.sendKeys(postCode);
        return this;
    }

    /**
     * Отправляет форму Add Customer.
     */
    public BankManagerPage submitAddCustomer() {
        click(addCustomerSubmitButton);
        return this;
    }

    /**
     * Принимает алерт и возвращает его текст.
     *
     * @return текст алерта
     */
    public String acceptAlertAndGetText() {
        return waiter.waitForAlertAndAccept();
    }

    /**
     * Открывает вкладку Open Account и ждёт появления списка клиентов.
     */
    public BankManagerPage clickOpenCustomerButton() {
        click(openCustomerButton);
        waiter.waitForVisibility(customerSelect);
        return this;
    }

    /**
     * Выбирает последнего клиента в выпадающем списке.
     */
    public BankManagerPage selectLastCustomer() {
        Select select = new Select(customerSelect);
        List<WebElement> options = select.getOptions();
        WebElement lastOption = options.get(options.size() - 1);
        lastOption.click();
        return this;
    }

    /**
     * @param name полное имя клиента (First + Last)
     */
    public BankManagerPage selectCustomerByName(String name) {
        new Select(customerSelect).selectByVisibleText(name);
        return this;
    }

    /**
     * @param currency валюта (Dollar, Pound, Rupee)
     */
    public BankManagerPage selectCurrency(String currency) {
        new Select(currencySelect).selectByVisibleText(currency);
        return this;
    }

    /**
     * Нажимает кнопку Process для открытия счёта.
     */
    public BankManagerPage clickProcess() {
        click(processButton);
        return this;
    }

    /**
     * Создаёт клиента и сразу открывает ему счёт.
     *
     * @param firstName имя
     * @param lastName  фамилия
     * @param postCode  почтовый индекс
     * @param currency  валюта
     * @return полное имя созданного клиента
     */
    public String createCustomerWithAccount(String firstName, String lastName, String postCode, String currency) {
        clickAddCustomerButton()
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostCode(postCode)
                .submitAddCustomer();
        acceptAlertAndGetText();

        clickOpenCustomerButton()
                .selectCustomerByName(firstName + " " + lastName)
                .selectCurrency(currency)
                .clickProcess();
        acceptAlertAndGetText();
        return firstName + " " + lastName;
    }

    /**
     * Открывает вкладку Customers.
     */
    public BankManagerPage clickCustomersButton() {
        click(customersButton);
        waiter.waitForVisibility(searchCustomerInput);
        return this;
    }

    /**
     * @param firstName имя для поиска
     */
    public BankManagerPage searchCustomer(String firstName) {
        searchCustomerInput.clear();
        searchCustomerInput.sendKeys(firstName);
        return this;
    }

    /**
     * @return количество строк в таблице клиентов
     */
    public int getCustomerCount() {
        return driver.findElements(CUSTOMER_ROWS).size();
    }

    /**
     * Удаляет первую строку в таблице клиентов.
     */
    public BankManagerPage deleteFirstCustomerInTable() {
        List<WebElement> rows = driver.findElements(CUSTOMER_ROWS);
        if (rows.isEmpty()) {
            throw new NoSuchElementException("No customer rows found to delete");
        }
        WebElement deleteButton = rows.get(0).findElement(By.xpath("./td[5]/button[text()='Delete']"));
        click(deleteButton);
        return this;
    }

    /**
     * Очищает поле поиска клиентов.
     */
    public BankManagerPage clearSearchField() {
        searchCustomerInput.clear();
        return this;
    }

    /**
     * @return список первых имён (First Name) всех видимых клиентов
     */
    public List<String> getCustomerFirstNames() {
        List<WebElement> rows = driver.findElements(CUSTOMER_ROWS);
        return rows.stream()
                .map(row -> row.findElement(By.xpath("./td[1]")).getText().trim())
                .toList();
    }
}
