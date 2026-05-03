package utils;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertConfirmationPopup {

    WebDriver driver;
    WebDriverWait wait;

    public AlertConfirmationPopup(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

   public String handlePopupFast() {
    String message = "";

    By alertMessage = By.xpath(
        "//div[contains(@class,'alert') and not(contains(@style,'display: none'))] | " +
        "//div[contains(@class,'swal2-popup')] | " +
        "//p[contains(text(),'successfully') or contains(text(),'updated successfully') or contains(text(),'exist') or contains(text(),'sure')]" +
        "//p[contains(text(),'updated successfully!')] | " + 
        "//div[contains(@class,'alert-body')]//p[contains(text(),'No records found')]"
    );

    By okButtons = By.xpath("//button[normalize-space()='OK' or normalize-space()='Ok' |" +
    "//div[contains(@class,'alert-btn')]//button[normalize-space()='OK']");
//    By okButtons1 = By.xpath("(//button[contains(text(),'OK')])[3]");
    By yesButtons = By.xpath("//button[normalize-space()='Yes']");

    try {
        WebElement msgEl = wait.until(ExpectedConditions.visibilityOfElementLocated(alertMessage));
        message = msgEl.getText();
        System.out.println("✅ ALERT: " + message);

        // Click first visible OK button
        for (WebElement btn : driver.findElements(okButtons)) {
            if (btn.isDisplayed() && btn.isEnabled()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                break;
            }
        }
        // Click first visible Yes button (if present)
        for (WebElement btn : driver.findElements(yesButtons)) {
            if (btn.isDisplayed() && btn.isEnabled()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                break;
            }
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(alertMessage));

    } catch (Exception e) {
        System.out.println("⚠️ No alert found");
    }

    return message;
}


    private void clickJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}