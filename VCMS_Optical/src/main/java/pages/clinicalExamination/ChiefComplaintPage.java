package pages.clinicalExamination;

import pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ChiefComplaintPage extends BasePage {

    public ChiefComplaintPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By chiefComplaintTab = By.xpath("//a[@id='chiefComplaint-tab' or normalize-space()='Chief Complaint']");
    private By chiefComplaintDiv = By.xpath("//*[@id='divChiefComplaint']");

    private By complaintDropdown = By.xpath("//select[@id='CE_ddlChiefComplaint']");
    private By eyeDropdown       = By.xpath("//*[@id='CE_txtChiefComplaintEyeSubSection']");
    private By durationDropdown  = By.xpath("//*[@id='CE_txtChiefComplaintDuration']");
    private By periodDropdown    = By.xpath("//*[@id='CE_txtChiefComplaintPeriod']");

    private By saveButton        = By.xpath("//*[@id='CE_btnChiefComplaint']");
    private By successPopup      = By.xpath("//*[@id='popup_message']");
    private By okButton          = By.xpath("//*[@id='popup_ok']");

    // ===== ACTION METHODS =====
    public void openChiefComplaintTab() {
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(chiefComplaintTab));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", tab
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);

        wait.until(ExpectedConditions.visibilityOfElementLocated(chiefComplaintDiv));
    }

    public void selectChiefComplaint(String complaint) {
        selectHiddenDropdownByText(complaintDropdown, complaint);
    }

    public void selectEye(String eye) {
        selectDropdownByText(eyeDropdown, eye);
    }

    public void selectDuration(String duration) {
        selectDropdownByText(durationDropdown, duration);
    }

    public void selectPeriod(String period) {
        selectDropdownByText(periodDropdown, period);
    }

    private void selectHiddenDropdownByText(By dropdownLocator, String visibleText) {
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(dropdownLocator));

        String script =
                "var select = arguments[0];" +
                "var text = arguments[1].trim().toUpperCase();" +
                "var matched = false;" +
                "for (var i = 0; i < select.options.length; i++) {" +
                "   if (select.options[i].text.trim().toUpperCase() === text) {" +
                "       select.value = select.options[i].value;" +
                "       matched = true;" +
                "       break;" +
                "   }" +
                "}" +
                "if (!matched) { throw 'Option not found: ' + arguments[1]; }" +
                "if (window.jQuery) {" +
                "   $(select).trigger('change');" +
                "} else {" +
                "   select.dispatchEvent(new Event('change', { bubbles: true }));" +
                "}";

        ((JavascriptExecutor) driver).executeScript(script, dropdown, visibleText);
    }

    public void selectDropdownByText(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        String tagName = element.getTagName();

        if (tagName.equalsIgnoreCase("select")) {
            selectHiddenDropdownByText(locator, value);
        } else {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", element
            );

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

            By option = By.xpath(
                    "//*[self::li or self::div or self::span][normalize-space()='" + value + "']"
            );

            WebElement optionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(option));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", optionElement);
        }
    }

    public void saveChiefComplaint() {
        WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(saveButton));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", saveBtn
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);

        handleSuccessPopup();

        System.out.println("Chief Complaint saved successfully!");
    }

    public void handleSuccessPopup() {
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
        System.out.println("Popup Message: " + popup.getText());

        WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(okButton));
        okBtn.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
    }

    public void fillChiefComplaint(String complaint, String eye, String duration, String period) {
        openChiefComplaintTab();
        selectChiefComplaint(complaint);
        selectEye(eye);
        selectDuration(duration);
        selectPeriod(period);
        saveChiefComplaint();
    }
}
