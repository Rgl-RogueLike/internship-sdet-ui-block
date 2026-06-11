package com.haritonov.uitests.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Трансформер аннотаций TestNG, автоматически добавляющий {@link RetryAnalyzer}
 * ко всем тестовым методам.
 *
 * <p>Реализует {@link IAnnotationTransformer} и переопределяет метод
 * {@link #transform(ITestAnnotation, Class, Constructor, Method)},
 * в котором устанавливает {@link RetryAnalyzer} для каждой аннотации {@code @Test}.
 *
 * <p>Это избавляет от необходимости прописывать {@code retryAnalyzer = ...}
 * в каждом тестовом методе вручную и позволяет централизованно управлять
 * стратегией повторного запуска упавших тестов.
 */
public class RetryTransformer implements IAnnotationTransformer {

    /**
     * Трансформирует аннотацию {@code @Test}, добавляя к ней
     * {@link RetryAnalyzer} для автоматического перезапуска упавших тестов.
     *
     * @param annotation      аннотация {@code @Test}, которую можно изменить
     * @param testClass       класс теста
     * @param testConstructor конструктор тестового класса
     * @param testMethod      метод теста
     */
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}