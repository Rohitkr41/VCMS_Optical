//package utils;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.*;
//import java.time.Duration;
//
//public class AlertHandler {
//
//    /**
//     * Ultra-stable method to close "No Data" alert.
//     * Works for alerts in modal or main page, before or after search.
//     */
//    public static void closeNoDataAlert(WebDriver driver) {
//
//        // alert message text (catch variations)
//        By alertMessage = By.xpath("//p[contains(text(),'No records found')]");
//        // OK button relative to alert message
//        By okButton = By.xpath("//p[contains(text(),'No records found')]/following::button[normalize-space()='OK'][1]");
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//
//        try {
//            // Wait for alert message (short wait)
//            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(alertMessage));
//
//            // Wait for OK button
//            WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(okButton));
//
//            try {
//                okBtn.click(); // normal click
//            } catch (Exception e) {
//                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okBtn);
//            }
//
//            // Wait until alert disappears
//            wait.until(ExpectedConditions.invisibilityOf(okBtn));
//            System.out.println("[AlertHandler] 'No Data' alert closed.");
//
//        } catch (TimeoutException te) {
//            // alert not present
//        } catch (StaleElementReferenceException se) {
//            // retry once if alert disappears/reappears
//            try {
//                WebElement okBtn = driver.findElement(okButton);
//                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okBtn);
//                System.out.println("[AlertHandler] 'No Data' alert closed on retry.");
//            } catch (Exception e) {}
//        } catch (Exception e) {
//            System.out.println("[AlertHandler] Exception while closing alert: " + e.getMessage());
//        }
//    }
//}

package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class AlertHandler {

    private static final Duration WAIT_TIME = Duration.ofSeconds(5);

    /**
     * Close "No records found" alert if present.
     * Works for modal alerts inside the page.
     */
    public static void closeNoDataAlert(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);

        By alertMessage = By.xpath("//p[contains(text(),'No records found')]");
        By okButton = By.xpath("//p[contains(text(),'No records found')]/following::button[normalize-space()='OK'][1]");

        try {

            // Wait for alert message
            WebElement alert = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(alertMessage));

            System.out.println("Alert Message: " + alert.getText());

            // Wait for OK button
            WebElement okBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(okButton));

            try {
                okBtn.click();
            } 
            catch (Exception e) {

                // JS click fallback
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", okBtn);
            }

            // Wait for alert to disappear
            wait.until(ExpectedConditions.invisibilityOf(okBtn));

            System.out.println("[AlertHandler] 'No records found' alert closed.");

        } 
        catch (TimeoutException e) {

            // Alert not present (normal case)
            System.out.println("[AlertHandler] No alert present.");

        } 
        catch (StaleElementReferenceException e) {

            // Retry once
            try {

                WebElement okBtn = driver.findElement(okButton);

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", okBtn);

                System.out.println("[AlertHandler] Alert closed on retry.");

            } catch (Exception ignore) {}
        }
    }
}