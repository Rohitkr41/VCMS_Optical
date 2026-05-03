package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static void initDriver() {

        WebDriverManager.chromedriver().setup();
        tlDriver.set(new ChromeDriver());
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {

        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}
