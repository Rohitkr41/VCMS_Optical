package utils;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenshotUtil {

    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            File screenshotDir = new File("screenshots");

            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File(screenshotDir, screenshotName + ".png");

            FileHandler.copy(source, destination);

            System.out.println("Screenshot captured: " + destination.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
