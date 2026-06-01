package com.haritonov.uitests.utils;

import com.github.javafaker.Faker;
import com.haritonov.uitests.helpers.ParameterProvider;

/**
 * Генератор случайных тестовых данных на основе библиотеки JavaFaker.
 * <p>Все методы возвращают случайные, но реалистичные значения,
 * подходящие для заполнения форм в тестах.
 */
public class TestDataGenerator {

    private static final Faker faker = new Faker();

    /**
     * @return случайное имя
     */
    public static String getRandomFirstName() {
        return faker.name().firstName();
    }

    /**
     * @return случайная фамилия
     */
    public static String getRandomLastName() {
        return faker.name().lastName();
    }

    /**
     * @return случайный email-адрес
     */
    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    /**
     * @return случайный пароль длиной от {@code faker.password.min.size} до {@code faker.password.max.size}
     */
    public static String getRandomPassword() {
        return faker.internet().password(
                Integer.parseInt(ParameterProvider.get("faker.password.min.size")),
                Integer.parseInt(ParameterProvider.get("faker.password.max.size")));
    }

    /**
     * @return случайный пол (один из вариантов, заданных в конфигурации)
     */
    public static String getRandomGender() {
        return faker.options()
                .option(
                        ParameterProvider.get("faker.gender.male"),
                        ParameterProvider.get("faker.gender.female"),
                        ParameterProvider.get("faker.gender.other")
                );
    }

    /**
     * @return случайный почтовый индекс
     */
    public static String getRandomPostCode() {
        return faker.address().zipCode();
    }

    /**
     * @return целое случайное число в диапазоне от {@code banking.customer.min.amount}
     *         до {@code banking.customer.max.amount}
     */
    public static String getRandomAmount() {
        return String.valueOf(
                faker.number().numberBetween(
                    Integer.parseInt(ParameterProvider.get("banking.customer.min.amount")),
                    Integer.parseInt(ParameterProvider.get("banking.customer.max.amount"))
                )
        );
    }

    /**
     * Возвращает случайное целое число от {@code banking.customer.min.amount}
     * до указанного максимума.
     *
     * @param maxAmount верхняя граница (включительно)
     * @return случайное число в виде строки
     */
    public static String getRandomAmount(int maxAmount) {
        return String.valueOf(
                faker.number().numberBetween(
                        Integer.parseInt(ParameterProvider.get("banking.customer.min.amount")),
                        maxAmount
                )
        );
    }
}
