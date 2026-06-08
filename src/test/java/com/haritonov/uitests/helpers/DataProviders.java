package com.haritonov.uitests.helpers;

import org.testng.annotations.DataProvider;

/**
 * Централизованный поставщик тестовых данных для DataProvider TestNG.
 * Все значения читаются из конфигурационных файлов через {@link ParameterProvider}.
 */
public class DataProviders {

    /**
     * Наборы валидных учётных данных для теста успешной авторизации.
     * @return двумерный массив объектов
     */
    @DataProvider(name = "validLoginCredentials")
    public static Object[][] loginCredentials() {
        return new Object[][] {
                {
                    ParameterProvider.get("login.dataset.valid.username"),
                    ParameterProvider.get("login.dataset.valid.password"),
                    ParameterProvider.get("login.dataset.valid.description"),
                    ParameterProvider.get("login.dataset.valid.expected.key")
                }
        };
    }

    /**
     * Наборы невалидных учётных данных для теста неуспешной авторизации.
     * @return двумерный массив объектов
     */
    @DataProvider(name = "invalidLoginCredentials")
    public static Object[][] invalidLoginCredentials() {
        return new Object[][] {
                {
                    ParameterProvider.get("login.dataset.invalid.username"),
                    ParameterProvider.get("login.dataset.invalid.password"),
                    ParameterProvider.get("login.dataset.invalid.description"),
                    ParameterProvider.get("login.dataset.invalid.expected.key")
                },
                {
                    ParameterProvider.get("login.dataset.wrongpass.username"),
                    ParameterProvider.get("login.dataset.wrongpass.password"),
                    ParameterProvider.get("login.dataset.wrongpass.description"),
                    ParameterProvider.get("login.dataset.wrongpass.expected.key")
                }
        };
    }
}
