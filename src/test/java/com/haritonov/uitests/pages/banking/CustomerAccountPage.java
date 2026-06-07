package com.haritonov.uitests.pages.banking;

import com.haritonov.uitests.helpers.ParameterProvider;
import com.haritonov.uitests.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Page Object, представляющий страницу аккаунта клиента после входа.
 */
public class CustomerAccountPage extends BasePage {

    @FindBy(xpath = "//strong[contains(text(),'Welcome')]")
    private WebElement welcomeMessage;

    @FindBy(css = "button[ng-click='deposit()']")
    private WebElement depositButton;

    @FindBy(css = "button[ng-click='transactions()']")
    private WebElement transactionsButton;

    @FindBy(css = "input[type='number']")
    private WebElement amountInput;

    @FindBy(css = "button[type='submit']")
    private WebElement depositSubmitButton;

    @FindBy(xpath = "//span[contains(text(),'Deposit Successful')]")
    private WebElement depositSuccessMessage;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped']/tbody/tr")
    private List<WebElement> transactionRows;

    @FindBy(css = "button[ng-click='back()']")
    private WebElement backButton;

    @FindBy(css = "button[ng-click='withdrawl()']")
    private WebElement withdrawlButton;

    @FindBy(xpath = "//span[contains(text(),'Transaction successful')]")
    private WebElement withdrawlSuccessMessage;

    @FindBy(xpath = "(//div[@class='center']/strong)[2]")
    private WebElement balanceValue;

    @FindBy(xpath = "//span[contains(text(), 'Transaction Failed')]")
    private WebElement withdrawalErrorMessage;

    @FindBy(css = "button[ng-click='reset()']")
    private WebElement resetButton;

    private static final By TRANSACTION_ROWS = By.xpath("//table[@class='table table-bordered table-striped']/tbody/tr");

    /**
     * Создаёт CustomerAccountPage и инициализирует его элементы.
     *
     * @param driver активный WebDriver
     */
    public CustomerAccountPage(WebDriver driver) {
        super(driver);
    }

    /**
     * @return true, если видно приветственное сообщение.
     */
    @Step("Проверить загрузку страницы аккаунта")
    @Override
    public boolean isPageLoaded() {
        waiter.waitForVisibility(welcomeMessage);
        return welcomeMessage.isDisplayed();
    }

    /**
     * @return текст приветствия
     */
    @Step("Получить приветственное сообщение")
    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }

    /**
     * Открывает вкладку Deposit.
     */
    @Step("Открыть вкладку Deposit")
    public CustomerAccountPage clickDepositButton() {
        click(depositButton);
        return this;
    }

    /**
     * @param amount сумма для ввода
     */
    @Step("Ввести сумму '{amount}'")
    public CustomerAccountPage enterAmount(String amount) {
        waiter.waitForVisibility(amountInput);
        amountInput.clear();
        amountInput.sendKeys(amount);
        return this;
    }

    /**
     * Проверяет видимость кнопки Deposit.
     *
     * @return true, если кнопка видна.
     */
    @Step("Проверяет видимость кнопки депозита")
    public boolean isDepositButtonVisible() {
        try {
            return depositButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Нажимает кнопку подтверждения транзакции (Deposit/Withdraw).
     */
    @Step("Подтвердить транзакцию")
    public CustomerAccountPage submitTransaction() {
        click(depositSubmitButton);
        return this;
    }

    /**
     * Проверяет видимость сообщения "Deposit Successful".
     *
     * @return true, если сообщение появилось.
     */
    @Step("Проверить видимость сообщения 'Deposit Successful'")
    public boolean isDepositSuccessMessageVisible() {
        try {
            waiter.waitForVisibility(depositSuccessMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Переходит на вкладку Transactions и ждёт загрузки.
     */
    @Step("Перейти на вкладку Transactions")
    public CustomerAccountPage goToTransactions() {
        waiter.waitForTransactionToProcess(Long.parseLong(ParameterProvider.get("banking.customer.transaction.pause.millis")));
        click(transactionsButton);
        waiter.waitForVisibility(backButton);                                 // вкладка загрузилась
        return this;
    }

    /**
     * Возвращается на главную страницу аккаунта (кнопка Back).
     */
    @Step("Вернуться на главную страницу аккаунта")
    public CustomerAccountPage goBackToAccount() {
        click(backButton);
        return this;
    }

    /**
     * Выполняет депозит указанной суммы.
     *
     * @param amount сумма
     */
    @Step("Выполнить депозит на сумму '{amount}'")
    public CustomerAccountPage depositAmount(String amount) {
        clickDepositButton()
                .enterAmount(amount)
                .submitTransaction();
        return this;
    }

    /**
     * Проверяет, есть ли в таблице транзакций сумма.
     *
     * @param amount сумма для поиска
     * @return true, если сумма найдена.
     */
    @Step("Проверить наличие транзакции с суммой '{amount}'")
    public boolean hasTransactionWithAmount(String amount) {
        goToTransactions();
        List<String> amounts = getTransactionAmount();
        boolean found = amounts.contains(amount);
        goBackToAccount();
        return found;
    }

    /**
     * @return список сумм всех транзакций (столбец Amount)
     */
    @Step("Получить список сумм транзакций")
    public List<String> getTransactionAmount() {
        return driver.findElements(TRANSACTION_ROWS).stream()
                .map(row -> row.findElement(By.xpath("./td[2]")).getText())
                .toList();
    }

    /**
     * Открывает вкладку Withdrawal.
     */
    @Step("Открыть вкладку Withdrawal")
    public CustomerAccountPage clickWithdrawalButton() {
        click(withdrawlButton);
        return this;
    }

    /**
     * Проверяет видимость сообщения "Transaction successful" после снятия.
     *
     * @return true, если сообщение появилось.
     */
    @Step("Проверить видимость сообщения 'Transaction successful'")
    public boolean isWithdrawalSuccessMessageVisible() {
        try {
            waiter.waitForVisibility(withdrawlSuccessMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @return числовое значение текущего баланса
     */
    @Step("Получить текущий баланс")
    public int getBalanceNumeric() {
        String balanceText = getText(balanceValue);
        return Integer.parseInt(balanceText.trim());
    }

    /**
     * Выполняет снятие указанной суммы.
     *
     * @param amount сумма
     */
    @Step("Выполнить снятие суммы '{amount}'")
    public CustomerAccountPage withdrawlAmount(String amount) {
        clickWithdrawalButton()
                .enterAmount(amount)
                .submitTransaction();
        return this;
    }

    /**
     * Проверяет видимость сообщения об ошибке "Transaction Failed".
     *
     * @return true, если сообщение появилось.
     */
    @Step("Проверить видимость сообщения об ошибке 'Transaction Failed'")
    public boolean isWithdrawalErrorMessageVisible() {
        try {
            waiter.waitForVisibility(withdrawalErrorMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Подсчитывает баланс на основе таблицы транзакций
     * (Credit = +, Debit = -).
     *
     * @return вычисленный баланс
     */
    @Step("Рассчитать баланс из таблицы транзакций")
    public int calculateBalanceFromTransactions() {
        goToTransactions();
        int balance = driver.findElements(TRANSACTION_ROWS).stream()
                .mapToInt(row -> {
                    int amount = Integer.parseInt(row.findElement(By.xpath("./td[2]")).getText().trim());
                    String type = row.findElement(By.xpath("./td[3]")).getText().trim();
                    return type.equalsIgnoreCase("Credit") ? amount : -amount;
                })
                .sum();
        goBackToAccount();
        return balance;
    }

    /**
     * Нажимает кнопку Reset в транзакциях.
     */
    @Step("Нажать Reset в транзакциях")
    public CustomerAccountPage clickResetButton() {
        click(resetButton);
        return this;
    }

    /**
     * @return количество строк в таблице транзакций
     */
    @Step("Получить количество транзакций")
    public int getTransactionCount() {
        waiter.waitForTransactionToProcess(Long.parseLong(ParameterProvider.get("banking.customer.transaction.pause.millis")));
        return driver.findElements(TRANSACTION_ROWS).size();
    }
}
