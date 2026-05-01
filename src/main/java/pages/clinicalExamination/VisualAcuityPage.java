
package pages.clinicalExamination;

import pages.BasePage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VisualAcuityPage extends BasePage {

    public VisualAcuityPage(WebDriver driver) {
        super(driver);
    }

    // ===== TAB =====
    private By visualAcuityTab = By.id("visualAcuityRefraction-tab");

    // ===== TOP SECTION =====
    private By spectaclesDropdown = By.id("CE_ddlVisualActuityWearingSpecs");
    private By specAgeYears = By.id("CE_txtUsingSpectaclesYear");
    private By specAgeMonths = By.id("CE_txtUsingSpectaclesMonth");
    private By purposeField = By.id("CE_txtVisualAcuitySpecsPrupose");

    // ===== RIGHT EYE =====
    private By reUnaided = By.id("s2id_CE_ddl_RE_Unaided");
    private By reWithSpecs = By.id("s2id_CE_ddl_RE_WithSpecs");
    private By reWithPH = By.id("s2id_CE_ddl_RE_WithPH");
    private By reNearVision = By.id("s2id_CE_ddl_RE_NearVision");

    // ===== LEFT EYE =====
    private By leUnaided = By.id("s2id_CE_ddl_LE_Unaided");
    private By leWithSpecs = By.id("s2id_CE_ddl_LE_WithSpecs");
    private By leWithPH = By.id("s2id_CE_ddl_LE_WithPH");
    private By leNearVision = By.id("s2id_CE_ddl_LE_NearVision");

    // ===== SAVE BUTTON =====
    private By saveButton = By.id("CE_btnAddUpadateVisualAcuity");

    // ===== OTHER DIAGNOSTIC =====


    private By contrastField = By.id("CECV_txtContrastTest");
    private By otherSaveButton = By.id("CE_btnAddUpdateColorVision");

    // ===== POPUP =====
    private By successPopup = By.id("popup_message");
    private By okButton = By.id("popup_ok");

    // ===== RE FRACTION TAB =====
    private By rePGSph = By.id("s2id_CE_ddlRefraction_RE_PGP_SPH");
    private By rePGCyl = By.id("s2id_CE_ddlRefraction_RE_PGP_CYL");
    private By rePGAxis = By.id("s2id_CE_ddlRefraction_RE_PGP_Axis");
    private By rePGAdd = By.id("s2id_CE_ddlRefraction_RE_PGP_Add");

    private By reFinalSph = By.id("s2id_CE_ddlRefraction_RE_Acceptance_SPH");
    private By reFinalCyl = By.id("s2id_CE_ddlRefraction_RE_Acceptance_CYL");
    private By reFinalAxis = By.id("s2id_CE_ddlRefraction_RE_Acceptance_Axis");
    private By reFinalAdd = By.id("s2id_CE_ddlRefraction_RE_Acceptance_Add");

    private By lePGSph = By.id("s2id_CE_ddlRefraction_LE_PGP_SPH");
    private By lePGCyl = By.id("s2id_CE_ddlRefraction_LE_PGP_CYL");
    private By lePGAxis = By.id("s2id_CE_ddlRefraction_LE_PGP_Axis");
    private By lePGAdd = By.id("s2id_CE_ddlRefraction_LE_PGP_Add");

    private By leFinalSph = By.id("s2id_CE_ddlRefraction_LE_Acceptance_SPH");
    private By leFinalCyl = By.id("s2id_CE_ddlRefraction_LE_Acceptance_CYL");
    private By leFinalAxis = By.id("s2id_CE_ddlRefraction_LE_Acceptance_Axis");
    private By leFinalAdd = By.id("s2id_CE_ddlRefraction_LE_Acceptance_Add");

    private By refractionSaveBtn = By.id("CE_btnAddUpadateRefraction");

    
    
 // ===== DRY RETINOSCOPY VALUES =====
    private By reDrySph = By.id("s2id_CE_ddlRefraction_RE_DRY_SPH");
    private By reDryCyl = By.id("s2id_CE_ddlRefraction_RE_DRY_CYL");
    private By reDryAxis = By.id("s2id_CE_ddlRefraction_RE_DRY_Axis");

    private By leDrySph = By.id("s2id_CE_ddlRefraction_LE_DRY_SPH");
    private By leDryCyl = By.id("s2id_CE_ddlRefraction_LE_DRY_CYL");
    private By leDryAxis = By.id("s2id_CE_ddlRefraction_LE_DRY_Axis");
    
 // ===== DRY RETINOSCOPY =====
    private By reDryRemark = By.id("CE_txtRefraction_DRY_Remarks_RE");
    private By leDryRemark = By.id("CE_txtRefraction_DRY_Remarks_LE");


    // ===== CYCLOPLEGIC VALUES =====
    private By reCycloSphVal = By.id("s2id_CE_ddlRefraction_RE_DIALATE_SPH");
    private By reCycloCylVal = By.id("s2id_CE_ddlRefraction_RE_DIALATE_CYL");
    private By reCycloAxisVal = By.id("s2id_CE_ddlRefraction_RE_DIALATE_Axis");

    private By leCycloSphVal = By.id("s2id_CE_ddlRefraction_LE_DIALATE_SPH");
    private By leCycloCylVal = By.id("s2id_CE_ddlRefraction_LE_DIALATE_CYL");
    private By leCycloAxisVal = By.id("s2id_CE_ddlRefraction_LE_DIALATE_Axis");


    // ===== EXTRA ADD (separate ADD field) =====
    private By reAddExtra = By.id("s2id_CE_ddlRefraction_RE_ADD_Add");
    private By leAddExtra = By.id("s2id_CE_ddlRefraction_LE_ADD_Add");


    // ===== NEAR VISION (2nd FIELD if present) =====
    private By reNearVisionExtra = By.id("s2id_CE_ddlRefraction_RE_NearVision_SPH");
    private By leNearVisionExtra = By.id("s2id_CE_ddlRefraction_LE_NearVision_SPH");


    // ===== IOP VALUE DROPDOWN =====
    private By reIOPValueDropdown = By.id("s2id_CE_txtRefraction_RE_IOPValue");
    private By leIOPValueDropdown = By.id("s2id_CE_txtRefraction_LE_IOPValue");

    // ===== CYCLOPLEGIC =====
    private By reCycloRemark = By.id("CE_txtRefraction_DIALATE_Remarks_RE");
    private By leCycloRemark = By.id("CE_txtRefraction_DIALATE_Remarks_LE");

    // ===== ADD REMARK =====
    private By reAddRemark = By.id("CE_txtRefraction_IOP_Remarks_RE");
    private By leAddRemark = By.id("CE_txtRefraction_IOP_Remarks_LE");

    // ===== NEAR VISION / NPC =====
    private By reNearNpc = By.id("s2id_CE_ddlRefraction_RE_NearVision_CYL");
    private By leNearNpc = By.id("s2id_CE_ddlRefraction_LE_NearVision_CYL");


    // ===== TAB ACTION =====
    public void openVisualAcuityTab() {
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(visualAcuityTab));
        if (!tab.getAttribute("class").contains("active")) {
            scrollAndClick(tab);
        }
    }

    // ===== TOP SECTION METHODS =====
    public void selectSpectacles(String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(spectaclesDropdown));
        dropdown.click();
        String optionText = value.equalsIgnoreCase("yes") ? "Yes"
                : value.equalsIgnoreCase("no") ? "No" : "Select";
        WebElement option = dropdown.findElement(By.xpath(".//option[normalize-space(text())='" + optionText + "']"));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
        wait.until(d -> dropdown.getAttribute("value").equals(option.getAttribute("value")));
        System.out.println("✅ Spectacles selected: " + optionText);
    }

    public void enterSpectacleAge(String years, String months) {
        clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(specAgeYears)), years);
        clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(specAgeMonths)), months);
    }

    public void enterPurpose(String purpose) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(purposeField));
        input.clear();
        input.sendKeys(purpose);
    }

    // ===== RE + LE DROPDOWN METHODS =====
    public void fillRightEye(String unaided, String withSpecs, String withPH, String near) {
        selectSelect2Dropdown(reUnaided, unaided);
        selectSelect2Dropdown(reWithSpecs, withSpecs);
        selectSelect2Dropdown(reWithPH, withPH);
        selectSelect2Dropdown(reNearVision, near);
    }

    public void fillLeftEye(String unaided, String withSpecs, String withPH, String near) {
        selectSelect2Dropdown(leUnaided, unaided);
        selectSelect2Dropdown(leWithSpecs, withSpecs);
        selectSelect2Dropdown(leWithPH, withPH);
        selectSelect2Dropdown(leNearVision, near);
    }

    // ================= SELECT2 UNIVERSAL METHOD =================
    private void selectSelect2Dropdown(By select2Container, String value) {

        WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(select2Container));

        // Step 1: Get actual select ID
        String select2Id = container.getAttribute("id");  
        String actualSelectId = select2Id.replace("s2id_", "");

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Step 2: Set value directly
        js.executeScript(
            "var select = document.getElementById(arguments[0]);" +
            "if(!select) return;" +
            "for (var i = 0; i < select.options.length; i++) {" +
            "  var txt = select.options[i].text.trim();" +
            "  if (txt === arguments[1]) {" +
            "    select.selectedIndex = i;" +
            "    select.dispatchEvent(new Event('change'));" +
            "    return;" +
            "  }" +
            "}",
            actualSelectId, value
        );

        System.out.println("✅ Selected (JS): " + value);
    }

    // ===== SAVE =====
    public void clickSave() { scrollAndClick(wait.until(ExpectedConditions.elementToBeClickable(saveButton))); }

    // ===== OTHER DIAGNOSTIC =====
    public void selectColorVision(String value) {
        String id = value.equalsIgnoreCase("normal") ? "CECV_rdbNormal" :
                    value.equalsIgnoreCase("abnormal") ? "CECV_rdbAbnormal" : "CECV_rdbPartial";
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].checked=true; arguments[0].dispatchEvent(new Event('change'));", radio);
        System.out.println("✅ Color vision selected: " + value);
    }

    public void selectStereopsis(String value) {
        String id = value.equals("<40") ? "CECV_rdbStereopsis1" :
                    value.equals("50-100") ? "CECV_rdbStereopsis2" : "CECV_rdbStereopsis3";
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].checked=true; arguments[0].dispatchEvent(new Event('change'));", radio);
        System.out.println("✅ Stereopsis selected: " + value);
    }

    public void enterContrast(String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(contrastField));
        input.click();
        input.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", input);
        input.sendKeys(value);
        System.out.println("✅ Contrast entered: " + value);
    }

    public void clickOtherSave() { scrollAndClick(wait.until(ExpectedConditions.elementToBeClickable(otherSaveButton)));
    wait.until(ExpectedConditions.visibilityOfElementLocated(rePGSph));}

    public void handleSuccessPopup() {
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
        System.out.println("✅ Popup: " + popup.getText());
        wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
    }

    // ===== UTIL =====
    private void scrollAndClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void click(By locator) { wait.until(ExpectedConditions.elementToBeClickable(locator)).click(); }

    private void enterText(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        element.click();
        element.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", element);
        element.sendKeys(value);
        System.out.println("✅ Entered: " + value);
    }

    // ===== RE + LE REMARK METHODS =====
    public void fillDryRetinoscopy(String reRemark, String leRemark) { enterText(reDryRemark, reRemark); enterText(leDryRemark, leRemark); }
    public void fillCycloplegic(String reRemark, String leRemark) { enterText(reCycloRemark, reRemark); enterText(leCycloRemark, leRemark); }
    public void fillAddRemark(String reRemark, String leRemark) { enterText(reAddRemark, reRemark); enterText(leAddRemark, leRemark); }
//    public void fillNearVisionNPC(String reValue, String leValue) { enterText(reNearNpc, reValue); enterText(leNearNpc, leValue); }
    public void fillIOP(String reValue, String leValue, String time) {
//        enterText(reIOP, reValue); enterText(leIOP, leValue);
//        WebElement timeField = wait.until(ExpectedConditions.elementToBeClickable(iopTime));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + time + "';", timeField);
    }

    public void fillAdditionalRefractionDetails(String reDry, String leDry, String reCyclo, String leCyclo,
                                                String reAddRem, String leAddRem, String reNpc, String leNpc,
                                                String reIOPVal, String leIOPVal, String time) {
        fillDryRetinoscopy(reDry, leDry);
        fillCycloplegic(reCyclo, leCyclo);
        fillAddRemark(reAddRem, leAddRem);
        fillNearVisionNPC(reNpc, leNpc);
        fillIOP(reIOPVal, leIOPVal, time);
        enterCycloplegicRemark("Test");  
    }
    
   
    // ===== RE + LE REFRACTION METHODS =====
    public void fillRefractionRightEye(String sph, String cyl, String axis, String add,
                                       String finalSph, String finalCyl, String finalAxis, String finalAdd) {
        selectSelect2Dropdown(rePGSph, sph);
        selectSelect2Dropdown(rePGCyl, cyl);
        selectSelect2Dropdown(rePGAxis, axis);
        selectSelect2Dropdown(rePGAdd, add);
        selectSelect2Dropdown(reFinalSph, finalSph);
        selectSelect2Dropdown(reFinalCyl, finalCyl);
        selectSelect2Dropdown(reFinalAxis, finalAxis);
        selectSelect2Dropdown(reFinalAdd, finalAdd);
    }

    public void fillRefractionLeftEye(String sph, String cyl, String axis, String add,
                                      String finalSph, String finalCyl, String finalAxis, String finalAdd) {
        selectSelect2Dropdown(lePGSph, sph);
        selectSelect2Dropdown(lePGCyl, cyl);
        selectSelect2Dropdown(lePGAxis, axis);
        selectSelect2Dropdown(lePGAdd, add);
        selectSelect2Dropdown(leFinalSph, finalSph);
        selectSelect2Dropdown(leFinalCyl, finalCyl);
        selectSelect2Dropdown(leFinalAxis, finalAxis);
        selectSelect2Dropdown(leFinalAdd, finalAdd);
    }

    public void clickRefractionSave() { scrollAndClick(wait.until(ExpectedConditions.elementToBeClickable(refractionSaveBtn))); }

    public void fillRefractionForm(String reSph, String reCyl, String reAxis, String reAdd,
                                   String reFinalSph, String reFinalCyl, String reFinalAxis, String reFinalAdd,
                                   String leSph, String leCyl, String leAxis, String leAdd,
                                   String leFinalSph, String leFinalCyl, String leFinalAxis, String leFinalAdd) {
        fillRefractionRightEye(reSph, reCyl, reAxis, reAdd, reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd);
        fillRefractionLeftEye(leSph, leCyl, leAxis, leAdd, leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);
        clickRefractionSave();
        handleSuccessPopup();
    }

    // ===== FULL FLOW =====
    public void fillVisualAcuityForm(String spectacles, String years, String months, String purpose,
                                     String reUnaided, String reSpecs, String rePH, String reNear,
                                     String leUnaided, String leSpecs, String lePH, String leNear,
                                     String colorVision, String stereopsis, String contrast) {
        openVisualAcuityTab();
        selectSpectacles(spectacles);
        enterSpectacleAge(years, months);
        enterPurpose(purpose);
        fillRightEye(reUnaided, reSpecs, rePH, reNear);
        fillLeftEye(leUnaided, leSpecs, lePH, leNear);
        clickSave();
        handleSuccessPopup();

        selectColorVision(colorVision);
        selectStereopsis(stereopsis);
        enterContrast(contrast);

        clickOtherSave();
        handleSuccessPopup();

        // 🔥 ADD THIS LINE
        waitForRefractionSection();
    }

    // ===== OTHER DIAGNOSIS POPUP =====
    public void handleOtherDiagnosisPopup() {
        try {
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
            String message = popup.getText().trim();
            if (!message.equalsIgnoreCase("Patient other diagnostic details saved successfully.")) {
                throw new RuntimeException("❌ Unexpected popup message: " + message);
            }
            WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(okButton));
            Thread.sleep(300);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okBtn);
            wait.until(ExpectedConditions.invisibilityOf(popup));
            System.out.println("✅ Other Diagnosis popup handled successfully");
        } catch (Exception e) {
            System.out.println("⚠️ Popup handling failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public void fillDryRetinoscopyValues(String reSph, String reCyl, String reAxis,
            String leSph, String leCyl, String leAxis) {
	selectSelect2Dropdown(reDrySph, reSph);
	selectSelect2Dropdown(reDryCyl, reCyl);
	selectSelect2Dropdown(reDryAxis, reAxis);
	
	selectSelect2Dropdown(leDrySph, leSph);
	selectSelect2Dropdown(leDryCyl, leCyl);
	selectSelect2Dropdown(leDryAxis, leAxis);
	}
    
    public void fillCycloplegicValues(String reSph, String reCyl, String reAxis,
            String leSph, String leCyl, String leAxis) {
	selectSelect2Dropdown(reCycloSphVal, reSph);
	selectSelect2Dropdown(reCycloCylVal, reCyl);
	selectSelect2Dropdown(reCycloAxisVal, reAxis);
	
	selectSelect2Dropdown(leCycloSphVal, leSph);
	selectSelect2Dropdown(leCycloCylVal, leCyl);
	selectSelect2Dropdown(leCycloAxisVal, leAxis);
	}
    
    public void fillAdditionalAdd(String reAdd, String leAdd) {
        selectSelect2Dropdown(reAddExtra, reAdd);
        selectSelect2Dropdown(leAddExtra, leAdd);
    }
    
    public void fillNearVisionExtra(String reVal, String leVal) {
        enterText(reNearVisionExtra, reVal);
        enterText(leNearVisionExtra, leVal);
    }
    
    public void fillCompleteRefraction(
            // PG
            String reSph, String reCyl, String reAxis, String reAdd,
            String leSph, String leCyl, String leAxis, String leAdd,

            // DRY
            String reDrySphVal, String reDryCylVal, String reDryAxisVal,
            String leDrySphVal, String leDryCylVal, String leDryAxisVal,

            // CYCLO
            String reCycloSph, String reCycloCyl, String reCycloAxis,
            String leCycloSph, String leCycloCyl, String leCycloAxis,

            // FINAL
            String reFinalSph, String reFinalCyl, String reFinalAxis, String reFinalAdd,
            String leFinalSph, String leFinalCyl, String leFinalAxis, String leFinalAdd,

            // EXTRA
            String reAddExtraVal, String leAddExtraVal
    ) {

        // PG
        fillRefractionRightEye(reSph, reCyl, reAxis, reAdd, reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd);
        fillRefractionLeftEye(leSph, leCyl, leAxis, leAdd, leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);

        // DRY
        fillDryRetinoscopyValues(reDrySphVal, reDryCylVal, reDryAxisVal,
                                  leDrySphVal, leDryCylVal, leDryAxisVal);

        // CYCLO
        fillCycloplegicValues(reCycloSph, reCycloCyl, reCycloAxis,
                              leCycloSph, leCycloCyl, leCycloAxis);

        // EXTRA ADD
        fillAdditionalAdd(reAddExtraVal, leAddExtraVal);

        // SAVE
        clickRefractionSave();
        handleSuccessPopup();
    }

    public void selectIOPValue(String reValue, String leValue) {

        if (reValue != null && !reValue.isEmpty()) {
            selectSelect2Dropdown(reIOPValueDropdown, reValue);
            System.out.println("🟢 RE IOP Value selected: " + reValue);
        }

        if (leValue != null && !leValue.isEmpty()) {
            selectSelect2Dropdown(leIOPValueDropdown, leValue);
            System.out.println("🟢 LE IOP Value selected: " + leValue);
        }
    }
    
    private void waitForRefractionSection() {

        // Wait till first dropdown visible
        WebElement firstDropdown = wait.until(
            ExpectedConditions.visibilityOfElementLocated(rePGSph));

        // Wait till clickable (IMPORTANT)
        wait.until(ExpectedConditions.elementToBeClickable(rePGSph));

        // Extra stability wait (DOM settle)
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));

        // Optional small buffer (safe)
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("✅ Refraction section ready");
    }
    
    private void enterCycloplegicRemark(String value) {

	    By locator = By.id("CE_txtCycloplegic_RE_Remark");
	
	    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	
	    // 🔥 Force enable + type
	    js.executeScript(
	        "arguments[0].removeAttribute('disabled');" +
	        "arguments[0].removeAttribute('readonly');" +
	        "arguments[0].value = arguments[1];" +
	        "arguments[0].dispatchEvent(new Event('input'));",
	        element, value
	    );
	
	    System.out.println("✅ Cycloplegic remark entered (force)");
    }
    
	    public void fillFullRefractionFlow(
	
	            // ===== PG =====
	            String reSph, String reCyl, String reAxis, String reAdd,
	            String leSph, String leCyl, String leAxis, String leAdd,
	
	            // ===== DRY =====
	            String reDrySph, String reDryCyl, String reDryAxis,
	            String leDrySph, String leDryCyl, String leDryAxis,
	
	            // ===== CYCLO =====
	            String reCycloSph, String reCycloCyl, String reCycloAxis,
	            String leCycloSph, String leCycloCyl, String leCycloAxis,
	
	            // ===== FINAL =====
	            String reFinalSph, String reFinalCyl, String reFinalAxis, String reFinalAdd,
	            String leFinalSph, String leFinalCyl, String leFinalAxis, String leFinalAdd,
	
	            // ===== EXTRA ADD =====
	            String reAddExtraVal, String leAddExtraVal,
	
	            // ===== REMARKS =====
	            String reDryRemarkVal, String leDryRemarkVal,
	            String reCycloRemarkVal, String leCycloRemarkVal,
	            String reAddRemarkVal, String leAddRemarkVal,
	
	            // ===== NPC =====
	            String reNpc, String leNpc,
	
	            // ===== IOP =====
	            String reIOP, String leIOP, String iopTimeVal
	    ) {
	
	        waitForRefractionSection();
	
	        // PG + FINAL
	        fillRefractionRightEye(reSph, reCyl, reAxis, reAdd,
	                reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd);
	
	        fillRefractionLeftEye(leSph, leCyl, leAxis, leAdd,
	                leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);
	
	        // DRY
	        fillDryRetinoscopyValues(
	                reDrySph, reDryCyl, reDryAxis,
	                leDrySph, leDryCyl, leDryAxis
	        );
	        fillDryRetinoscopy(reDryRemarkVal, leDryRemarkVal);
	
	        // CYCLO
	        fillCycloplegicValues(
	                reCycloSph, reCycloCyl, reCycloAxis,
	                leCycloSph, leCycloCyl, leCycloAxis
	        );
	        fillCycloplegic(reCycloRemarkVal, leCycloRemarkVal);
	
	        // ADD
	        fillAdditionalAdd(reAddExtraVal, leAddExtraVal);
	        fillAddRemark(reAddRemarkVal, leAddRemarkVal);
	
	        // NPC
	
	
	        // IOP
	        fillIOP(reIOP, leIOP, iopTimeVal);
	
	        // SAVE
	        clickRefractionSave();
	        handleSuccessPopup();
	
	        System.out.println("✅ Full Refraction Flow Completed");
	    }
	    public void fillNearVisionNPC(String reValue, String leValue) {
	
	    if (reValue != null && !reValue.isEmpty()) {
	        selectSelect2Dropdown(reNearNpc, reValue);
	        System.out.println("✅ RE NPC selected: " + reValue);
	    }
	
	    if (leValue != null && !leValue.isEmpty()) {
	        selectSelect2Dropdown(leNearNpc, leValue);
	        System.out.println("✅ LE NPC selected: " + leValue);
	    }
	}
    
    
}