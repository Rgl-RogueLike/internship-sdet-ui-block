package com.haritonov.uitests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SampleFormPage extends BasePage{

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

    public SampleFormPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(firstNameField);
        return firstNameField.isDisplayed();
    }

    public SampleFormPage enterFirstName(String firstName) {
        waiter.waitForVisibility(firstNameField);
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        return this;
    }

    public SampleFormPage enterLastname(String lastName) {
        waiter.waitForVisibility(lastNameField);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        return this;
    }

    public SampleFormPage enterEmail(String email) {
        waiter.waitForVisibility(emailField);
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    public SampleFormPage enterPassword(String password) {
        waiter.waitForVisibility(passwordField);
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public SampleFormPage selectHobby(String hobbyValue) {
        WebElement checkbox = hobbyCheckboxes.stream()
                .filter(cb -> cb.getAttribute("value").equals(hobbyValue))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hobby checkbox not found " + hobbyValue));
        waiter.waitForVisibility(checkbox);
        checkbox.click();
        return this;
    }

    public SampleFormPage selectGender(String genderValue) {
        waiter.waitForVisibility(genderSelect);
        new Select(genderSelect).selectByValue(genderValue);
        return this;
    }

    public List<String> getHobbyText() {
        return hobbyLabels.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    public String getLongestHobbyWord() {
        List<String> words = getHobbyText().stream()
                .flatMap(text -> Arrays.stream(text.split("\\s+")))
                .toList();

        return words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    public SampleFormPage enterAboutYourself(String text) {
        waiter.waitForVisibility(aboutField);
        aboutField.clear();
        aboutField.sendKeys(text);
        return this;
    }

    public SampleFormPage clickRegister() {
        click(registerButton);
        return this;
    }

    public boolean isSuccessMessageVisible() {
        waiter.waitForVisibility(successMessage);
        return successMessage.isDisplayed();
    }

    public String getSuccessMessageText() {
        waiter.waitForVisibility(successMessage);
        return successMessage.getText();
    }
}
