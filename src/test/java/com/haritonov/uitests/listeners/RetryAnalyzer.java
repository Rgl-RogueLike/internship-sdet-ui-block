package com.haritonov.uitests.listeners;

import com.haritonov.uitests.helpers.ParameterProvider;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Анализатор повторных запусков упавших тестов.
 *
 * <p>Позволяет автоматически перезапускать упавший тест заданное количество раз.
 * Количество попыток настраивается через конфигурацию {@code retry.max.count}.
 * Если параметр не задан, по умолчанию используется 2 попытки.
 *
 * <p>Применяется ко всем тестам через {@link RetryTransformer}.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private final int maxRetryCount;

    /**
     * Создаёт анализатор с количеством попыток из конфигурации.
     */
    public RetryAnalyzer() {
        String configValue = ParameterProvider.get("retry.max.count");
        this.maxRetryCount = configValue != null ? Integer.parseInt(configValue) : 2;
    }

    /**
     * Определяет, нужно ли повторить упавший тест.
     *
     * @param result результат выполнения теста
     * @return {@code true}, если тест будет перезапущен; {@code false} если попытки исчерпаны
     */
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }
}