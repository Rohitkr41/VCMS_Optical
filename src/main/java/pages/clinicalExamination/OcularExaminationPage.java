package pages.clinicalExamination;
import pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class OcularExaminationPage extends BasePage {

    public OcularExaminationPage(WebDriver driver) {
        super(driver);
    }

    // ===== TAB =====
    private By ocularExamTab = By.id("ocularExamination-tab");

    // ===== TOP SECTION =====
    private By ocularAlignment = By.id("CE_ddlOcularExamination_Motility");
    private By ocularMovement = By.id("CE_ddlOcularExamination_Movement");
    private By remarks = By.id("CE_txtOcularExamination_FreeRemarks");

    private By isNormalCheckbox = By.id("CE_OcularIsDefaultValue");

    // ===== RIGHT EYE (RE) =====
    private By re_lid = By.id("s2id_CE_ddlOcularExamination_RE_LID");
    private By re_roplas = By.id("s2id_CE_ddlOcularExamination_RE_Roplas");
    private By re_conjunctiva = By.id("s2id_CE_ddlOcularExamination_RE_Conjunctiva");
    private By re_cornea = By.id("s2id_CE_ddlOcularExamination_RE_Cornea");
    private By re_anteriorChamber = By.id("s2id_CE_ddlOcularExamination_RE_AnteriorChamber");
    private By re_pupil = By.id("s2id_CE_ddlOcularExamination_RE_Pupil");
    private By re_lens = By.id("s2id_CE_ddlOcularExamination_RE_Lens");
    private By re_fundus = By.id("s2id_CE_ddlOcularExamination_RE_Fundus");
    private By re_remarks = By.id("CE_txtOcularExamination_RE_Remarks");

    // ===== LEFT EYE (LE) =====
    private By le_lid = By.id("s2id_CE_ddlOcularExamination_LE_LID");
    private By le_roplas = By.id("s2id_CE_ddlOcularExamination_LE_Roplas");
    private By le_conjunctiva = By.id("s2id_CE_ddlOcularExamination_LE_Conjunctiva");
    private By le_cornea = By.id("s2id_CE_ddlOcularExamination_LE_Cornea");
    private By le_anteriorChamber = By.id("s2id_CE_ddlOcularExamination_LE_AnteriorChamber");
    private By le_pupil = By.id("s2id_CE_ddlOcularExamination_LE_Pupil");
    private By le_lens = By.id("s2id_CE_ddlOcularExamination_LE_Lens");
    private By le_fundus = By.id("s2id_CE_ddlOcularExamination_LE_Fundus");
    private By le_remarks = By.id("CE_txtOcularExamination_LE_Remarks");

    // ===== SAVE =====
    private By saveBtn = By.id("CE_btnAddUpadateOcularExamination");

    // ===== COMMON METHOD =====
	 private void selectDropdown(By locator, String visibleText) {

    WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));

    // Scroll to element
    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", dropdown);

    // Extra wait BEFORE click (important for your issue)
    wait.until(ExpectedConditions.visibilityOf(dropdown));
    wait.until(ExpectedConditions.elementToBeClickable(dropdown));

    try {
        // Click dropdown first (important)
        dropdown.click();

        // Small wait to avoid next dropdown opening issue
        Thread.sleep(500);

        // Select using Select class
        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);

    } catch (Exception e) {

        System.out.println("⚠️ Normal select failed, trying JS...");

        String script =
                "var select = arguments[0];" +
                "for (var i = 0; i < select.options.length; i++) {" +
                "   if (select.options[i].text.trim() === arguments[1]) {" +
                "       select.selectedIndex = i;" +
                "       select.dispatchEvent(new Event('change'));" +
                "       break;" +
                "   }" +
                "}";

        ((JavascriptExecutor) driver).executeScript(script, dropdown, visibleText);
    }

    // 🔴 MOST IMPORTANT: wait until value is actually selected
    wait.until(driver -> {
        Select s = new Select(dropdown);
        return s.getFirstSelectedOption().getText().trim().equals(visibleText);
    });

    // Small pause so next dropdown doesn't auto open
    try { Thread.sleep(400); } catch (InterruptedException ex) {}

    System.out.println("🟢 Selected: " + visibleText);
}

    public void type(By locator, String value) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(value);
    }

    private void scrollAndClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", element);
    }

    // ===== TAB OPEN =====
    public void openOcularExamTab() {
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(ocularExamTab));
        if (!tab.getAttribute("class").contains("active")) {
            scrollAndClick(tab);
        }
    }

    // ===== TOP METHODS =====

    public void selectOcularAlignment(String value) {
        selectDropdown(ocularAlignment, value);

        // Wait after alignment (VERY IMPORTANT)
        try { Thread.sleep(300); } catch (InterruptedException e) {}
    }
    
   
    public void selectOcularMovement(String value) {

        // Ensure alignment is already selected before movement
        wait.until(ExpectedConditions.presenceOfElementLocated(ocularAlignment));

        selectDropdown(ocularMovement, value);
    }

    public void enterRemarks(String value) {
        type(remarks, value);
    }

    public void clickIsNormal() {

        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(isNormalCheckbox));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", checkbox);

        try {
            // Try normal click
            wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();

        } catch (Exception e) {

            System.out.println("⚠️ Normal click failed, trying JS click...");

            // Fallback JS click
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", checkbox);
        }

        // 🔴 IMPORTANT: ensure checkbox is actually selected
        wait.until(driver -> checkbox.isSelected());

        System.out.println("🟢 Checkbox Selected");
    }

    // ===== RIGHT EYE METHODS =====
    public void fillRightEye(String lid, String roplas, String conjunctiva,
                             String cornea, String anteriorChamber,
                             String pupil, String lens, String fundus,
                             String remarks) {

    	selectSelect2Dropdown(re_lid, lid);
    	selectSelect2Dropdown(re_roplas, roplas);
    	selectSelect2Dropdown(re_conjunctiva, conjunctiva);
    	selectSelect2Dropdown(re_cornea, cornea);
    	selectSelect2Dropdown(re_anteriorChamber, anteriorChamber);
    	selectSelect2Dropdown(re_pupil, pupil);
    	selectSelect2Dropdown(re_lens, lens);
    	selectSelect2Dropdown(re_fundus, fundus);
        type(re_remarks, remarks);
    }

    // ===== LEFT EYE METHODS =====
    public void fillLeftEye(String lid, String roplas, String conjunctiva,
                            String cornea, String anteriorChamber,
                            String pupil, String lens, String fundus,
                            String remarks) {

    	selectSelect2Dropdown(le_lid, lid);
    	selectSelect2Dropdown(le_roplas, roplas);
    	selectSelect2Dropdown(le_conjunctiva, conjunctiva);
    	selectSelect2Dropdown(le_cornea, cornea);
    	selectSelect2Dropdown(le_anteriorChamber, anteriorChamber);
    	selectSelect2Dropdown(le_pupil, pupil);
    	selectSelect2Dropdown(le_lens, lens);
    	selectSelect2Dropdown(le_fundus, fundus);
        type(le_remarks, remarks);
    }

    // ===== SAVE =====
    public void clickSave() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
        scrollAndClick(btn);
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

    // ===== COMPLETE FLOW =====
//    public void fillOcularExamination(
//            String alignment, String movement, String remarksText,
//            String re_lidVal, String re_roplasVal, String re_conjVal,
//            String re_corneaVal, String re_acVal, String re_pupilVal,
//            String re_lensVal, String re_fundusVal, String re_remarkVal,
//            String le_lidVal, String le_roplasVal, String le_conjVal,
//            String le_corneaVal, String le_acVal, String le_pupilVal,
//            String le_lensVal, String le_fundusVal, String le_remarkVal
//    ) {
//
//        selectOcularAlignment(alignment);
//        selectOcularMovement(movement);
//        enterRemarks(remarksText);
//        
//        clickIsNormal();
//        
//        fillRightEye(re_lidVal, re_roplasVal, re_conjVal, re_corneaVal,
//                re_acVal, re_pupilVal, re_lensVal, re_fundusVal, re_remarkVal);
//
//        fillLeftEye(le_lidVal, le_roplasVal, le_conjVal, le_corneaVal,
//                le_acVal, le_pupilVal, le_lensVal, le_fundusVal, le_remarkVal);
//        
//        clickSave();
//    }
    
    //Condition check IsNormal//
    
    public void waitForEyeSectionToLoad() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(re_lid));
        wait.until(ExpectedConditions.elementToBeClickable(re_lid));

        System.out.println("✅ Eye section ready");
    }
    
   public void fillOcularExamination(
        boolean isNormalFlow,

        String alignment, String movement, String remarksText,
        String re_lidVal, String re_roplasVal, String re_conjVal,
        String re_corneaVal, String re_acVal, String re_pupilVal,
        String re_lensVal, String re_fundusVal, String re_remarkVal,
        String le_lidVal, String le_roplasVal, String le_conjVal,
        String le_corneaVal, String le_acVal, String le_pupilVal,
        String le_lensVal, String le_fundusVal, String le_remarkVal
	) {
	
	    selectOcularAlignment(alignment);
	    selectOcularMovement(movement);
	    enterRemarks(remarksText);
	
	    // 🔥 IMPORTANT WAIT
	    waitForEyeSectionToLoad();
	
	    if (isNormalFlow) {
	
	        System.out.println("⚡ Is Normal flow → Skipping Eye fields");
	
	        // OPTIONAL (agar required ho)
	//        clickIsNormal();
	
	    } else {
	
	        System.out.println("⚡ Manual flow → Filling Eye fields");
	
	        fillRightEye(re_lidVal, re_roplasVal, re_conjVal, re_corneaVal,
	                re_acVal, re_pupilVal, re_lensVal, re_fundusVal, re_remarkVal);
	
	        fillLeftEye(le_lidVal, le_roplasVal, le_conjVal, le_corneaVal,
	                le_acVal, le_pupilVal, le_lensVal, le_fundusVal, le_remarkVal);
	    }
	
	//    clickSave();
	}
	   
	// ===== VERIFICATION METHODS =====
	
	public boolean isNormalChecked() {
	    WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(isNormalCheckbox));
	    return checkbox.isSelected();
	}
	
	public boolean areEyeFieldsEnabled() {
	    WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(re_lid));
	    return el.isEnabled();
	}

	//===== DEFAULT VALUES =====
	private static final String DEFAULT_LID = "Normal";
	private static final String DEFAULT_ROPLAS = "Normal";
	private static final String DEFAULT_CONJUNCTIVA = "Normal";
	private static final String DEFAULT_CORNEA = "Clear";
	private static final String DEFAULT_AC = "Deep";
	private static final String DEFAULT_PUPIL = "Round";
	private static final String DEFAULT_LENS = "Clear";
	private static final String DEFAULT_FUNDUS = "Normal";
	private static final String DEFAULT_REMARKS = "Normal";
	    

	public void fillDefaultEyeValues() {
	
	    System.out.println("⚡ Auto-filling default eye values...");
	
	    fillRightEye(
	            DEFAULT_LID, DEFAULT_ROPLAS, DEFAULT_CONJUNCTIVA,
	            DEFAULT_CORNEA, DEFAULT_AC, DEFAULT_PUPIL,
	            DEFAULT_LENS, DEFAULT_FUNDUS, DEFAULT_REMARKS
	    );
	
	    fillLeftEye(
	            DEFAULT_LID, DEFAULT_ROPLAS, DEFAULT_CONJUNCTIVA,
	            DEFAULT_CORNEA, DEFAULT_AC, DEFAULT_PUPIL,
	            DEFAULT_LENS, DEFAULT_FUNDUS, DEFAULT_REMARKS
	    );
	}

	public void handleEyeSection(boolean isNormalFlow) {
	
	    waitForEyeSectionToLoad();
	
	    if (isNormalFlow) {
	
	        System.out.println("⚡ Is Normal selected → skipping fields");
	
	        clickIsNormal(); // checkbox ON
	
	    } else {
	
	        System.out.println("⚡ Is Normal NOT selected → auto fill");
	
	        // 🔥 IMPORTANT: ensure checkbox OFF
	        if (isNormalChecked()) {
	            clickIsNormal(); // toggle off
	        }
	
	        // 🔥 AUTO FILL
	        fillDefaultEyeValues();
	    }
	}

	public void fillOcularExamination(
	        boolean isNormalFlow,
	        String alignment,
	        String movement,
	        String remarksText
	) {
	
	    selectOcularAlignment(alignment);
	    selectOcularMovement(movement);
	    enterRemarks(remarksText);
	
	    handleEyeSection(isNormalFlow);
	
	    clickSave();
	}
}
