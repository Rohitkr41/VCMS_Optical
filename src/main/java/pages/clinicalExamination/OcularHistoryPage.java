package pages.clinicalExamination;

import pages.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class OcularHistoryPage extends BasePage {

    public OcularHistoryPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By ocularHistoryTab = By.id("ocularSystemicDisease-tab");

    private By diseaseDropdown = By.id("s2id_CE_ddlOcularDiseaseName");
    private By surgeryDropdown = By.id("s2id_CE_ddlOcularDiseaseSurgery");

    private By eyeDropdown = By.id("CE_txtOcularDiseaseEyeSubSection");
    private By statusDropdown = By.id("CE_ddlOcularDiseaseStatus");
    private By remarksField = By.id("CE_ddlOcularDiseaseRemarks");
    private By saveButton = By.id("CE_btnAddUpdateOcularDisease");

    private By successPopup = By.id("popup_message");
    private By okButton = By.id("popup_ok");

    // ===== SYSTEMIC =====
    private By anyOfDropdown = By.id("CE_ddlSystemicDiseasesYesNo");
    private By systemicDiseaseDropdown = By.id("s2id_CE_ddlAUSystemicHistory");
    private By durationDropdown = By.id("CE_ddlSYSPeriod");
    private By periodDropdown = By.id("CE_ddlSYSDuration");
    private By systemicStatusDropdown = By.id("CE_ddlSYSStatus");
    private By systemicRemarksField = By.id("CE_ddlSystemiceRemarks");
    private By systemicSaveButton = By.id("CE_btnAddUpdateSystemicDiseases");

    // ===== TAB =====
    public void openOcularHistoryTab() {
        WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(ocularHistoryTab));
        if (!tab.getAttribute("class").contains("active")) {
            scrollAndClick(tab);
        }
    }

    // ===== SELECT2 =====
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

    // ===== OCULAR METHODS =====
    public void selectDisease(String disease) {
        selectSelect2Dropdown(diseaseDropdown, disease);
    }

    public void selectEye(String eye) {
        new Select(wait.until(ExpectedConditions.elementToBeClickable(eyeDropdown)))
                .selectByVisibleText(eye);
    }

    public void selectSurgeryTreatmentDone(String value) {
        selectSelect2Dropdown(surgeryDropdown, value);
    }

    public void selectStatus(String status) {
        new Select(wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)))
                .selectByVisibleText(status);
    }

    public void enterRemarks(String remarks) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(remarksField));
        input.clear();
        input.sendKeys(remarks);
    }

    public void clickSaveButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        scrollAndClick(btn);
    }

    // ===== SYSTEMIC METHODS =====
    public void selectAnyOf(String value) {

        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(anyOfDropdown));

        try { Thread.sleep(500); } catch (Exception ignored) {}

        String val = value.equalsIgnoreCase("yes") ? "1"
                : value.equalsIgnoreCase("no") ? "0" : "-1";

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value='" + val + "';" +
                        "arguments[0].dispatchEvent(new Event('change'));", dropdown);

        wait.until(d -> {
            try {
                return new Select(d.findElement(anyOfDropdown))
                        .getFirstSelectedOption().getAttribute("value").equals(val);
            } catch (Exception e) {
                return false;
            }
        });

        try { Thread.sleep(800); } catch (Exception ignored) {}

        wait.until(ExpectedConditions.visibilityOfElementLocated(systemicDiseaseDropdown));
    }

    public void selectSystemicDisease(String disease) {
        selectSelect2Dropdown(systemicDiseaseDropdown, disease);
    }

    public void selectDuration(String duration) {
        new Select(wait.until(ExpectedConditions.elementToBeClickable(durationDropdown)))
                .selectByVisibleText(duration);
    }

    public void selectPeriod(String period) {
        new Select(wait.until(ExpectedConditions.elementToBeClickable(periodDropdown)))
                .selectByVisibleText(period);
    }

    public void selectSystemicStatus(String status) {
        new Select(wait.until(ExpectedConditions.elementToBeClickable(systemicStatusDropdown)))
                .selectByVisibleText(status);
    }

    public void enterSystemicRemarks(String remarks) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(systemicRemarksField));
        input.clear();
        input.sendKeys(remarks);
    }

    public void clickSystemicSaveButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(systemicSaveButton));
        scrollAndClick(btn);
    }

    public void handleSuccessPopup() {
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
        System.out.println("✅ Popup: " + popup.getText());
        wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
    }

    // ===== UTIL =====
    private void scrollAndClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", element);
    }

    // ===== FLOW =====
    public void fillSystemicDisease(String anyOf, String disease,
                                   String duration, String period,
                                   String status, String remarks) {

        selectAnyOf(anyOf);
        selectSystemicDisease(disease);
        selectDuration(duration);
        selectPeriod(period);
        selectSystemicStatus(status);
        enterSystemicRemarks(remarks);
        clickSystemicSaveButton();
        handleSuccessPopup();
    }
}
//<<<<<<< HEAD
//}
//=======
//}
//>>>>>>> branch 'master' of https://github.com/Rohitkr41/EicherProject
