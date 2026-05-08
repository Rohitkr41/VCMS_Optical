package opticalTranscationsMasterPage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.BasePage;

public class WareHousePage extends BasePage {

    public WareHousePage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By opticalTransactionsMenu =
            By.xpath("//span[normalize-space()='Optical Transactions']/ancestor::a");

    private By warehouseMenu =
            By.xpath("//span[normalize-space()='WareHouse']/ancestor::a");

    private By overlay = By.id("V3MOverlay");

    // ===== NAVIGATION =====
    public void navigateToWareHousePage() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Click Optical Transactions Menu
        WebElement menu = wait.until(
                ExpectedConditions.elementToBeClickable(opticalTransactionsMenu));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", menu);

        // Click Warehouse Sub Menu
        WebElement subMenu = wait.until(
                ExpectedConditions.elementToBeClickable(warehouseMenu));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", subMenu);

        // Wait for page load
        wait.until(ExpectedConditions.urlContains("ViewBookingOrder"));

        waitForOverlay();

        System.out.println("Navigated to WareHouse page.");
    }

    // ===== VALIDATION =====
    public boolean isWareHousePageOpened() {
        return driver.getCurrentUrl().contains("ViewBookingOrder");
    }

    // ===== HELPER =====
    private void waitForOverlay() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (Exception ignored) {
        }
    }
}