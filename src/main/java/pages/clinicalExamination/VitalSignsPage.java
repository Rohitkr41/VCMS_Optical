package pages.clinicalExamination;

import pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VitalSignsPage extends BasePage {

    public VitalSignsPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By vitalSignsTab = By.xpath("//a[@id='vitalSigns-tab' or normalize-space()='Vital Signs']");
    private By vitalSignsDiv = By.xpath("//*[contains(.,'VITAL SIGNS')]/ancestor::div[contains(@class,'panel') or contains(@class,'tab-pane')][1]");

    private By bpDiastolicInput = By.xpath("//label[normalize-space()='BP Diastolic']/following::input[1]");
    private By bpSystolicInput  = By.xpath("//label[normalize-space()='BP Systolic']/following::input[1]");
    private By pulseInput       = By.xpath("//label[normalize-space()='Pulse']/following::input[1]");
    private By randomSugarInput = By.xpath("//label[normalize-space()='Random Sugar']/following::input[1]");

    private By diabeticStatusDropdown = By.id("CE_ddlVitalSigns_DiabeticStatus");

    private By saveButton   = By.id("CE_btnAddUpadateVitalSigns");
    private By successPopup = By.xpath("//*[@id='popup_message']");
    private By okButton     = By.xpath("//*[@id='popup_ok']");

    // ===== ACTION METHODS =====
    public void openVitalSignsTab() {
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(vitalSignsTab));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", tab
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);

        wait.until(ExpectedConditions.presenceOfElementLocated(bpDiastolicInput));
    }

    public void enterBpDiastolic(String value) {
        enterText(bpDiastolicInput, value);
    }

    public void enterBpSystolic(String value) {
        enterText(bpSystolicInput, value);
    }

    public void enterPulse(String value) {
        enterText(pulseInput, value);
    }

    public void enterRandomSugar(String value) {
        enterText(randomSugarInput, value);
    }

    public void selectDiabeticStatus(String status) {
        selectDropdownByText(diabeticStatusDropdown, status);
    }

    private void enterText(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element
        );

        element.clear();
        element.sendKeys(value);
    }

    public void selectDropdownByText(By locator, String visibleText) {
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

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

    public void saveVitalSigns() {
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", saveBtn
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);

        handleSuccessPopup();

        System.out.println("Vital Signs saved successfully!");
    }

    public void handleSuccessPopup() {
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
        System.out.println("Popup Message: " + popup.getText());

        WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(okButton));
        okBtn.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
    }

    public void fillVitalSigns(String bpDiastolic, String bpSystolic, String pulse,
                               String randomSugar, String diabeticStatus) {
        openVitalSignsTab();
        enterBpDiastolic(bpDiastolic);
        enterBpSystolic(bpSystolic);
        enterPulse(pulse);
        enterRandomSugar(randomSugar);
        selectDiabeticStatus(diabeticStatus);
        saveVitalSigns();
    }
}
