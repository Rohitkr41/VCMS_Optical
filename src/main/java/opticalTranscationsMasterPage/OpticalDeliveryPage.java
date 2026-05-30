package opticalTranscationsMasterPage;

import pages.BasePage;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OpticalDeliveryPage extends BasePage {

    public OpticalDeliveryPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By opticalTransactionsMenu = By.xpath("//span[normalize-space()='Optical Transactions']/ancestor::a");
    private By opticalDeliveryMenu = By.cssSelector("a[href='/VCMS_Optical/OpticalBooking/ViewOpticalDelivery']");

    private By fromDateInput = By.id("VOD_txtFromDate");
    private By toDateInput = By.id("VOD_txtToDate");
    private By dateSearchCheckbox = By.id("VOD_chkDateSearch");
    private By searchButton = By.id("VOD_btnSearch");

    private By overlay = By.id("V3MOverlay");

    private By opticalDeliveryRows = By.xpath("//table[@id='VOD_tblRecord']//tbody//tr");
    private By waitingToReceiveButton = By.id("VOD_btnHold");

    // ===== NAVIGATION =====
    public void openOpticalDelivery() {
        try {
            WebElement menu = driver.findElement(opticalTransactionsMenu);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", menu);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);

            WebElement deliveryMenu = wait.until(
                    ExpectedConditions.presenceOfElementLocated(opticalDeliveryMenu)
            );

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deliveryMenu);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deliveryMenu);

            wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));

        } catch (Exception e) {
            throw new RuntimeException("Unable to navigate to Optical Delivery page.", e);
        }
    }

    // ===== DATE FILTER =====
    public void enableDateSearch() {
        WebElement checkbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(dateSearchCheckbox)
        );

        if (!checkbox.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
    }

    public void enterFromDate(String fromDate) {
        setReadonlyDate(fromDateInput, fromDate);
    }

    public void enterToDate(String toDate) {
        setReadonlyDate(toDateInput, toDate);
    }

    private void setReadonlyDate(By locator, String dateValue) {
        WebElement dateInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('readonly');" +
                "arguments[0].value = arguments[1];" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                dateInput,
                dateValue
        );
    }

    public void clickSearch() {
        WebElement searchBtn = wait.until(
                ExpectedConditions.elementToBeClickable(searchButton)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                searchBtn
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);

        waitForLoaderToDisappear();

        wait.until(ExpectedConditions.presenceOfElementLocated(opticalDeliveryRows));
    }

    public void searchByDate(String fromDate, String toDate) {
        openOpticalDelivery();
        enableDateSearch();
        enterFromDate(fromDate);
        enterToDate(toDate);
        clickSearch();
    }

    // ===== WAITING TO RECEIVE FLOW =====
    public void selectWaitingToReceiveRecordFromList() {
        waitForLoaderToDisappear();

        List<WebElement> rows = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(opticalDeliveryRows)
        );

        WebElement matchedRow = null;

        for (WebElement row : rows) {
            String rowText = row.getText().trim();

            if (rowText.contains("Waiting to Receive")) {
                matchedRow = row;
                System.out.println("Selected Waiting to Receive row: " + rowText);
                break;
            }
        }

        if (matchedRow == null) {
            throw new RuntimeException("No Waiting to Receive record found in Optical Delivery list.");
        }

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                matchedRow
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                matchedRow
        );

        try {
            wait.until(ExpectedConditions.attributeContains(matchedRow, "class", "selectedrow"));
        } catch (Exception ignored) {
            System.out.println("Row clicked, but selectedrow class was not found.");
        }
    }

    public void clickWaitingToReceiveButton() {
        WebElement holdButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(waitingToReceiveButton)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                holdButton
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", holdButton);

        waitForLoaderToDisappear();
    }

    public void selectWaitingToReceiveRecordAndClickHold() {
        selectWaitingToReceiveRecordFromList();
        clickWaitingToReceiveButton();
    }

    public void searchByDateAndClickWaitingToReceive(String fromDate, String toDate) {
        searchByDate(fromDate, toDate);
        selectWaitingToReceiveRecordAndClickHold();
    }

    protected void waitForLoaderToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (Exception ignored) {
        }
    }
}