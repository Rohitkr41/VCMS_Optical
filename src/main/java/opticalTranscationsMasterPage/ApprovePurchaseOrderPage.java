package opticalTranscationsMasterPage;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.BasePage;

public class ApprovePurchaseOrderPage extends BasePage {

    public ApprovePurchaseOrderPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By opticalTransactionsMenu = By.xpath("//span[normalize-space()='Optical Transactions']/ancestor::a");
    private By approvePurchaseOrderMenu = By.cssSelector("a[href='/VCMS_Optical/OpticalRequest/ViewPOApproval']");

    private By regionalDropdown = By.id("APO_ddlSearchEyeHospital");
    private By supplierNameInput = By.id("APO_txtSearchSupplierName");
    private By poNumberInput = By.id("APO_txtPuchaseOrderNo");
    private By fromDateInput = By.id("APO_txtFromDate");
    private By toDateInput = By.id("APO_txtToDate");
    private By searchButton = By.id("APO_btnSearch");

    private By poTableRows = By.xpath("//table[@id='APO_tblRecord']//tbody//tr");
    private By poStatusLabelInRow = By.cssSelector("td[name='code'] label");
    private By approveButton = By.id("APO_btnApprovePONew");

    private By overlay = By.id("V3MOverlay");

    // ===== NAVIGATION =====
    public void navigateToApprovePurchaseOrder() {
        try {
            WebElement menu = driver.findElement(opticalTransactionsMenu);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", menu);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);

            WebElement approveMenu = driver.findElement(approvePurchaseOrderMenu);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", approveMenu);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", approveMenu);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File("D:\\rohit\\VcmsOptical\\screenshots\\afterMenuClick.png"));

        } catch (Exception e) {
            throw new RuntimeException("Unable to navigate to Approve Purchase Order page.", e);
        }
    }

    // ===== PAGE OPEN CHECK =====
    public boolean isPageOpened() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));
            return driver.findElement(searchButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ===== FILTER POs =====
    public void filterPurchaseOrders(String regional, String supplierName, String poNumber, String fromDate, String toDate) {

        waitForOverlayToDisappear();

        if (regional != null && !regional.trim().isEmpty()) {
            selectDropdownByNormalizedText(regionalDropdown, regional);
        }

        if (supplierName != null && !supplierName.trim().isEmpty()) {
            driver.findElement(supplierNameInput).clear();
            driver.findElement(supplierNameInput).sendKeys(supplierName);
        }

        if (poNumber != null && !poNumber.trim().isEmpty()) {
            driver.findElement(poNumberInput).clear();
            driver.findElement(poNumberInput).sendKeys(poNumber);
        }

        setDateValue(fromDateInput, fromDate);
        setDateValue(toDateInput, toDate);

        driver.findElement(searchButton).click();
        waitForOverlayToDisappear();
    }

    // ===== APPROVE NEW PO RECORDS =====
    public void approveNewPORecords(String remark) {

        List<WebElement> rows = driver.findElements(poTableRows);

        if (rows.isEmpty()) {
            System.out.println("No records found in the table.");
            return;
        }

        boolean anyApproved = false;

        for (WebElement row : rows) {
            try {
                WebElement statusCell = row.findElement(poStatusLabelInRow);
                String status = normalizeText(statusCell.getText());

                if (isNewPoStatus(status)) {

                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", row);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", row);

                    WebElement approveBtn = driver.findElement(approveButton);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", approveBtn);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", approveBtn);

                    waitForOverlayToDisappear();
                    submitApprovalRemark(remark);
                    confirmApprovalPopup();

                    anyApproved = true;
                    Thread.sleep(1000);
                }

            } catch (NoSuchElementException e) {
                continue;
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

        if (!anyApproved) {
            System.out.println("No 'New PO' records found to approve.");
        }
    }

    private boolean isNewPoStatus(String status) {
        return "New PO".equalsIgnoreCase(status) || "New".equalsIgnoreCase(status);
    }

    // ===== SUBMIT APPROVAL REMARK =====
    public void submitApprovalRemark(String remark) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement remarkTextarea = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("APO_txtPOApprovalRemark"))
            );

            remarkTextarea.clear();
            remarkTextarea.sendKeys(remark);

            WebElement approveModalBtn = driver.findElement(By.id("APO_btnApprovePO"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", approveModalBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", approveModalBtn);

            waitForOverlayToDisappear();

            System.out.println("Approval submitted with remark: " + remark);

        } catch (Exception e) {
            throw new RuntimeException("Failed to submit approval remark.", e);
        }
    }

    // ===== HELPER =====
    private void waitForOverlayToDisappear() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (Exception e) {
            // ignore if overlay not present
        }
    }

    private void selectDropdownByNormalizedText(By dropdownLocator, String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));

        wait.until(driver -> new Select(dropdown).getOptions().size() > 1);

        Select select = new Select(dropdown);
        String expected = normalizeText(expectedText);

        for (WebElement option : select.getOptions()) {
            if (normalizeText(option.getText()).equalsIgnoreCase(expected)) {
                select.selectByVisibleText(option.getText());
                return;
            }
        }

        for (WebElement option : select.getOptions()) {
            String actual = normalizeText(option.getText());
            if (actual.toLowerCase().contains(expected.toLowerCase())
                    || expected.toLowerCase().contains(actual.toLowerCase())) {
                select.selectByVisibleText(option.getText());
                return;
            }
        }

        String availableOptions = select.getOptions()
            .stream()
            .map(WebElement::getText)
            .map(this::normalizeText)
            .filter(text -> !text.isEmpty())
            .collect(Collectors.joining(" | "));

        throw new NoSuchElementException(
            "Cannot locate dropdown option: '" + expectedText + "'. Available options: " + availableOptions
        );
    }

    private void setDateValue(By locator, String value) {
        if (value == null || value.trim().isEmpty()) {
            return;
        }

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].value=arguments[1]; arguments[0].dispatchEvent(new Event('change'));",
            driver.findElement(locator),
            value
        );
    }

    private String normalizeText(String text) {
        if (text == null) {
            return "";
        }

        return text.replace('\u00A0', ' ')
            .replaceAll("\\s+", " ")
            .trim();
    }

    /**
     * Clicks the "Yes" button on the approval confirmation popup.
     */
    public void confirmApprovalPopup() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement yesButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("popup_ok"))
            );

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", yesButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yesButton);

            waitForOverlayToDisappear();

            System.out.println("Clicked 'Yes' on approval confirmation popup.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to click 'Yes' on approval confirmation popup.", e);
        }
    }
}
