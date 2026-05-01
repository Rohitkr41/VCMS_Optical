package pages.clinicalExamination;

import pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;

public class ClinicalPage extends BasePage {

    public ClinicalPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By clinicalMenu   = By.xpath("//span[normalize-space()='Clinical Examination']");
//    private By fromDateField  = By.id("CE_txtFromDate");
//    private By toDateField    = By.id("CE_txtToDate");
    private By searchButton   = By.id("CE_btnSearch");

    private By newStatusIcon  = By.xpath("//*[@id=\"row1\"]/td[46]/a/span/i");
    private By clinicalTab    = By.xpath("//*[@id='clinical-tab']");

    // ===== ACTION METHODS =====
    public void openClinicalMenu() {
        click(clinicalMenu);
    }

    // ✅ Search by Date (same structure as BP & Sugar)
    public void searchByDate(String fromDate, String toDate) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Set From Date
        js.executeScript(
            "document.getElementById('CE_txtFromDate').value='" + fromDate + "';" +
            "$('#CE_txtFromDate').trigger('change');"
        );

        // Set To Date
        js.executeScript(
            "document.getElementById('CE_txtToDate').value='" + toDate + "';" +
            "$('#CE_txtToDate').trigger('change');"
        );

        // Click Search button
        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchBtn.click();

        // Wait for table update
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='row1']")));

        System.out.println("✅ Clinical Search executed with FromDate: " + fromDate + " and ToDate: " + toDate);
    }

    public void clickNewStatusIcon() {
        click(newStatusIcon);
    }

    public void openClinicalTab() {
        click(clinicalTab);
    }
}

