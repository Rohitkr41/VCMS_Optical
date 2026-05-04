
package pages.opticalTransaction;

import pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class OpticalBookingPage extends BasePage {

    public OpticalBookingPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By opticalTransactionsMenu = By.xpath("//span[normalize-space()='Optical Transactions']");
    private By opticalBookingMenu = By.xpath("//a[normalize-space()='Optical Booking' or .//span[normalize-space()='Optical Booking']]");

    private By plusIcon = By.id("OpticalBooking_btnNewBooking");

    // VCMR Search button on Spectacle Booking popup
    private By vcmrSearchButton = By.id("OpticalBooking_btnSearchRecord");

    // ===== SEARCH VISION CENTER PATIENT POPUP LOCATORS =====
    private By searchVisionPopup = By.xpath("//*[contains(normalize-space(),'Search Vision Center Patient')]");
    private By fromDateField = By.id("OpticalBooking_txtFromDate_Patient");
    private By toDateField = By.id("OpticalBooking_txtToDate_Patient");
    private By visionPopupSearchButton = By.id("OpticalBooking_btnserchbtn");
    private By patientRecordTable = By.id("OpticalBooking_tblRecordSearchPatient");

    // All patient rows from search result table
    private By patientResultRows = By.xpath("//*[@id='OpticalBooking_tblRecordSearchPatient']//tr[td]");

    private By itemTypeDropdown = By.id("OpticalBooking_ddlItemCategory");

    private By overlay = By.id("V3MOverlay");

    // ===== ACTION METHODS =====
    public void openOpticalTransactionsMenu() {
        clickAfterOverlay(opticalTransactionsMenu);
    }

    public void clickOpticalBooking() {
        clickAfterOverlay(opticalBookingMenu);
        System.out.println("Optical Booking menu clicked successfully");
    }

    public void clickPlusIcon() {
        clickAfterOverlay(plusIcon);
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
        clickAfterOverlay(vcmrSearchButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchVisionPopup));
        System.out.println("VCMR search button clicked successfully");
    }

    private void waitForOverlayToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (Exception e) {
            // ignore if overlay is not present
        }
    }

    private void clickAfterOverlay(By locator) {
        waitForOverlayToDisappear();

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );

        waitForOverlayToDisappear();

        try {
            element.click();
        } catch (Exception e) {
            waitForOverlayToDisappear();
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }

        waitForOverlayToDisappear();
    }

    // ===== SEARCH VISION CENTER PATIENT ACTION =====
    public void searchVisionCenterPatientByDate(String fromDate, String toDate) {
        waitForOverlayToDisappear();

        wait.until(ExpectedConditions.visibilityOfElementLocated(searchVisionPopup));

        setDateByJS(fromDateField, fromDate);
        waitForOverlayToDisappear();

        setDateByJS(toDateField, toDate);
        waitForOverlayToDisappear();

        clickAfterOverlay(visionPopupSearchButton);

        wait.until(ExpectedConditions.visibilityOfElementLocated(patientRecordTable));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(patientResultRows));

        System.out.println("Vision Center Patient searched successfully from " + fromDate + " to " + toDate);
    }

    // ===== COMMON DATE SETTER =====
    private void setDateByJS(By locator, String dateValue) {
        waitForOverlayToDisappear();

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('readonly');" +
                "arguments[0].value = arguments[1];" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));" +
                "if (window.jQuery) { $(arguments[0]).trigger('change').trigger('blur'); }",
                element,
                dateValue
        );

        waitForOverlayToDisappear();
    }

    // ===== SELECT PARTICULAR PATIENT BY VCMR NO. =====
    public void selectPatientByVcmrNo(String vcmrNo) {
    waitForOverlayToDisappear();

    wait.until(ExpectedConditions.visibilityOfElementLocated(patientRecordTable));
    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(patientResultRows));

    WebElement patientRow = null;

    List<WebElement> rows = driver.findElements(patientResultRows);

    for (WebElement row : rows) {
        String rowText = row.getText().trim();

        System.out.println("Patient row found: " + rowText);

        if (rowText.contains(vcmrNo)) {
            patientRow = row;
            break;
        }
    }

    if (patientRow == null) {
        throw new RuntimeException("Patient not found with VCMR No: " + vcmrNo);
    }

    clickPatientSelectIconFromRow(patientRow);

    System.out.println("Patient selected successfully with VCMR No: " + vcmrNo);
}

    // ===== SELECT PARTICULAR PATIENT BY NAME + CONTACT =====
    public void selectPatientByNameAndContact(String patientName, String contactNo) {
        waitForOverlayToDisappear();

        wait.until(ExpectedConditions.visibilityOfElementLocated(patientRecordTable));

        WebElement patientRow = wait.until(driver -> {
            List<WebElement> rows = driver.findElements(patientResultRows);

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));

                // Based on screenshot:
                // Column 3 = Patient Name
                // Column 7 = Contact No.
                if (cells.size() >= 7) {
                    String actualPatientName = cells.get(2).getText().trim();
                    String actualContactNo = cells.get(6).getText().trim();

                    if (actualPatientName.equalsIgnoreCase(patientName)
                            && actualContactNo.equalsIgnoreCase(contactNo)) {
                        return row;
                    }
                }
            }

            return null;
        });

        clickPatientSelectIconFromRow(patientRow);

        System.out.println("Patient selected successfully: " + patientName + " / " + contactNo);
    }

   private void clickPatientSelectIconFromRow(WebElement patientRow) {
    waitForOverlayToDisappear();

    WebElement selectIcon = patientRow.findElement(
            By.xpath(".//td[last()]//a | .//td[last()]//button | .//td[last()]//*[self::span or self::i]")
    );

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            selectIcon
    );

    waitForOverlayToDisappear();

    try {
        selectIcon.click();
    } catch (Exception e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectIcon);
    }

    waitForOverlayToDisappear();
}


    public void selectItemType(String itemType) {
        waitForOverlayToDisappear();

        WebElement dropdownElement = wait.until(
                ExpectedConditions.elementToBeClickable(itemTypeDropdown)
        );

        Select select = new Select(dropdownElement);
        select.selectByVisibleText(itemType);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                "if (window.jQuery) { $(arguments[0]).trigger('change'); }",
                dropdownElement
        );

        waitForOverlayToDisappear();

        System.out.println("Item Type selected successfully: " + itemType);
    }
    
    
}

