package com.haritonov.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Page Object для формы регистрации на странице
 * {@code https://www.way2automation.com/angularjs-protractor/banking/registrationform.html}.
 */
public class SampleFormPage extends BasePage {

    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(xpath = "//*[@id=\"lastName\"]")
    private WebElement lastNameField;

    @FindBy(css = "input[name='email']")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "about")
    private WebElement aboutField;

    @FindBy(xpath = "//*[@id=\"gender\"]")
    private WebElement genderSelect;

    @FindBy(css = "button[type='submit']")
    private WebElement registerButton;

    @FindBy(id = "successMessage")
    private WebElement successMessage;

    @FindBy(css = "input[name='hobbies']")
    private List<WebElement> hobbyCheckboxes;

    @FindBy(xpath = "//input[@name='hobbies']/parent::label")
    private List<WebElement> hobbyLabels;

    /**
     * Создаёт SampleFormPage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public SampleFormPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Страница загружена, когда видно поле First Name.
     */
    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(firstNameField);
        return firstNameField.isDisplayed();
    }

    /**
     * Вводит имя.
     *
     * @param firstName имя
     * @return текущая страница
     */
    public SampleFormPage enterFirstName(String firstName) {
        waiter.waitForVisibility(firstNameField);
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        return this;
    }

    /**
     * Вводит фамилию.
     *
     * @param lastName фамилия
     * @return текущая страница
     */
    public SampleFormPage enterLastname(String lastName) {
        waiter.waitForVisibility(lastNameField);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        return this;
    }

    /**
     * Вводит email.
     *
     * @param email адрес электронной почты
     * @return текущая страница
     */
    public SampleFormPage enterEmail(String email) {
        waiter.waitForVisibility(emailField);
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    /**
     * Вводит пароль.
     *
     * @param password пароль
     * @return текущая страница
     */
    public SampleFormPage enterPassword(String password) {
        waiter.waitForVisibility(passwordField);
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    /**
     * Выбирает указанное хобби по его значению.
     *
     * @param hobbyValue значение атрибута {@code value}
     * @return текущая страница
     * @throws RuntimeException если чекбокс не найден
     */
    public SampleFormPage selectHobby(String hobbyValue) {
        WebElement checkbox = hobbyCheckboxes.stream()
                .filter(cb -> cb.getAttribute("value").equals(hobbyValue))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hobby checkbox not found " + hobbyValue));
        waiter.waitForVisibility(checkbox);
        checkbox.click();
        return this;
    }

    /**
     * Выбирает пол из выпадающего списка.
     *
     * @param genderValue значение пола
     * @return текущая страница
     */
    public SampleFormPage selectGender(String genderValue) {
        waiter.waitForVisibility(genderSelect);
        new Select(genderSelect).selectByValue(genderValue);
        return this;
    }

    /**
     * @return список всех хобби в виде списка строк
     */
    public List<String> getHobbyText() {
        return hobbyLabels.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    /**
     * Находит самое длинное слово среди названий хобби.
     * Если названий несколько, разбивает их на слова по пробелам.
     *
     * @return самое длинное слово или пустую строку, если список хобби пуст
     */
    public String getLongestHobbyWord() {
        List<String> words = getHobbyText().stream()
                .flatMap(text -> Arrays.stream(text.split("\\s+")))
                .toList();

        return words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    /**
     * Вводит текст в поле "About Yourself".
     *
     * @param text текст для ввода
     * @return текущая страница
     */
    public SampleFormPage enterAboutYourself(String text) {
        waiter.waitForVisibility(aboutField);
        aboutField.clear();
        aboutField.sendKeys(text);
        return this;
    }

    /**
     * Нажимает кнопку Register.
     *
     * @return текущая страница
     */
    public SampleFormPage clickRegister() {
        click(registerButton);
        return this;
    }

    /**
     * @return {@code true}, если сообщение об успешной регистрации отображается
     */
    public boolean isSuccessMessageVisible() {
        waiter.waitForVisibility(successMessage);
        return successMessage.isDisplayed();
    }

    /**
     * @return текст сообщения об успешной регистрации
     */
    public String getSuccessMessageText() {
        waiter.waitForVisibility(successMessage);
        return successMessage.getText();
    }
}
