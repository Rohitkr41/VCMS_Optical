package pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ClinicalPage extends BasePage {

    public ClinicalPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By fromDateField = By.id("CE_txtFromDate");
    private By toDateField = By.id("CE_txtToDate");
    private By dateSearchCheck = By.id("CE_chkDateSearch");
    private By searchButton = By.id("CE_btnSearch");
    private By clinicalTab = By.id("clinical-tab");

    private By clinicalGrid = By.id("CE_dataGrid");
    private By clinicalGridRows = By.cssSelector("#CE_dataGrid tbody tr");
    private By clinicalGridProcessing = By.cssSelector("#CE_dataGrid_processing");
    private By statusIconInRow = By.cssSelector("td[name='key'] [title]");
    private By clinicalExaminationIconInRow = By.cssSelector("td[name='fieldFortyFive'] [title='Clinical Examination']");

    // ===== ACTION METHODS =====
    public void searchByDate(String fromDate, String toDate) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement fromDateElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(fromDateField)
        );

        WebElement toDateElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(toDateField)
        );

        WebElement dateCheckbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(dateSearchCheck)
        );

        // Date filter checkbox should be selected
        if (!dateCheckbox.isSelected()) {
            js.executeScript("arguments[0].click();", dateCheckbox);
        }

        // Set From Date
        js.executeScript(
                "arguments[0].removeAttribute('readonly');" +
                        "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                        "arguments[0].blur();",
                fromDateElement,
                fromDate
        );

        // Set To Date
        js.executeScript(
                "arguments[0].removeAttribute('readonly');" +
                        "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                        "arguments[0].blur();",
                toDateElement,
                toDate
        );

        WebElement searchBtn = wait.until(
                ExpectedConditions.elementToBeClickable(searchButton)
        );

        js.executeScript("arguments[0].click();", searchBtn);

        wait.until(ExpectedConditions.visibilityOfElementLocated(clinicalGrid));
        waitForClinicalGridToFinishLoading();

        List<WebElement> resultRows = getClinicalGridResultRows();

        if (resultRows.isEmpty()) {
            throw new NoSuchElementException(
                    "Clinical search returned no result rows for FromDate: " + fromDate + " and ToDate: " + toDate
            );
        }

        System.out.println("Clinical Search executed with FromDate: " + fromDate + " and ToDate: " + toDate);
    }

    public void clickClinicalExaminationForPendingRecord() {
        List<String> completedStatuses = Arrays.asList("Examine", "Examination Done");

        wait.until(ExpectedConditions.visibilityOfElementLocated(clinicalGrid));
        waitForClinicalGridToFinishLoading();

        List<WebElement> rows = getClinicalGridResultRows();

        if (rows.isEmpty()) {
            throw new NoSuchElementException("No clinical grid result rows found");
        }

        for (WebElement row : rows) {
            WebElement statusIcon;

            try {
                statusIcon = row.findElement(statusIconInRow);
            } catch (NoSuchElementException e) {
                continue;
            }

            String status = statusIcon.getAttribute("title").trim();

            if (completedStatuses.contains(status)) {
                System.out.println("Skipping completed patient status: " + status);
                continue;
            }

            WebElement clinicalExaminationIcon = row.findElement(clinicalExaminationIconInRow);

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});", clinicalExaminationIcon
            );

            try {
                clinicalExaminationIcon.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clinicalExaminationIcon);
            }

            System.out.println("Clinical Examination opened for patient status: " + status);
            return;
        }

        throw new NoSuchElementException("No pending patient found for Clinical Examination");
    }

    public void clickNewOrInProgressStatusIcon() {
        clickClinicalExaminationForPendingRecord();
    }

    public void clickNewStatusIcon() {
        clickClinicalExaminationForPendingRecord();
    }

    public void openClinicalTab() {
        WebElement tab = wait.until(
                ExpectedConditions.presenceOfElementLocated(clinicalTab)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", tab
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
    }

    private void waitForClinicalGridToFinishLoading() {
        wait.until(driver -> {
            List<WebElement> processingIndicators = driver.findElements(clinicalGridProcessing);

            if (processingIndicators.isEmpty()) {
                return true;
            }

            return !processingIndicators.get(0).isDisplayed();
        });

        try {
            wait.until(driver -> !driver.findElements(clinicalGridRows).isEmpty());
        } catch (TimeoutException e) {
            throw new TimeoutException("Clinical grid loaded, but no table rows were rendered.", e);
        }
    }

    private List<WebElement> getClinicalGridResultRows() {
        return driver.findElements(clinicalGridRows)
                .stream()
                .filter(WebElement::isDisplayed)
                .filter(row -> row.findElements(By.cssSelector("td.dataTables_empty")).isEmpty())
                .toList();
    }
}
