package pages.clinicalExamination;
import pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.StaleElementReferenceException;


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
    System.out.println("⚡ Clicking Is Normal checkbox");

    setIsNormalCheckbox(true);

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }

    System.out.println("🟢 Is Normal checkbox selected");
}


	    
	    private void setIsNormalCheckbox(boolean shouldBeChecked) {
    WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(isNormalCheckbox));

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", checkbox);

    if (checkbox.isSelected() != shouldBeChecked) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
    }

    wait.until(driver -> {
        WebElement freshCheckbox = driver.findElement(isNormalCheckbox);
        return freshCheckbox.isSelected() == shouldBeChecked;
    });

    System.out.println("🟢 Is Normal checkbox value: " + shouldBeChecked);
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
    
    private By successPopup = By.id("popup_container");
    private By successPopupMessage = By.id("popup_message");
    private By successPopupOk = By.id("popup_ok");

    public void handleSuccessPopup() {
        WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(successPopup));

        String message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(successPopupMessage))
                .getText();

        System.out.println("🟢 Popup message: " + message);

        WebElement okBtn = wait.until(
                ExpectedConditions.elementToBeClickable(successPopupOk));

        try {
            okBtn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okBtn);
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
    }


    // ===== SAVE =====
    public void clickSave() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("select2-drop")));

        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(saveBtn));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", btn);

        wait.until(ExpectedConditions.elementToBeClickable(btn));

        try {
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }

        System.out.println("🟢 Save clicked");

        handleSuccessPopup();
    }

    
   private void selectSelect2Dropdown(By locator, String value) {

    for (int attempt = 1; attempt <= 3; attempt++) {
        try {
            WebElement container = wait.until(ExpectedConditions.elementToBeClickable(locator));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", container);

            container.click();

            WebElement dropdown = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("select2-drop")));

            try {
                WebElement searchBox = dropdown.findElement(By.cssSelector("input.select2-input"));
                searchBox.clear();
                searchBox.sendKeys(value);
            } catch (Exception ignored) {}

            By option = By.xpath(
                    "//div[@id='select2-drop']//li[contains(@class,'select2-result-selectable')]//div[contains(normalize-space(),'" + value + "')]"
            );

            WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(option));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", optionElement);

            try {
                optionElement.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", optionElement);
            }

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("select2-drop")));

            System.out.println("🟢 Selected: " + value);
            return;

        } catch (StaleElementReferenceException e) {
            System.out.println("⚠️ Stale element found, retrying Select2: " + value + " attempt " + attempt);

            try {
                Thread.sleep(700);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    throw new RuntimeException("Unable to select value after retries: " + value);
}


  
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
        String le_lensVal, String le_fundusVal, String le_remarkVals
) {

    selectOcularAlignment(alignment);
    selectOcularMovement(movement);
    enterRemarks(remarksText);

    waitForEyeSectionToLoad();

    if (isNormalFlow) {

        System.out.println("⚡ Is Normal flow → only checkbox click, no manual eye fill");

        clickIsNormal();

    } else {

        System.out.println("⚡ Manual flow → checkbox OFF + manual eye values fill");

        setIsNormalCheckbox(false);

        fillRightEye(re_lidVal, re_roplasVal, re_conjVal, re_corneaVal,
                re_acVal, re_pupilVal, re_lensVal, re_fundusVal, re_remarkVal);

        fillLeftEye(le_lidVal, le_roplasVal, le_conjVal, le_corneaVal,
                le_acVal, le_pupilVal, le_lensVal, le_fundusVal, le_remarkVals);
    }

    clickSave();
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
