package com.haritonov.uitests.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * Утилита для сохранения и загрузки cookies веб-драйвера.
 * <p>Позволяет сохранить текущие cookies в файл и восстановить их
 * в другом сеансе WebDriver, чтобы пропустить повторную аутентификацию.
 * <p>Cookies хранятся в виде {@link ArrayList}.
 */
public class CookieUtils {

    /**
     * Сохраняет переданный набор cookies в файл.
     * <p>Внутри {@link Set} преобразуется в {@link ArrayList} для сериализации.
     *
     * @param cookies  набор cookies, полученный от {@code driver.manage().getCookies()}
     * @param filePath путь к файлу, в который будут сохранены cookies
     * @throws RuntimeException если произошла ошибка ввода-вывода
     */
    public static void saveCookiesToFile(Set<Cookie> cookies, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(cookies));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save cookies to " + filePath, e);
        }
    }

    /**
     * Загружает cookies из файла и добавляет их в текущий браузер.
     * <p>Перед вызовом этого метода драйвер уже должен находиться на домене,
     * для которого предназначены cookies.
     *
     * @param driver   текущий экземпляр {@link WebDriver}
     * @param filePath путь к файлу с сохранёнными cookies
     * @throws RuntimeException если файл не найден, повреждён или cookies не могут быть добавлены
     */
    @SuppressWarnings("unchecked")
    public static void loadCookiesFromFile(WebDriver driver, String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            ArrayList<Cookie> cookies = (ArrayList<Cookie>) ois.readObject();
            for (Cookie cookie : cookies) {
                driver.manage().addCookie(cookie);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load cookies from " + filePath, e);
        }
    }

    /**
     * Удаляет файл по указанному пути, если он существует.
     * <p>Используется для очистки cookies перед первым тестом,
     * чтобы гарантировать чистую сессию.
     *
     * @param filePath путь к файлу
     * @throws RuntimeException если файл существует, но не может быть удалён
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && !file.delete()) {
            throw new RuntimeException("Failed to delete file: " + filePath);
        }
    }
}