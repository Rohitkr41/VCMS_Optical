package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VisualAcuityConfirmPopup {
	

    private WebDriver driver;
    private WebDriverWait wait;

    public VisualAcuityConfirmPopup(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Handle Visual Acuity / Refraction modal alert
     * Works for multiple OK buttons and Angular modals
     * @return alert message text
     */
    public String handleVisualAcuityAlert() {
        String message = "";

        By alertContainer = By.xpath("//div[contains(@class,'swal2-popup') or contains(@class,'alert')]");
        By okButtons = By.xpath("//div[contains(@class,'swal2-popup') or contains(@class,'alert')]//button[normalize-space()='OK']");

        try {
            // Wait for alert container
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(alertContainer));
            message = alert.getText().trim();
            System.out.println("✅ ALERT MESSAGE: " + message);

            // Try clicking all OK buttons in case multiple visible
            List<WebElement> buttons = driver.findElements(okButtons);
            for (WebElement btn : buttons) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                    Thread.sleep(200); // short pause
                } catch (Exception ignored) {}
            }

            // Wait until alert disappears
            wait.until(ExpectedConditions.invisibilityOfElementLocated(alertContainer));
            System.out.println("✅ Alert closed successfully");

        } catch (TimeoutException e) {
            System.out.println("⚠️ Alert did not appear");
        } catch (Exception e) {
            System.out.println("⚠️ Error handling alert: " + e.getMessage());
        }

        return message;
    }

}
