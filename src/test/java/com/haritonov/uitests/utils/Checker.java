package com.haritonov.uitests.utils;

import com.haritonov.uitests.pages.banking.CustomerAccountPage;
import org.testng.Assert;

/**
 * Вспомогательные методы для часто используемых проверок.
 */
public class Checker {

    /**
     * Проверяет, что фактическая строка содержит ожидаемую подстроку.
     *
     * @param actualText   фактическая строка
     * @param expectedText ожидаемая строка
     * @param message      сообщение об ошибке
     */
    public static void assertTextContains(String actualText, String expectedText, String message) {
        Assert.assertTrue(actualText.contains(expectedText), message);
    }

    /**
     * Проверяет, что транзакция с заданной суммой присутствует в истории счёта.
     *
     * @param accountPage страница аккаунта, через которую выполняется проверка
     * @param amount      ожидаемая сумма транзакции
     * @param message     описание ошибки в случае падения
     */
    public static void assertTransactionPresent(CustomerAccountPage accountPage, String amount, String message) {
        Assert.assertTrue(accountPage.hasTransactionWithAmount(amount), message);
    }

    /**
     * Проверяет, что транзакция с заданной суммой отсутствует в истории счёта.
     *
     * @param accountPage страница аккаунта, через которую выполняется проверка
     * @param amount      сумма, которой не должно быть в истории
     * @param message     описание ошибки в случае падения
     */
    public static void assertTransactionNotPresent(CustomerAccountPage accountPage, String amount, String message) {
        Assert.assertFalse(accountPage.hasTransactionWithAmount(amount), message);
    }

    /**
     * Проверяет, что целое число строго больше нуля.
     *
     * @param value   проверяемое значение
     * @param message смысловое описание того, что проверяется (например, "баланс")
     */
    public static void assertPositive(int value, String message) {
        Assert.assertTrue(value > 0, message);
    }

    /**
     * Сравнивает два целых числа на равенство.
     *
     * @param expected ожидаемое значение
     * @param actual   фактическое значение
     * @param message  описание ошибки при несовпадении
     */
    public static void assertEquals(int expected, int actual, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    /**
     * Универсальная проверка истинности условия.
     *
     * @param condition условие, которое должно быть true
     * @param message   описание ошибки при падении
     */
    public static void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    /**
     * Универсальная проверка ложности условия.
     *
     * @param condition условие, которое должно быть false
     * @param message   описание ошибки при падении
     */
    public static void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    /**
     * Точное сравнение двух строк.
     *
     * @param expected ожидаемая строка
     * @param actual   фактическая строка
     * @param message  описание ошибки при несовпадении
     */
    public static void assertEquals(String expected, String actual, String message) {
        Assert.assertEquals(actual, expected, message);
    }
}
