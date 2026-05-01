package pages.clinicalExamination;

import pages.BasePage;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DiagnosisAndAdvisePage extends BasePage {

    public DiagnosisAndAdvisePage(WebDriver driver) {
        super(driver);
    }

    // ===== TAB =====
    private By diagnosisTab = By.id("adviseDiagnosis-tab");
    
    private By successPopup = By.id("popup_message");
    private By okButton = By.id("popup_ok");

    // =========================
    // ===== EYE DIAGNOSIS =====
    // =========================
    private By eyeDiagnosisDropdown = By.id("s2id_CE_ddlDiagnosis");
    private By eyeDropdown = By.id("CE_ddlDiagnosisEye");
    private By eyeSaveBtn = By.id("CE_btnAddUpdateDiagnosis");

    // =========================
    // ===== EAR DIAGNOSIS =====
    // =========================
    private By earDiagnosisDropdown = By.id("s2id_CE_ddlDiagnosisEar");
    private By earDropdown = By.id("CE_ddlEarType");
    private By earSaveBtn = By.id("CE_btnAddUpdateDiagnosisEar");

    // =========================
    // ===== PATIENT ADVICE =====
    // =========================
    private By adviseForEyes = By.id("s2id_CE_ddlAdvisedFor");
    private By prescribeSpectaclesCheckbox = By.id("CE_chkPatientAdviseSpectacles");
    private By prescribeSpectacles = By.id("s2id_CE_ddlAdvisedForSpectaclesType");   
    private By adviceEyeSaveBtn = By.id("CE_btnAddUpdateAdvise");
    
    private By adviseForEars = By.id("s2id_CE_ddlAdvisedForEar");
    private By earAdviceDropdown = By.id("CE_ddlInterventionEarType");

    private By adviceSaveBtn = By.id("CE_btnAddUpdateEarAdvise");

    // =========================
    // ===== REFERRAL SECTION =====
    // =========================
    private By referToHospitalCheckbox = By.id("CE_chkPatientAdviseReferred");
 // 🔥 NEW locator (parent container)
//    private By referralSection = By.id("CE_chkPatientAdviseReferred"); // 👈 actual id check karo

    private By baseHospitalRadio = By.id("CE_rdbEyeHospitalSelf");
    private By crossPartnerRadio = By.id("CE_rdbEyeHospitalCrossPartner");

    private By hospitalVisitDate = By.id("CE_HospitalVisitDate");
    private By locationDropdown = By.id("CE_ddlLocation");
    private By eccNameDropdown = By.id("s2id_CE_ddlHospitalName");

    private By remarks = By.id("CE_txtOtherRemarks");

    private By hospitalSaveBtn = By.id("CE_btnAddUpdateReferralToHospital");
    
    private By holdBtn = By.id("CE_btnHoldClinicalData");        // 👈 verify id once

 // Example fields inside HOLD (inspect & update IDs)
 private By holdReasonDropdown = By.id("CE_ddlholdReason");
 private By holdRemarks = By.id("CE_txtholdDescription");

 private By holdSaveBtn = By.id("CE_btnModalHoldPatient");
 
 private By closedBtn = By.id("CE_btnModalHoldClose");
 
    private By checkoutBtn = By.id("CE_btnClinicalCheckout"); // 👈 verify id once

    // =========================
    // ===== COMMON METHODS =====
    // =========================

    private void selectDropdown(By locator, String value) {

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", dropdown);

        try {
            Select select = new Select(dropdown);
            select.selectByVisibleText(value);
        } catch (Exception e) {

            // JS fallback
            String script =
                    "var select = arguments[0];" +
                    "for (var i = 0; i < select.options.length; i++) {" +
                    " if (select.options[i].text.trim() === arguments[1]) {" +
                    "   select.selectedIndex = i;" +
                    "   select.dispatchEvent(new Event('change')); break;}}";

            ((JavascriptExecutor) driver).executeScript(script, dropdown, value);
        }
    }

    public void type(By locator, String value) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(value);
    }

    protected void click(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);
        el.click();
    }
    
    
    public void handleSuccessPopup() {
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
        System.out.println("✅ Popup: " + popup.getText());
        wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
    }

    // =========================
    // ===== TAB OPEN =====
    // =========================
    public void openDiagnosisTab() {

        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(diagnosisTab));

        // 🔥 WAIT until visible
        wait.until(ExpectedConditions.visibilityOf(tab));

        // 🔥 WAIT until clickable
        wait.until(ExpectedConditions.elementToBeClickable(tab));

        // 🔥 Scroll
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", tab);

        try {
            tab.click();
        } catch (Exception e) {

            System.out.println("⚠️ Normal click failed, using JS click");

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", tab);
        }

        // 🔥 EXTRA WAIT (tab content load)
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        System.out.println("🟢 Diagnosis tab opened");
    }

    // =========================
    // ===== EYE DIAGNOSIS =====
    // =========================
    public void addEyeDiagnosis(String diagnosis, String eye) {

    	selectSelect2Dropdown(eyeDiagnosisDropdown, diagnosis);
        selectDropdown(eyeDropdown, eye);

        click(eyeSaveBtn);
        handleSuccessPopup();

        System.out.println("🟢 Eye Diagnosis Added");
    }

    // =========================
    // ===== EAR DIAGNOSIS =====
    // =========================
    public void addEarDiagnosis(String diagnosis, String ear) {

    	selectSelect2Dropdown(earDiagnosisDropdown, diagnosis);
        selectDropdown(earDropdown, ear);

        click(earSaveBtn);
        handleSuccessPopup();

        System.out.println("🟢 Ear Diagnosis Added");
    }

    // =========================
    // ===== ADVICE SECTION =====
    // =========================
  public void enterEyeAdvice(String advice, boolean spectacles, String spectacleType) {

    // ✅ Step 1: Check if advice already filled
    WebElement adviceField = wait.until(ExpectedConditions.presenceOfElementLocated(adviseForEyes));
    String existingAdvice = adviceField.getText();

    // ✅ Step 2: Check checkbox state
    WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(prescribeSpectaclesCheckbox));
    boolean isChecked = checkbox.isSelected();

    // =====================================================
    // 🔥 CONDITION: Skip if already filled + checkbox checked
    // =====================================================
    if (!existingAdvice.isEmpty() && isChecked) {

        System.out.println("⚠️ Advice already present & checkbox checked → Skipping Eye Advice section");
        return; // 👉 सीधे Ear Advice पर जाएगा
    }

    // =====================================================
    // ✅ NORMAL FLOW
    // =====================================================

    // Select Eye Advice
    selectSelect2Dropdown(adviseForEyes, advice);

    if (spectacles) {

        // Scroll
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", checkbox);

        // Click checkbox (JS for reliability)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);

        System.out.println("🟢 Checkbox clicked");

        // Fill dropdown ONLY if value provided
        if (spectacleType != null && !spectacleType.trim().isEmpty()) {

            wait.until(ExpectedConditions.elementToBeClickable(prescribeSpectacles));
            selectSelect2Dropdown(prescribeSpectacles, spectacleType);

            System.out.println("🟢 Spectacle Type Selected: " + spectacleType);
        }
    }

    // Save
    click(adviceEyeSaveBtn);
    handleSuccessPopup();

    System.out.println("🟢 Eye Advice Saved");
}

    public void enterEarAdvice(String advice, String ear) {

    	selectSelect2Dropdown(adviseForEars, advice);
        selectByVisibleText(earAdviceDropdown, ear);
    }

    public void saveAdvice() {
        click(adviceSaveBtn);
        handleSuccessPopup();
        
    }

    // =========================
    // ===== REFERRAL SECTION =====
    // =========================
    
 public void referToHospital(
        boolean refer,
        String hospitalType,
        String visitDate,
        String location,
        String ecc,
        String remarksText
) throws InterruptedException {

    if (!refer) return;

    // STEP 0: Close overlays
    try {
        driver.findElement(By.tagName("body")).click();
        Thread.sleep(200);
    } catch (Exception ignored) {}

    // STEP 1: Locate checkbox
    WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(referToHospitalCheckbox));

    // =====================================================
    // 🔥 NEW CONDITION: अगर पहले से checked है → skip + checkout
    // =====================================================
    if (checkbox.isSelected()) {

        System.out.println("⚠️ Refer to Hospital already checked → Skipping form");

        clickCheckout();   // 👈 Direct checkout
        return;
    }

    // STEP 2: क्लिक checkbox (अगर पहले unchecked था)
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);

    wait.until(driver -> checkbox.isSelected());
    System.out.println("🟢 Checkbox checked");

    // STEP 3: Wait popup gone
    try {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("popup_message")));
    } catch (Exception ignored) {}

    // STEP 4: Select hospital type
    By radioLocator = hospitalType.equalsIgnoreCase("Base") ? baseHospitalRadio : crossPartnerRadio;
    WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(radioLocator));

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", radio
    );

    ((JavascriptExecutor) driver).executeScript(
            "var elem = arguments[0];" +
            "var rect = elem.getBoundingClientRect();" +
            "var elAtPoint = document.elementFromPoint(rect.left + rect.width/2, rect.top + rect.height/2);" +
            "if(elAtPoint === elem || elem.contains(elAtPoint)){ elem.click(); }",
            radio
    );

    wait.until(driver -> radio.isSelected());
    System.out.println("🟢 Hospital type selected: " + hospitalType);

    // STEP 5: Date
    WebElement date = wait.until(ExpectedConditions.visibilityOfElementLocated(hospitalVisitDate));
    date.clear();
    date.sendKeys(visitDate);
    date.sendKeys(Keys.ESCAPE);

    Thread.sleep(200);

    // STEP 6: Location (only Base)
    if (hospitalType.equalsIgnoreCase("Base")) {

        try {
            if (driver.findElement(locationDropdown).isDisplayed()) {

                System.out.println("📍 Filling Location (Base Hospital)");
                selectDropdown(locationDropdown, location);

                driver.findElement(By.tagName("body")).click();
                Thread.sleep(200);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Location not visible, skipping");
        }

    } else {
        System.out.println("⏭️ Skipping Location (Cross Partner)");
    }

    // STEP 7: ECC
    selectSelect2Dropdown(eccNameDropdown, ecc);

    // STEP 8: Remarks
    type(remarks, remarksText);

    // STEP 9: Save
    click(hospitalSaveBtn);
    handleSuccessPopup();

    System.out.println("🏥 Referral Saved ✅");

    // STEP 10: Checkout after save
    clickCheckout();
}
 
 public void handleHold(String reason, String remarksText) {

    System.out.println("🟡 Starting HOLD flow");

    // STEP 1: Click HOLD button
    WebElement hold = wait.until(ExpectedConditions.elementToBeClickable(holdBtn));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", hold);

    try {
        hold.click();
    } catch (Exception e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", hold);
    }

    System.out.println("🟡 Hold button clicked");

    // STEP 2: Check if success popup appears directly (No form case)
    if (isElementVisible(successPopup, 2)) {
        handleSuccessPopup();
        System.out.println("🟡 Hold completed (No form)");
        return;
    }

    // STEP 3: Fill Hold Reason
    try {
//        WebElement reasonDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(holdReasonDropdown));
        selectDropdown(holdReasonDropdown, reason);
        System.out.println("📝 Hold Reason selected");
    } catch (Exception e) {
        System.out.println("⚠️ Hold Reason not visible");
    }

    // STEP 4: Fill Remarks
    try {
        WebElement remarks = wait.until(ExpectedConditions.visibilityOfElementLocated(holdRemarks));
        remarks.clear();
        remarks.sendKeys(remarksText);
        System.out.println("📝 Hold Remarks entered");
    } catch (Exception e) {
        System.out.println("⚠️ Hold Remarks not visible");
    }

    // STEP 5: Click HOLD (Save)
    try {
        WebElement save = wait.until(ExpectedConditions.elementToBeClickable(holdSaveBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", save);

        try {
            save.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", save);
        }

        System.out.println("🟡 Hold Save clicked");

    } catch (Exception e) {
        System.out.println("❌ Hold Save button not found");
        return;
    }

    // STEP 6: Handle success popup
    handleSuccessPopup();
    System.out.println("🟡 Hold Saved Successfully");

    // STEP 7: Ensure modal closes (auto OR using your Close button)
//    By modal = By.id("holdModal"); // update if needed

 // ✅ Use YOUR Close Button (robust click)
    try {
        WebElement closeBtnElement = wait.until(ExpectedConditions.visibilityOfElementLocated(closedBtn));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", closeBtnElement);

        // Wait until clickable
        wait.until(ExpectedConditions.elementToBeClickable(closeBtnElement));

        try {
            closeBtnElement.click();
        } catch (Exception e) {
            System.out.println("⚠️ Normal click failed, using JS click");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtnElement);
        }

        System.out.println("🟡 Modal closed using Close button (ID)");

        // Wait for modal to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("holdModal")));

    } catch (Exception e) {
        System.out.println("❌ Failed to close modal using Close button");
    }
}
 
 
 
 
 public boolean isElementVisible(By locator, int timeoutSec) {
	    try {
	        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
	        return shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
	    } catch (Exception e) {
	        return false;
	    }
	}
 
 public void clickCheckout() {

	    WebElement checkout = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});", checkout);

	    try {
	        checkout.click();
	    } catch (Exception e) {
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkout);
	    }

	    System.out.println("🔴 Checkout button clicked");

	    // Handle popup if appears
	    try {
	        handleSuccessPopup();
	    } catch (Exception ignored) {}
	}

    // =========================
    // ===== COMPLETE FLOW =====
    // =========================
   public void completeDiagnosisFlow(

        String eyeDiagnosis,
        String eye,

        String earDiagnosis,
        String earVal,

        String eyeAdvice,
        boolean spectacles,
        String spectacleType,   // 👈 NEW PARAM
        String earAdvice,
        String earAdviceSide,

        boolean refer,
        String hospitalType,
        String visitDate,
        String location,
        String ecc,
        String remarksText
) throws InterruptedException {

    openDiagnosisTab();

    addEyeDiagnosis(eyeDiagnosis, eye);
    addEarDiagnosis(earDiagnosis, earVal);

    enterEyeAdvice(eyeAdvice, spectacles, spectacleType); // 👈 updated
    enterEarAdvice(earAdvice, earAdviceSide);

    saveAdvice();

    referToHospital(refer, hospitalType, visitDate, location, ecc, remarksText);
    
 // ✅ FIXED: pass String values
    handleHold("TELECONSULTATION", "Will visit later");
    clickCheckout();
    
}
    
    private void selectSelect2Dropdown(By locator, String value) {

        WebElement container = wait.until(ExpectedConditions.elementToBeClickable(locator));
        container.click();

        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("select2-drop")));

        // 🔥 Type value
        try {
            WebElement searchBox = dropdown.findElement(By.cssSelector("input.select2-input"));
            searchBox.clear();
            searchBox.sendKeys(value);
        } catch (Exception ignored) {}

        // 🔥 BETTER XPATH (contains + normalize-space)
        By option = By.xpath(
                "//div[@id='select2-drop']//li[contains(@class,'select2-result-selectable')]//div[contains(normalize-space(),'" + value + "')]"
        );

        WebElement optionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(option));

        // 🔥 Scroll into view (important)
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", optionElement);

        // 🔥 Click with fallback
        try {
            optionElement.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", optionElement);
        }

        System.out.println("🟢 Selected: " + value);
    }
}