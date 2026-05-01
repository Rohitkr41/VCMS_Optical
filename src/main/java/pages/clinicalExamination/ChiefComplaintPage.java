package pages.clinicalExamination;

import pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ChiefComplaintPage extends BasePage {

    public ChiefComplaintPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By chiefComplaintTab   = By.id("chiefComplaint-tab");

    // Chief Complaint (REAL SELECT)
    private By complaintDropdown   = By.id("CE_ddlChiefComplaint");

    // Eye (Select2)
    private By eyeDropdown         = By.id("CE_txtChiefComplaintEyeSubSection");
    private By select2SearchField  = By.xpath("//input[contains(@class,'select2-input')]");

    // Duration & Period (may be select/custom)
    private By durationDropdown    = By.id("CE_txtChiefComplaintDuration");
    private By periodDropdown      = By.id("CE_txtChiefComplaintPeriod");

    private By saveButton          = By.id("CE_btnChiefComplaint");
    private By chiefComplaintDiv   = By.id("divChiefComplaint");

    // ===== ACTION METHODS =====

    public void openChiefComplaintTab() {
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(chiefComplaintTab));

        if (!tab.getAttribute("class").contains("active")) {
            scrollAndClick(tab);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(chiefComplaintDiv));
    }

    // ===== CHIEF COMPLAINT (SELECT) =====
   public void selectChiefComplaint(String complaint) {
    WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(complaintDropdown));
    Select select = new Select(dropdown);
    select.selectByVisibleText(complaint.toUpperCase());
}

public void selectEye(String eye) {
    WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(eyeDropdown));
    Select select = new Select(dropdown);
    select.selectByVisibleText(eye);
}

public void selectDuration(String duration) {
    selectCustomOrNormalDropdown(durationDropdown, duration);
}

public void selectPeriod(String period) {
    selectCustomOrNormalDropdown(periodDropdown, period);
}

    // ===== UNIVERSAL DROPDOWN HANDLER =====
    private void selectCustomOrNormalDropdown(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        String tagName = element.getTagName();

        // ✅ If <select>
        if (tagName.equalsIgnoreCase("select")) {
            new Select(element).selectByVisibleText(value);
        } 
        // ✅ If custom dropdown
        else {
            element.click();

            By option = By.xpath("//li[normalize-space()='" + value + "']");
            WebElement optionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(option));

            optionElement.click();
        }
    }

    // ===== SAVE =====
    public void saveChiefComplaint() {
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        scrollAndClick(saveBtn);
        handleSuccessPopup();

        System.out.println("✅ Chief Complaint saved successfully!");
    }

    // ===== UTIL =====
    private void scrollAndClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", element);
    }

    // ===== COMPLETE FLOW =====
    public void fillChiefComplaint(String complaint, String eye, String duration, String period) {
        openChiefComplaintTab();
        selectChiefComplaint(complaint);
        selectEye(eye);
        selectDuration(duration);
        selectPeriod(period);
        saveChiefComplaint();
    }
    
    private By successPopup = By.id("popup_message");
    private By okButton     = By.id("popup_ok");
    
    public void handleSuccessPopup() {

   	    // Wait for popup visible
   	    WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));

   	    // Print message
   	    System.out.println("✅ Popup Message: " + popup.getText());

   	    // Click OK button
   	    WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(okButton));
   	    okBtn.click();

   	    // Wait for popup to disappear
   	    wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));

   	    System.out.println("✅ Popup handled successfully");
   	}
}