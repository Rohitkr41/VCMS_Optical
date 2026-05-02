package pages.clinicalExamination;

import pages.BasePage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DiagnosisAndAdvisePage extends BasePage {

    public DiagnosisAndAdvisePage(WebDriver driver) {
        super(driver);
    }

    // ===== TAB =====
    private final By diagnosisTab = By.id("adviseDiagnosis-tab");

    // ===== POPUP =====
    private final By successPopup = By.id("popup_message");
    private final By okButton = By.id("popup_ok");

    // ===== EYE DIAGNOSIS =====
    private final By eyeDiagnosisDropdown = By.id("s2id_CE_ddlDiagnosis");
    private final By eyeDropdown = By.id("CE_ddlDiagnosisEye");
    private final By eyeSaveBtn = By.id("CE_btnAddUpdateDiagnosis");

    // ===== PATIENT STATUS & ADVISE =====
    private final By followUpInVisionCenterCheckbox = By.id("CE_chkPatientAdviseNormal");
    private final By referToHospitalCheckbox = By.id("CE_chkPatientAdviseReferred");
    private final By prescribeSpectaclesCheckbox = By.id("CE_chkPatientAdviseSpectacles");
    private final By adviseFor = By.id("s2id_CE_ddlAdvisedFor");
    private final By spectaclesType = By.id("CE_ddlAdvisedForSpectaclesType");
    private final By otherRemarks = By.id("CE_txtOtherAdvised");
    private final By hospitalVisitDateCheckbox = By.id("chkNextVisitDate");
    private final By hospitalVisitDate = By.id("CE_NextVisitDate");
    private final By patientStatusSaveBtn = By.id("CE_btnAddUpdateAdvise");

    // ===== HOLD =====
    private final By holdBtn = By.id("CE_btnHoldClinicalData");
    private final By holdReasonDropdown = By.id("CE_ddlholdReason");
    private final By holdRemarks = By.id("CE_txtholdDescription");
    private final By holdSaveBtn = By.id("CE_btnModalHoldPatient");
    private final By holdCloseBtn = By.id("CE_btnModalHoldClose");
    private final By holdModal = By.id("holdModal");

    // ===== CHECKOUT =====
    private final By checkoutBtn = By.id("CE_btnClinicalCheckout");

    public void openDiagnosisTab() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(diagnosisTab));
        scrollToCenter(tab);
        clickElement(tab);
        System.out.println("Diagnosis tab opened");
    }

    public void addEyeDiagnosis(String diagnosis, String eye) {
        if (diagnosis != null && !diagnosis.trim().isEmpty()) {
            selectSelect2Dropdown(eyeDiagnosisDropdown, diagnosis);
        }

        if (eye != null && !eye.trim().isEmpty()) {
            selectDropdownByVisibleText(eyeDropdown, eye);
        }

        click(eyeSaveBtn);
        handleSuccessPopupIfPresent();
        System.out.println("Eye Diagnosis added");
    }

    public void enterPatientStatusAndAdvise(
            boolean followUpInVisionCenter,
            boolean referToHospital,
            boolean prescribeSpectacles,
            String advice,
            String spectacleType,
            String remarksText,
            boolean setHospitalVisitDate,
            String visitDate
    ) {
        setCheckbox(followUpInVisionCenterCheckbox, followUpInVisionCenter);
        setCheckbox(referToHospitalCheckbox, referToHospital);
        setCheckbox(prescribeSpectaclesCheckbox, prescribeSpectacles);

        if (advice != null && !advice.trim().isEmpty()) {
            selectSelect2MultiDropdown(adviseFor, advice);
        }

        if (prescribeSpectacles && spectacleType != null && !spectacleType.trim().isEmpty()) {
            selectDropdownByVisibleText(spectaclesType, spectacleType);
        }

        if (remarksText != null) {
            type(otherRemarks, remarksText);
        }

        setCheckbox(hospitalVisitDateCheckbox, setHospitalVisitDate);
        if (setHospitalVisitDate && visitDate != null && !visitDate.trim().isEmpty()) {
            typeDate(hospitalVisitDate, visitDate);
        }

        click(patientStatusSaveBtn);
        handleSuccessPopupIfPresent();
        System.out.println("Patient Status & Advise saved");
    }

    public void handleHold(String reason, String remarksText) {
        click(holdBtn);

        if (isElementVisible(successPopup, 2)) {
            handleSuccessPopup();
            System.out.println("Hold completed");
            return;
        }

        if (reason != null && !reason.trim().isEmpty() && isElementVisible(holdReasonDropdown, 2)) {
            selectDropdownByVisibleText(holdReasonDropdown, reason);
        }

        if (remarksText != null && isElementVisible(holdRemarks, 2)) {
            type(holdRemarks, remarksText);
        }

        click(holdSaveBtn);
        handleSuccessPopupIfPresent();
        closeHoldModalIfOpen();
        System.out.println("Hold saved");
    }

    public void clickCheckout() {
        click(checkoutBtn);
        handleSuccessPopupIfPresent();
        System.out.println("Checkout clicked");
    }

    public void completeDiagnosisAndPatientStatusFlow(
            String eyeDiagnosis,
            String eye,
            boolean followUpInVisionCenter,
            boolean referToHospital,
            boolean prescribeSpectacles,
            String advice,
            String spectacleType,
            String remarksText,
            boolean setHospitalVisitDate,
            String visitDate,
            boolean holdPatient,
            String holdReason,
            String holdRemarksText
    ) {
        openDiagnosisTab();
        addEyeDiagnosis(eyeDiagnosis, eye);
        enterPatientStatusAndAdvise(
                followUpInVisionCenter,
                referToHospital,
                prescribeSpectacles,
                advice,
                spectacleType,
                remarksText,
                setHospitalVisitDate,
                visitDate
        );

        if (holdPatient) {
            handleHold(holdReason, holdRemarksText);
        } else {
            clickCheckout();
        }
    }

    public void completePatientStatusAndAdviseFlow(
            boolean followUpInVisionCenter,
            boolean referToHospital,
            boolean prescribeSpectacles,
            String advice,
            String spectacleType,
            String remarksText,
            boolean setHospitalVisitDate,
            String visitDate,
            boolean holdPatient,
            String holdReason,
            String holdRemarksText
    ) {
        openDiagnosisTab();
        enterPatientStatusAndAdvise(
                followUpInVisionCenter,
                referToHospital,
                prescribeSpectacles,
                advice,
                spectacleType,
                remarksText,
                setHospitalVisitDate,
                visitDate
        );

        if (holdPatient) {
            handleHold(holdReason, holdRemarksText);
        } else {
            clickCheckout();
        }
    }

    public void type(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        scrollToCenter(element);
        element.clear();
        element.sendKeys(value);
    }

    private void typeDate(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        scrollToCenter(element);
        if (!element.isEnabled()) {
            System.out.println("Date field disabled, skipping: " + locator);
            return;
        }
        element.clear();
        element.sendKeys(value);
        element.sendKeys(Keys.ESCAPE);
    }

    protected void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollToCenter(element);
        clickElement(element);
    }

    private void setCheckbox(By locator, boolean shouldBeChecked) {
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        scrollToCenter(checkbox);

        if (!checkbox.isEnabled()) {
            System.out.println("Checkbox disabled, skipping: " + locator);
            return;
        }

        if (checkbox.isSelected() != shouldBeChecked) {
            clickElement(checkbox);
            wait.until(driver -> checkbox.isSelected() == shouldBeChecked);
        }
    }

    private void selectDropdownByVisibleText(By locator, String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollToCenter(dropdown);
        Select select = new Select(dropdown);

        try {
            select.selectByVisibleText(value);
            return;
        } catch (Exception ignored) {
            // Fallback below supports partial visible text like "SINGLE VISION GLASSES".
        }

        for (WebElement option : select.getOptions()) {
            if (option.getText().trim().contains(value.trim())) {
                select.selectByVisibleText(option.getText().trim());
                return;
            }
        }

        throw new NoSuchElementException("Dropdown option not found: " + value);
    }

    private void selectSelect2Dropdown(By locator, String value) {
        WebElement container = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollToCenter(container);
        clickElement(container);

        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("select2-drop")));

        try {
            WebElement searchBox = dropdown.findElement(By.cssSelector("input.select2-input"));
            searchBox.clear();
            searchBox.sendKeys(value);
        } catch (Exception ignored) {
            // Some Select2 fields do not expose a search box.
        }

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@id='select2-drop']//li[contains(@class,'select2-result-selectable')]"
                        + "//div[contains(normalize-space(),\"" + value + "\")]"
        )));

        scrollToCenter(option);
        clickElement(option);
    }

    private void selectSelect2MultiDropdown(By locator, String value) {
        WebElement container = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollToCenter(container);

        if (isSelect2ValueAlreadySelected(container, value)) {
            System.out.println("Advise already selected: " + value);
            return;
        }

        WebElement input = container.findElement(By.cssSelector("input.select2-input"));
        clickElement(input);
        input.clear();
        input.sendKeys(value);

        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("select2-drop")));

        By optionLocator = By.xpath(
                "//div[@id='select2-drop']//li[contains(@class,'select2-result-selectable')]"
                        + "//div[contains(normalize-space(),\"" + value + "\")]"
        );

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        scrollToCenter(option);
        clickElement(option);
        System.out.println("Advise selected: " + value);
    }

    private boolean isSelect2ValueAlreadySelected(WebElement container, String value) {
        for (WebElement selected : container.findElements(By.cssSelector(".select2-search-choice div"))) {
            if (selected.getText().trim().equalsIgnoreCase(value.trim())) {
                return true;
            }
        }
        return false;
    }

    public void handleSuccessPopup() {
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
        System.out.println("Popup: " + popup.getText());
        wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
    }

    private void handleSuccessPopupIfPresent() {
        if (isElementVisible(successPopup, 3)) {
            handleSuccessPopup();
        }
    }

    private boolean isElementVisible(By locator, int timeoutSec) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void closeHoldModalIfOpen() {
        if (!isElementVisible(holdModal, 2)) {
            return;
        }

        try {
            click(holdCloseBtn);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(holdModal));
        } catch (Exception e) {
            System.out.println("Hold modal close button was not available");
        }
    }

    private void scrollToCenter(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);
    }

    private void clickElement(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
}
