package opticalTranscationsMasterPage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.BasePage;

public class DiscountRefundApprovalPage extends BasePage {

    public DiscountRefundApprovalPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By opticalTransactionsMenu = By.xpath("//span[normalize-space()='Optical Transactions']/ancestor::a");
    private By discountRefundMenu = By.xpath("//span[normalize-space()='Disc & Refund Approval']/ancestor::a");

    private By regionalDropdown = By.id("VDRC_ddlSearchEyeHospital");
    private By opticalShopDropdown = By.id("VDRC_ddlSearchVC");
    private By fromDateInput = By.id("VDRC_txtFromDate");
    private By toDateInput = By.id("VDRC_txtToDate");
    private By searchButton = By.id("VDRC_btnSearch");
    private By overlay = By.id("V3MOverlay"); // for overlay wait

    // Table
    private By tableRows = By.xpath("//table//tbody/tr");
    private By firstCheckbox = By.xpath("//table//tbody/tr[1]//input[@type='checkbox']");

    // Action
    private By approveButton = By.xpath("//button[normalize-space()='Approve']");

    // ===== NAVIGATION =====
    public void navigateToDiscountRefundPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(opticalTransactionsMenu));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);

        WebElement subMenu = wait.until(ExpectedConditions.elementToBeClickable(discountRefundMenu));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subMenu);

        wait.until(ExpectedConditions.urlContains("ViewDiscAndRefundCancelApproval"));
        waitForOverlay();

        System.out.println("Navigated to Discount & Refund Approval page.");
    }

    // ===== FILTER ACTIONS =====
    public void selectRegional(String regionalName) {
        Select select = new Select(waitForElement(regionalDropdown));
        select.selectByVisibleText(regionalName);
        System.out.println("Selected Regional: " + regionalName);
    }


   public void setDateRange(String fromDate, String toDate) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    WebElement from = waitForElement(fromDateInput);
    js.executeScript("arguments[0].removeAttribute('readonly')", from); // remove readonly
    js.executeScript("arguments[0].value = arguments[1]", from, fromDate);

    WebElement to = waitForElement(toDateInput);
    js.executeScript("arguments[0].removeAttribute('readonly')", to);
    js.executeScript("arguments[0].value = arguments[1]", to, toDate);

    System.out.println("Date range set via JS: " + fromDate + " to " + toDate);
}

    public void selectOpticalShop(String shopName) {
        Select select = new Select(waitForElement(opticalShopDropdown));
        if (select.getOptions().size() > 1) { // skip if empty
            select.selectByVisibleText(shopName);
            System.out.println("Selected Optical Shop: " + shopName);
        } else {
            System.out.println("Optical Shop dropdown has no options, skipping selection");
        }
    }

    public void clickSearch() {
        WebElement btn = waitForElement(searchButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        waitForOverlay();
        System.out.println("Clicked Search button");
    }

    // ===== TABLE ACTIONS =====
    public boolean isDataAvailable() {
        return driver.findElements(tableRows).size() > 0;
    }

    public void selectFirstRecord() {
        waitForElement(firstCheckbox).click();
        System.out.println("Selected first record for approval");
    }

    public void clickApprove() {
        waitForElement(approveButton).click();
        waitForOverlay();
        System.out.println("Clicked Approve button.");
    }

    // ===== VALIDATION =====
    public boolean isPageOpened() {
        return driver.getCurrentUrl().contains("ViewDiscAndRefundCancelApproval");
    }

    // ===== HELPER METHODS =====
    private void waitForOverlay() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (Exception ignored) {}
    }

    private WebElement waitForElement(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
 // ===== SELECT RECORD BY BOOKING NUMBER =====

 // ===== SELECT ALL RECORDS CHECKBOX =====
    public void selectAllRecords() {
        WebElement selectAllCheckbox = waitForElement(By.cssSelector("input.VDRC_dataGrid_chkAll"));
        if (!selectAllCheckbox.isSelected()) {
            selectAllCheckbox.click();
            System.out.println("Clicked 'Select All' checkbox.");
        } else {
            System.out.println("'Select All' checkbox is already selected.");
        }
    }

    // ===== CLICK APPROVE BUTTON =====
    public void clickApproveButton() {
        WebElement approveBtn = waitForElement(By.id("VDRC_btnSubmit"));
        approveBtn.click();
        waitForOverlay();
        System.out.println("Clicked Approve button.");
    }
    
 // ===== CLICK YES ON APPROVAL CONFIRMATION POPUP =====
    public void confirmApproval() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("popup_ok")));
            yesButton.click();
            waitForOverlay(); // optional: wait for overlay if your page has loading
            System.out.println("Clicked 'Yes' on approval confirmation popup.");
        } catch (Exception e) {
            System.out.println("Approval confirmation popup not found: " + e.getMessage());
        }
    }
}