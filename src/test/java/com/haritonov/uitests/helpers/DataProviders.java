package com.haritonov.uitests.helpers;

import org.testng.annotations.DataProvider;

/**
 * Централизованный поставщик тестовых данных для DataProvider TestNG.
 * Все значения читаются из конфигурационных файлов через {@link ParameterProvider}.
 */
public class DataProviders {

    /**
     * Наборы учётных данных для параметризованного теста авторизации.
     * <p>Каждый набор содержит четыре строки:
     * <ol>
     *   <li>имя пользователя</li>
     *   <li>пароль</li>
     *   <li>описание (для поля Username*)</li>
     *   <li>ключ ожидаемого сообщения в конфигурации</li>
     * </ol>
     * @return двумерный массив объектов, готовый к использованию в TestNG
     */
    @DataProvider(name = "loginCredentials")
    public static Object[][] loginCredentials() {
        return new Object[][] {
                {
                    ParameterProvider.get("login.dataset.valid.username"),
                    ParameterProvider.get("login.dataset.valid.password"),
                    ParameterProvider.get("login.dataset.valid.description"),
                    ParameterProvider.get("login.dataset.valid.expected.key")
                },
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
