package com.haritonov.uitests.utils;

import com.github.javafaker.Faker;
import com.haritonov.uitests.helpers.ParameterProvider;

public class TestDataGenerator {

    private static final Faker faker = new Faker();

    public static String getRandomFirstName() {
        return faker.name().firstName();
    }

    public static String getRandomLastName() {
        return faker.name().lastName();
    }

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomPassword() {
        return faker.internet().password(
                Integer.parseInt(ParameterProvider.get("faker.password.min.size")),
                Integer.parseInt(ParameterProvider.get("faker.password.max.size")));
    }

    public static String getRandomGender() {
        return faker.options()
                .option(
                        ParameterProvider.get("faker.gender.male"),
                        ParameterProvider.get("faker.gender.female"),
                        ParameterProvider.get("faker.gender.other")
                );
    }

    public static String getRandomPostCode() {
        return faker.address().zipCode();
    }

    public static String getRandomAmount() {
        return String.valueOf(
                faker.number().numberBetween(
                    Integer.parseInt(ParameterProvider.get("banking.customer.min.amount")),
                    Integer.parseInt(ParameterProvider.get("banking.customer.max.amount"))
                )
        );
    }
}
