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

    // Dropdown
    private By statusDropdown = By.id("VBO_ddlExaminationType");

    // Search Button
    private By searchBtn = By.id("VBO_btnSearch");

    // Header Checkbox
    private By selectAllCheckbox =
            By.xpath("//input[@class='VBO_tblRecord_chkAll']");

    // Action Buttons
    private By underFittingBtn = By.id("VBO_btnUnderFitting");

    private By readyToDispatchBtn = By.id("VBO_btnReadyToDispatch");

    private By dispatchBtn = By.id("VBO_btnDispatch");
    
	 // ===== COURIER DETAILS LOCATORS =====
	
	 // Mode Dropdown
	 private By courierModeDropdown = By.id("VBO_ddlCourierMode");
	
	 // Courier Company Dropdown
	 private By courierCompanyDropdown = By.id("VBO_ddlCourierCompany");
	
	 // Amount
	 private By amountField = By.id("VBO_txtAmount");
	
	 // Tracking No
	 private By trackingNoField = By.id("VBO_txtTrackingNo");
	
	 // Courier Note
	 private By courierNoteField = By.id("VBO_txtCourierNote");
	
	 // Submit Button
	 private By submitBtn =
	         By.xpath("//button[contains(text(),'Submit')] | //input[@value='Submit']");
	    
	 // Popup Message
	    private By popupMessage = By.id("popup_message");

    // OK Button
    private By popupOkButton = By.id("popup_ok");

    // ===== NAVIGATION =====
    public void navigateToWareHousePage() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement menu = wait.until(
                ExpectedConditions.elementToBeClickable(opticalTransactionsMenu));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", menu);

        WebElement subMenu = wait.until(
                ExpectedConditions.elementToBeClickable(warehouseMenu));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", subMenu);

        wait.until(ExpectedConditions.urlContains("ViewBookingOrder"));

        waitForOverlay();

        System.out.println("Navigated to WareHouse page.");
    }
    
    // ===== VALIDATION =====
    public boolean isWareHousePageOpened() {
        return driver.getCurrentUrl().contains("ViewBookingOrder");
    }


   // ===== COMMON METHOD =====
public void processStatus(String dropdownValue, By actionButton) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    // ===== Step 1 - Wait Overlay =====
    waitForOverlay();

    // ===== Step 2 - Select Dropdown =====
    WebElement dropdownElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(statusDropdown));

    Select select = new Select(dropdownElement);
    select.selectByVisibleText(dropdownValue);

    waitForOverlay();

    // ===== Step 3 - Click Search =====
    WebElement search = wait.until(
            ExpectedConditions.elementToBeClickable(searchBtn));

    ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", search);

    waitForOverlay();

    // ===== Step 4 - Select All Checkbox =====
    WebElement checkbox = wait.until(
            ExpectedConditions.elementToBeClickable(selectAllCheckbox));

    ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", checkbox);

    waitForOverlay();

    // ===== Step 5 - Click Action Button =====
    WebElement actionBtn = wait.until(
            ExpectedConditions.elementToBeClickable(actionButton));

    ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", actionBtn);

    // ===== Step 6 - Handle Popup =====
    handlePopup();

    waitForOverlay();

    System.out.println(dropdownValue + " process completed.");
}

    // ===== STEP 1 =====
    public void bookedToUnderFitting() {
        processStatus("Booked", underFittingBtn);
    }

    // ===== STEP 2 =====
    public void underFittingToReadyToDispatch() {
        processStatus("Under Fitting", readyToDispatchBtn);
    }

    // ===== STEP 3 =====
    public void readyToDispatchToDispatch() {
        processStatus("Ready To Dispatch", dispatchBtn);
    }
    
 // ===== FILL DISPATCH DETAILS =====
    public void fillDispatchDetails(String mode,
                                    String courierCompany,
                                    String amount,
                                    String trackingNo,
                                    String courierNote) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        waitForOverlay();

        // ===== Select Mode =====
        WebElement modeDropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(courierModeDropdown));

        Select modeSelect = new Select(modeDropdown);
        modeSelect.selectByVisibleText(mode);

        waitForOverlay();

        // ===== Select Courier Company =====
        WebElement companyDropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(courierCompanyDropdown));

        Select companySelect = new Select(companyDropdown);
        companySelect.selectByVisibleText(courierCompany);

        // ===== Enter Amount =====
        WebElement amountInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(amountField));

        amountInput.clear();
        amountInput.sendKeys(amount);

        // ===== Enter Tracking Number =====
        WebElement trackingInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(trackingNoField));

        trackingInput.clear();
        trackingInput.sendKeys(trackingNo);

        // ===== Enter Courier Note =====
        WebElement noteInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(courierNoteField));

        noteInput.clear();
        noteInput.sendKeys(courierNote);

        waitForOverlay();

        // ===== Click Submit =====
        WebElement submit = wait.until(
                ExpectedConditions.elementToBeClickable(submitBtn));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", submit);

        // ===== Handle Success Popup =====
        handlePopup();

        waitForOverlay();

        System.out.println("Dispatch details submitted successfully.");
    }
    

    // ===== HELPER =====
  private void waitForOverlay() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    try {

        wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));

    } catch (Exception e) {

        System.out.println("Overlay not visible.");
    }
}
    
    public void handlePopup() {

    try {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for popup
        WebElement popupText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(popupMessage));

        System.out.println("Popup Appeared : " + popupText.getText());

        // Click OK
        WebElement okBtn = wait.until(
                ExpectedConditions.elementToBeClickable(popupOkButton));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", okBtn);

        // Wait for popup disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(popupOkButton));

    } catch (Exception e) {

        System.out.println("No popup displayed.");
    }
}
    
}