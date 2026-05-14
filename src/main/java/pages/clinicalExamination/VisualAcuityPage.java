package pages.clinicalExamination;

import pages.BasePage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VisualAcuityPage extends BasePage {

    public VisualAcuityPage(WebDriver driver) {
        super(driver);
    }

    private By visualAcuityTab = By.id("visualAcuityRefraction-tab");

    private By spectaclesDropdown = By.id("CE_ddlVisualActuityWearingSpecs");
    private By specAgeYears = By.id("CE_txtUsingSpectaclesYear");
    private By specAgeMonths = By.id("CE_txtUsingSpectaclesMonth");
    private By purposeField = By.id("CE_txtVisualAcuitySpecsPrupose");

    private By reUnaided = By.id("s2id_CE_ddl_RE_Unaided");
    private By reWithSpecs = By.id("s2id_CE_ddl_RE_WithSpecs");
    private By reWithPH = By.id("s2id_CE_ddl_RE_WithPH");
    private By reVaNearVision = By.id("s2id_CE_ddl_RE_NearVision");

    private By leUnaided = By.id("s2id_CE_ddl_LE_Unaided");
    private By leWithSpecs = By.id("s2id_CE_ddl_LE_WithSpecs");
    private By leWithPH = By.id("s2id_CE_ddl_LE_WithPH");
    private By leVaNearVision = By.id("s2id_CE_ddl_LE_NearVision");

    private By saveButton = By.id("CE_btnAddUpadateVisualAcuity");
    private By refractionSaveBtn = By.id("CE_btnAddUpadateRefraction");
    private By successPopup = By.id("popup_message");
    private By okButton = By.id("popup_ok");

    private By rePGSph = By.id("s2id_CE_ddlRefraction_RE_PGP_SPH");
    private By rePGCyl = By.id("s2id_CE_ddlRefraction_RE_PGP_CYL");
    private By rePGAxis = By.id("s2id_CE_ddlRefraction_RE_PGP_Axis");
    private By rePGAdd = By.id("s2id_CE_ddlRefraction_RE_PGP_Add");

    private By lePGSph = By.id("s2id_CE_ddlRefraction_LE_PGP_SPH");
    private By lePGCyl = By.id("s2id_CE_ddlRefraction_LE_PGP_CYL");
    private By lePGAxis = By.id("s2id_CE_ddlRefraction_LE_PGP_Axis");
    private By lePGAdd = By.id("s2id_CE_ddlRefraction_LE_PGP_Add");

    private By reDrySph = By.id("s2id_CE_ddlRefraction_RE_DRY_SPH");
    private By reDryCyl = By.id("s2id_CE_ddlRefraction_RE_DRY_CYL");
    private By reDryAxis = By.id("s2id_CE_ddlRefraction_RE_DRY_Axis");

    private By leDrySph = By.id("s2id_CE_ddlRefraction_LE_DRY_SPH");
    private By leDryCyl = By.id("s2id_CE_ddlRefraction_LE_DRY_CYL");
    private By leDryAxis = By.id("s2id_CE_ddlRefraction_LE_DRY_Axis");

    private By reDryRemark = By.id("CE_txtRefraction_DRY_Remarks_RE");
    private By leDryRemark = By.id("CE_txtRefraction_DRY_Remarks_LE");

    private By reCycloSph = By.id("s2id_CE_ddlRefraction_RE_DIALATE_SPH");
    private By reCycloCyl = By.id("s2id_CE_ddlRefraction_RE_DIALATE_CYL");
    private By reCycloAxis = By.id("s2id_CE_ddlRefraction_RE_DIALATE_Axis");

    private By leCycloSph = By.id("s2id_CE_ddlRefraction_LE_DIALATE_SPH");
    private By leCycloCyl = By.id("s2id_CE_ddlRefraction_LE_DIALATE_CYL");
    private By leCycloAxis = By.id("s2id_CE_ddlRefraction_LE_DIALATE_Axis");

    private By reCycloRemark = By.id("CE_txtRefraction_DIALATE_Remarks_RE");
    private By leCycloRemark = By.id("CE_txtRefraction_DIALATE_Remarks_LE");

    private By reFinalSph = By.id("s2id_CE_ddlRefraction_RE_Acceptance_SPH");
    private By reFinalCyl = By.id("s2id_CE_ddlRefraction_RE_Acceptance_CYL");
    private By reFinalAxis = By.id("s2id_CE_ddlRefraction_RE_Acceptance_Axis");
    private By reFinalAdd = By.id("s2id_CE_ddlRefraction_RE_Acceptance_Add");

    private By leFinalSph = By.id("s2id_CE_ddlRefraction_LE_Acceptance_SPH");
    private By leFinalCyl = By.id("s2id_CE_ddlRefraction_LE_Acceptance_CYL");
    private By leFinalAxis = By.id("s2id_CE_ddlRefraction_LE_Acceptance_Axis");
    private By leFinalAdd = By.id("s2id_CE_ddlRefraction_LE_Acceptance_Add");

    private By reNearVisionSph = By.id("s2id_CE_ddlRefraction_RE_NearVision_SPH");
    private By reNearVisionCyl = By.id("s2id_CE_ddlRefraction_RE_NearVision_CYL");
    private By leNearVisionSph = By.id("s2id_CE_ddlRefraction_LE_NearVision_SPH");
    private By leNearVisionCyl = By.id("s2id_CE_ddlRefraction_LE_NearVision_CYL");

    private By reIpdDistance = By.id("CE_txtRefraction_RE_IPDDistance");
    private By reIpdNear = By.id("CE_txtRefraction_RE_IPDNear");
    private By leIpdDistance = By.id("CE_txtRefraction_LE_IPDDistance");
    private By leIpdNear = By.id("CE_txtRefraction_LE_IPDNear");

    private By reIopTime = By.id("CE_txtRefraction_RE_IOPTime");
    private By leIopTime = By.id("CE_txtRefraction_LE_IOPTime");
    private By reIopValue = By.id("s2id_CE_txtRefraction_RE_IOPValue");
    private By leIopValue = By.id("s2id_CE_txtRefraction_LE_IOPValue");

    private By reAddExtra = By.id("s2id_CE_ddlRefraction_RE_ADD_Add");
    private By leAddExtra = By.id("s2id_CE_ddlRefraction_LE_ADD_Add");
    private By reAddRemark = By.id("CE_txtRefraction_IOP_Remarks_RE");
    private By leAddRemark = By.id("CE_txtRefraction_IOP_Remarks_LE");

    public void openVisualAcuityTab() {
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(visualAcuityTab));
        if (!tab.getAttribute("class").contains("active")) {
            scrollAndClick(tab);
        }
    }

    public void selectSpectacles(String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(spectaclesDropdown));
        dropdown.click();

        String optionText = value.equalsIgnoreCase("yes") ? "Yes"
                : value.equalsIgnoreCase("no") ? "No" : "Select";

        WebElement option = dropdown.findElement(
                By.xpath(".//option[normalize-space(text())='" + optionText + "']")
        );

        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
        wait.until(d -> dropdown.getAttribute("value").equals(option.getAttribute("value")));
    }

    public void enterSpectacleAge(String years, String months) {
        clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(specAgeYears)), years);
        clearAndType(wait.until(ExpectedConditions.visibilityOfElementLocated(specAgeMonths)), months);
    }

    public void enterPurpose(String purpose) {
        enterInputValue(purposeField, purpose);
    }

    public void fillRightEye(String unaided, String withSpecs, String withPH, String near) {
        selectSelect2Dropdown(reUnaided, unaided);
        selectSelect2Dropdown(reWithSpecs, withSpecs);
        selectSelect2Dropdown(reWithPH, withPH);
        selectSelect2Dropdown(reVaNearVision, near);
    }

    public void fillLeftEye(String unaided, String withSpecs, String withPH, String near) {
        selectSelect2Dropdown(leUnaided, unaided);
        selectSelect2Dropdown(leWithSpecs, withSpecs);
        selectSelect2Dropdown(leWithPH, withPH);
        selectSelect2Dropdown(leVaNearVision, near);
    }

    private void selectSelect2Dropdown(By select2Container, String visibleText) {
        if (visibleText == null || visibleText.trim().isEmpty()) {
            return;
        }

        WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(select2Container));

        String containerId = container.getAttribute("id");
        String actualSelectId = containerId.startsWith("s2id_")
                ? containerId.replace("s2id_", "")
                : containerId;

        JavascriptExecutor js = (JavascriptExecutor) driver;

        Boolean selected = (Boolean) js.executeScript(
                "var select = document.getElementById(arguments[0]);" +
                "var text = arguments[1].trim();" +
                "if (!select) return false;" +
                "var matchedValue = null;" +
                "for (var i = 0; i < select.options.length; i++) {" +
                "  if (select.options[i].text.trim().toLowerCase() === text.toLowerCase()) {" +
                "    matchedValue = select.options[i].value;" +
                "    break;" +
                "  }" +
                "}" +
                "if (matchedValue === null) return false;" +
                "select.value = matchedValue;" +
                "if (window.jQuery) {" +
                "  var $select = jQuery(select);" +
                "  try { $select.select2('val', matchedValue); } catch(e) {}" +
                "  $select.trigger('input').trigger('change').trigger('blur');" +
                "}" +
                "select.dispatchEvent(new Event('input', { bubbles: true }));" +
                "select.dispatchEvent(new Event('change', { bubbles: true }));" +
                "select.dispatchEvent(new Event('blur', { bubbles: true }));" +
                "var c = document.getElementById('s2id_' + arguments[0]);" +
                "if (c) {" +
                "  var chosen = c.querySelector('.select2-chosen');" +
                "  if (chosen) chosen.innerText = text;" +
                "}" +
                "return select.value === matchedValue;",
                actualSelectId, visibleText
        );

        if (!Boolean.TRUE.equals(selected)) {
            throw new RuntimeException("Option not found in dropdown " + actualSelectId + ": " + visibleText);
        }

        waitForAjaxToFinish();
        System.out.println("Selected: " + visibleText);
    }

    private void enterInputValue(By locator, String value) {
        if (value == null || value.trim().isEmpty()) {
            return;
        }

        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});" +
                "arguments[0].removeAttribute('readonly');" +
                "arguments[0].removeAttribute('disabled');" +
                "arguments[0].value = arguments[1];" +
                "arguments[0].setAttribute('value', arguments[1]);" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
                input, value.trim()
        );

        waitForAjaxToFinish();
        System.out.println("Entered: " + value);
    }

    private void enterText(By locator, String value) {
        enterInputValue(locator, value);
    }

    public void fillRefractionRightEye(String sph, String cyl, String axis, String add,
                                       String finalSph, String finalCyl,
                                       String finalAxis, String finalAdd) {
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
                                      String finalSph, String finalCyl,
                                      String finalAxis, String finalAdd) {
        selectSelect2Dropdown(lePGSph, sph);
        selectSelect2Dropdown(lePGCyl, cyl);
        selectSelect2Dropdown(lePGAxis, axis);
        selectSelect2Dropdown(lePGAdd, add);
        selectSelect2Dropdown(leFinalSph, finalSph);
        selectSelect2Dropdown(leFinalCyl, finalCyl);
        selectSelect2Dropdown(leFinalAxis, finalAxis);
        selectSelect2Dropdown(leFinalAdd, finalAdd);
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
        selectSelect2Dropdown(reCycloSph, reSph);
        selectSelect2Dropdown(reCycloCyl, reCyl);
        selectSelect2Dropdown(reCycloAxis, reAxis);
        selectSelect2Dropdown(leCycloSph, leSph);
        selectSelect2Dropdown(leCycloCyl, leCyl);
        selectSelect2Dropdown(leCycloAxis, leAxis);
    }

    public void fillDryRetinoscopy(String reRemark, String leRemark) {
        enterText(reDryRemark, reRemark);
        enterText(leDryRemark, leRemark);
    }

    public void fillCycloplegic(String reRemark, String leRemark) {
        enterText(reCycloRemark, reRemark);
        enterText(leCycloRemark, leRemark);
    }

    public void fillAdditionalAdd(String reAdd, String leAdd) {
        selectSelect2Dropdown(reAddExtra, reAdd);
        selectSelect2Dropdown(leAddExtra, leAdd);
    }

    public void fillAddRemark(String reRemark, String leRemark) {
        enterText(reAddRemark, reRemark);
        enterText(leAddRemark, leRemark);
    }

    public void fillNearVision(String reNearSph, String reNearCyl,
                               String leNearSph, String leNearCyl) {
        selectSelect2Dropdown(reNearVisionSph, reNearSph);
        selectSelect2Dropdown(reNearVisionCyl, reNearCyl);
        selectSelect2Dropdown(leNearVisionSph, leNearSph);
        selectSelect2Dropdown(leNearVisionCyl, leNearCyl);
    }

    public void fillNearVisionExtra(String reVal, String leVal) {
        selectSelect2Dropdown(reNearVisionSph, reVal);
        selectSelect2Dropdown(leNearVisionSph, leVal);
    }

    public void fillNearVisionNPC(String reValue, String leValue) {
        selectSelect2Dropdown(reNearVisionCyl, reValue);
        selectSelect2Dropdown(leNearVisionCyl, leValue);
    }

    public void fillIPD(String reDist, String reNear, String leDist, String leNear) {
        enterInputValue(reIpdDistance, reDist);
        enterInputValue(reIpdNear, reNear);
        enterInputValue(leIpdDistance, leDist);
        enterInputValue(leIpdNear, leNear);
    }

    public void fillIOP(String reTime, String reValue, String leTime, String leValue) {
        enterInputValue(reIopTime, reTime);
        selectSelect2Dropdown(reIopValue, reValue);
        enterInputValue(leIopTime, leTime);
        selectSelect2Dropdown(leIopValue, leValue);
    }

    public void fillIOP(String reValue, String leValue, String time) {
        fillIOP(time, reValue, time, leValue);
    }

    public void selectIOPValue(String reValue, String leValue) {
        selectSelect2Dropdown(reIopValue, reValue);
        selectSelect2Dropdown(leIopValue, leValue);
    }

    public void clickSave() {
        scrollAndClick(wait.until(ExpectedConditions.elementToBeClickable(saveButton)));
    }

    public void clickRefractionSave() {
        waitForAjaxToFinish();
        scrollAndClick(wait.until(ExpectedConditions.elementToBeClickable(refractionSaveBtn)));
    }

    public void handleSuccessPopup() {
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
        System.out.println("Popup: " + popup.getText());
        wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
    }

    public void selectColorVision(String value) {
        String id = value.equalsIgnoreCase("normal") ? "CECV_rdbNormal"
                : value.equalsIgnoreCase("abnormal") ? "CECV_rdbAbnormal" : "CECV_rdbPartial";
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].checked=true;" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                radio
        );
    }

    public void selectStereopsis(String value) {
        String id = value.equals("<40") ? "CECV_rdbStereopsis1"
                : value.equals("50-100") ? "CECV_rdbStereopsis2" : "CECV_rdbStereopsis3";
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].checked=true;" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                radio
        );
    }

    public void handleOtherDiagnosisPopup() {
        try {
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
            String message = popup.getText().trim();

            if (!message.equalsIgnoreCase("Patient other diagnostic details saved successfully.")) {
                throw new RuntimeException("Unexpected popup message: " + message);
            }

            WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(okButton));
            Thread.sleep(300);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okBtn);
            wait.until(ExpectedConditions.invisibilityOf(popup));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void scrollAndClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void waitForAjaxToFinish() {
        wait.until(d -> (Boolean) ((JavascriptExecutor) d).executeScript(
                "return document.readyState === 'complete' && " +
                "(!window.jQuery || jQuery.active === 0);"
        ));
    }

    private void waitForRefractionSection() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(rePGSph));
        wait.until(ExpectedConditions.elementToBeClickable(rePGSph));
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState")
                .equals("complete"));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void enterCycloplegicRemark(String value) {
        By locator = By.id("CE_txtCycloplegic_RE_Remark");
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('disabled');" +
                "arguments[0].removeAttribute('readonly');" +
                "arguments[0].value = arguments[1];" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                element, value
        );
    }

    public void fillVisualAcuityForm(String spectacles, String years, String months, String purpose,
                                     String reUnaided, String reSpecs, String rePH, String reNear,
                                     String leUnaided, String leSpecs, String lePH, String leNear) {
        openVisualAcuityTab();
        selectSpectacles(spectacles);
        enterSpectacleAge(years, months);
        enterPurpose(purpose);
        fillRightEye(reUnaided, reSpecs, rePH, reNear);
        fillLeftEye(leUnaided, leSpecs, lePH, leNear);
        clickSave();
        handleSuccessPopup();
        waitForRefractionSection();
    }

    public void fillRefractionForm(String reSph, String reCyl, String reAxis, String reAdd,
                                   String reFinalSph, String reFinalCyl,
                                   String reFinalAxis, String reFinalAdd,
                                   String leSph, String leCyl, String leAxis, String leAdd,
                                   String leFinalSph, String leFinalCyl,
                                   String leFinalAxis, String leFinalAdd) {
        fillRefractionRightEye(reSph, reCyl, reAxis, reAdd,
                reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd);
        fillRefractionLeftEye(leSph, leCyl, leAxis, leAdd,
                leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);
        clickRefractionSave();
        handleSuccessPopup();
    }

    public void fillAdditionalRefractionDetails(String reDry, String leDry,
                                                String reCyclo, String leCyclo,
                                                String reAddRem, String leAddRem,
                                                String reNpc, String leNpc,
                                                String reIOPVal, String leIOPVal,
                                                String time) {
        fillDryRetinoscopy(reDry, leDry);
        fillCycloplegic(reCyclo, leCyclo);
        fillAddRemark(reAddRem, leAddRem);
        fillNearVisionNPC(reNpc, leNpc);
        fillIOP(reIOPVal, leIOPVal, time);
        enterCycloplegicRemark("Test");
    }

    public void fillCompleteRefraction(
            String reSph, String reCyl, String reAxis, String reAdd,
            String leSph, String leCyl, String leAxis, String leAdd,
            String reDrySphVal, String reDryCylVal, String reDryAxisVal,
            String leDrySphVal, String leDryCylVal, String leDryAxisVal,
            String reCycloSph, String reCycloCyl, String reCycloAxis,
            String leCycloSph, String leCycloCyl, String leCycloAxis,
            String reFinalSph, String reFinalCyl, String reFinalAxis, String reFinalAdd,
            String leFinalSph, String leFinalCyl, String leFinalAxis, String leFinalAdd,
            String reAddExtraVal, String leAddExtraVal
    ) {
        fillRefractionRightEye(reSph, reCyl, reAxis, reAdd,
                reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd);
        fillRefractionLeftEye(leSph, leCyl, leAxis, leAdd,
                leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);
        fillDryRetinoscopyValues(reDrySphVal, reDryCylVal, reDryAxisVal,
                leDrySphVal, leDryCylVal, leDryAxisVal);
        fillCycloplegicValues(reCycloSph, reCycloCyl, reCycloAxis,
                leCycloSph, leCycloCyl, leCycloAxis);
        fillAdditionalAdd(reAddExtraVal, leAddExtraVal);
        clickRefractionSave();
        handleSuccessPopup();
    }

    public void fillFullRefractionFlow(
            String reSph, String reCyl, String reAxis, String reAdd,
            String leSph, String leCyl, String leAxis, String leAdd,
            String reDrySph, String reDryCyl, String reDryAxis,
            String leDrySph, String leDryCyl, String leDryAxis,
            String reCycloSph, String reCycloCyl, String reCycloAxis,
            String leCycloSph, String leCycloCyl, String leCycloAxis,
            String reFinalSph, String reFinalCyl, String reFinalAxis, String reFinalAdd,
            String leFinalSph, String leFinalCyl, String leFinalAxis, String leFinalAdd,
            String reAddExtraVal, String leAddExtraVal,
            String reDryRemarkVal, String leDryRemarkVal,
            String reCycloRemarkVal, String leCycloRemarkVal,
            String reAddRemarkVal, String leAddRemarkVal,
            String reNpc, String leNpc,
            String reIOP, String leIOP, String iopTimeVal
    ) {
        waitForRefractionSection();

        fillRefractionRightEye(reSph, reCyl, reAxis, reAdd,
                reFinalSph, reFinalCyl, reFinalAxis, reFinalAdd);
        fillRefractionLeftEye(leSph, leCyl, leAxis, leAdd,
                leFinalSph, leFinalCyl, leFinalAxis, leFinalAdd);

        fillDryRetinoscopyValues(reDrySph, reDryCyl, reDryAxis,
                leDrySph, leDryCyl, leDryAxis);
        fillDryRetinoscopy(reDryRemarkVal, leDryRemarkVal);

        fillCycloplegicValues(reCycloSph, reCycloCyl, reCycloAxis,
                leCycloSph, leCycloCyl, leCycloAxis);
        fillCycloplegic(reCycloRemarkVal, leCycloRemarkVal);

        fillAdditionalAdd(reAddExtraVal, leAddExtraVal);
        fillAddRemark(reAddRemarkVal, leAddRemarkVal);

        fillNearVisionNPC(reNpc, leNpc);
        fillIOP(reIOP, leIOP, iopTimeVal);

        waitForAjaxToFinish();
        clickRefractionSave();
        handleSuccessPopup();

        System.out.println("Full Refraction Flow Completed");
    }
}
