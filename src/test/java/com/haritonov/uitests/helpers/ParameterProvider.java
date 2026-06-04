package com.haritonov.uitests.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Поставщик конфигурационных параметров из файла.
 * <p>Реализован как потокобезопасный синглтон с ленивой инициализацией.
 * Параметры загружаются в память при первом обращении.
 */
public class ParameterProvider {

    private static final String[] PARAMETERS_PATHS = {
            "configurations/settings.properties",
            "configurations/testdata.properties"
    };

    private static ParameterProvider instance;
    private final Map<String, String> parameters;


    private ParameterProvider() {
        parameters = new HashMap<>();
        for (String path : PARAMETERS_PATHS) {
            try {
                Properties prop = new Properties();
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
                if (inputStream == null) {
                    throw new RuntimeException("File not found: " + path);
                }
                prop.load(inputStream);
                prop.stringPropertyNames()
                        .forEach(key -> parameters.put(key, prop.getProperty(key)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Возвращает значение параметра по его ключу.
     *
     * @param key строковой ключ параметра
     * @return значение параметра или {@code null}, если ключ отсутствует
     */
    public static String get(String key) {
        if (instance == null) {
            synchronized (ParameterProvider.class) {
                instance = new ParameterProvider();
            }
        }
        return instance.parameters.get(key);
    }
}
