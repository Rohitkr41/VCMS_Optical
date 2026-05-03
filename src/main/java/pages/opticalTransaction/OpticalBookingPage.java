package pages.opticalTransaction;

import pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;



public class OpticalBookingPage extends BasePage {

    public OpticalBookingPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By opticalTransactionsMenu = By.xpath("//span[normalize-space()='Optical Transactions']");
    private By opticalBookingMenu = By.xpath("//a[normalize-space()='Optical Booking' or .//span[normalize-space()='Optical Booking']]");

    // Plus icon on Optical Booking page
    private By plusIcon = By.id("OpticalBooking_btnNewBooking");
    
 // VCMR Search button on Spectacle Booking popup
    private By vcmrSearchButton = By.id("OpticalBooking_btnSearchRecord");
    
 // ===== SEARCH VISION CENTER PATIENT POPUP LOCATORS =====
    private By searchVisionPopup = By.xpath("//*[contains(normalize-space(),'Search Vision Center Patient')]");
    private By fromDateField = By.id("OpticalBooking_txtFromDate_Patient");
    private By toDateField = By.id("OpticalBooking_txtToDate_Patient");
    private By visionPopupSearchButton = By.id("OpticalBooking_btnserchbtn");
    private By firstPatientRecord = By.id("OpticalBooking_tblRecordSearchPatient");
    
 // Red patient select icon in search result table
    private By firstPatientSelectIcon = By.xpath("//*[@id=\"row1\"]/td[27]/a/span");
    
    private By itemTypeDropdown = By.id("OpticalBooking_ddlItemCategory");




    // ===== ACTION METHODS =====
    public void openOpticalTransactionsMenu() {
        click(opticalTransactionsMenu);
    }

    public void clickOpticalBooking() {
        click(opticalBookingMenu);
        System.out.println("Optical Booking menu clicked successfully");
    }

    public void clickPlusIcon() {
        click(plusIcon);
        System.out.println("Optical Booking plus icon clicked successfully");
    }

    public void openOpticalBookingPage() {
        openOpticalTransactionsMenu();
        clickOpticalBooking();
    }

    public void openAddOpticalBookingPage() {
        openOpticalBookingPage();
        clickPlusIcon();
    }
    
    public void clickVcmrSearchButton() {
        click(vcmrSearchButton);
        System.out.println("VCMR search button clicked successfully");
    }
    
    
 // ===== SEARCH VISION CENTER PATIENT ACTION =====
   public void searchVisionCenterPatientByDate(String fromDate, String toDate) {

    wait.until(ExpectedConditions.visibilityOfElementLocated(searchVisionPopup));

    setDateByJS(fromDateField, fromDate);
    setDateByJS(toDateField, toDate);

    // ✅ WAIT FOR OVERLAY TO DISAPPEAR
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("V3MOverlay")));

    WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(visionPopupSearchButton));
    searchBtn.click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(firstPatientRecord));

    System.out.println("Vision Center Patient searched successfully from " + fromDate + " to " + toDate);
}

    // ===== COMMON DATE SETTER =====
    private void setDateByJS(By locator, String dateValue) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].value = arguments[1];" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                element,
                dateValue
        );
    
    }
    
    public void clickFirstPatientSelectIcon() {
        WebElement selectIcon = wait.until(ExpectedConditions.elementToBeClickable(firstPatientSelectIcon));
        selectIcon.click();

        System.out.println("First patient red select icon clicked successfully");
    }
    
    public void selectItemType(String itemType) {
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(itemTypeDropdown));

        Select select = new Select(dropdownElement);
        select.selectByVisibleText(itemType);

        System.out.println("Item Type selected successfully: " + itemType);
    }


    
}
