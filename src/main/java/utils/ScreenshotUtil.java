package utils;

import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenshotUtil {

    public static void captureScreenshot(WebDriver driver, String testName) {

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("screenshots/" + testName + ".png");
            FileHandler.copy(src, dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}