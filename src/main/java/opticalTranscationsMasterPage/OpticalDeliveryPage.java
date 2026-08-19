package opticalTranscationsMasterPage;

import pages.BasePage;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OpticalDeliveryPage extends BasePage {

    public OpticalDeliveryPage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By opticalTransactionsMenu = By.xpath(
            "//a[@data-toggle='collapse' and @href='#OpticalTransacations']" +
            "[.//span[normalize-space()='Optical Transactions']]"
    );

    private By opticalTransactionsPanel = By.id("OpticalTransacations");

    private By opticalDeliveryMenu = By.xpath(
            "//div[@id='OpticalTransacations']//a" +
            "[.//span[normalize-space()='Optical Delivery'] or normalize-space()='Optical Delivery' " +
            " or contains(@href,'/OpticalBooking/ViewOpticalDelivery')]"
    );

    private By fromDateInput = By.id("VOD_txtFromDate");
    private By toDateInput = By.id("VOD_txtToDate");
    private By dateSearchCheckbox = By.id("VOD_chkDateSearch");
    private By searchButton = By.id("VOD_btnSearch");

    private By overlay = By.id("V3MOverlay");

    private By opticalDeliveryRows = By.xpath("//table[@id='VOD_tblRecord']//tbody//tr");
    private By waitingToReceiveRows = By.xpath(
            "//table[@id='VOD_tblRecord']//tbody//tr" +
            "[.//*[normalize-space()='Waiting to Receive']]"
    );
    private By holdRows = By.xpath(
            "//table[@id='VOD_tblRecord']//tbody//tr" +
            "[.//*[normalize-space()='Hold']]"
    );
    private By waitingToReceiveOrHoldRows = By.xpath(
            "//table[@id='VOD_tblRecord']//tbody//tr" +
            "[.//*[normalize-space()='Waiting to Receive' or normalize-space()='Hold']]"
    );
    private By waitingToReceiveButton = By.id("VOD_btnHold");

    private By waitingToReceiveModal = By.xpath(
            "//div[contains(@class,'modal') and .//*[@id='VBO_btnSubmitQualityCheck']]"
    );
    private By holdRadioButton = By.id("VOD_rdbHold");
    private By receiveRadioButton = By.id("VOD_rdbReceive");
    private By lenseQcDropdown = By.id("VBO_ddlLenseQC");
    private By fittingQcDropdown = By.id("VBO_ddlFittingQC");
    private By powerMatchingDropdown = By.id("VBO_ddlPowerMatching");
    private By finalRemarkInput = By.id("VBO_txtFinalRemarks");
    private By qualityCheckSubmitButton = By.id("VBO_btnSubmitQualityCheck");

    // ===== NAVIGATION =====
    public void openOpticalDelivery() {
        try {
            waitForLoaderToDisappear();

            WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(opticalTransactionsMenu));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    menu
            );

            String expanded = menu.getAttribute("aria-expanded");
            if (!"true".equalsIgnoreCase(expanded)) {
                clickWithJs(menu);
            }

            wait.until(ExpectedConditions.attributeContains(opticalTransactionsMenu, "aria-expanded", "true"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(opticalTransactionsPanel));

            WebElement deliveryMenu = wait.until(ExpectedConditions.elementToBeClickable(opticalDeliveryMenu));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    deliveryMenu
            );
            clickWithJs(deliveryMenu);

            wait.until(ExpectedConditions.urlContains("/OpticalBooking/ViewOpticalDelivery"));
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
            clickWithJs(checkbox);
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

        clickWithJs(searchBtn);

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
                ExpectedConditions.presenceOfAllElementsLocatedBy(waitingToReceiveRows)
        );

        selectRow(rows.get(0));
    }

    public void selectHoldRecordFromList() {
        waitForLoaderToDisappear();

        List<WebElement> rows = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(holdRows)
        );

        selectRow(rows.get(0));
    }

    public void selectWaitingToReceiveOrHoldRecordFromList() {
        waitForLoaderToDisappear();

        List<WebElement> rows = driver.findElements(waitingToReceiveOrHoldRows);

        if (rows.isEmpty()) {
            System.out.println("No Waiting to Receive or Hold record found in Optical Delivery list.");
            return;
        }

        selectRow(rows.get(0));
    }

    public void clickWaitingToReceiveButton() {
        WebElement holdButton = wait.until(
                ExpectedConditions.elementToBeClickable(waitingToReceiveButton)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                holdButton
        );

        clickWithJs(holdButton);

        waitForLoaderToDisappear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(waitingToReceiveModal));
    }

    public void selectFirstWaitingToReceiveRecordAndClickIcon() {
        selectWaitingToReceiveRecordFromList();
        clickWaitingToReceiveButton();
    }

    public void selectFirstHoldRecordAndClickIcon() {
        selectHoldRecordFromList();
        clickWaitingToReceiveButton();
    }

    public void selectFirstWaitingToReceiveOrHoldRecordAndClickIcon() {
        if (!selectFirstWaitingToReceiveOrHoldRecordIfAvailable()) {
            return;
        }

        clickWaitingToReceiveButton();
    }

    public void processAllWaitingToReceiveRecordsAsHold(
            String lenseQc,
            String fittingQc,
            String powerMatching,
            String finalRemark
    ) {
        waitForLoaderToDisappear();

        while (true) {
            List<WebElement> rows = driver.findElements(waitingToReceiveOrHoldRows);

            if (rows.isEmpty()) {
                System.out.println("No more Waiting to Receive or Hold records found.");
                break;
            }

            selectRow(rows.get(0));
            clickWaitingToReceiveButton();
            submitWaitingToReceiveAsHold(lenseQc, fittingQc, powerMatching, finalRemark);
            waitForLoaderToDisappear();
        }
    }

    public void processAllWaitingToReceiveRecordsAsReceive(String finalRemark) {
        waitForLoaderToDisappear();

        while (true) {
            List<WebElement> rows = driver.findElements(waitingToReceiveOrHoldRows);

            if (rows.isEmpty()) {
                System.out.println("No more Waiting to Receive or Hold records founds.");
                break;
            }

            selectRow(rows.get(0));
            clickWaitingToReceiveButton();
            submitWaitingToReceiveAsReceive(finalRemark);
            waitForLoaderToDisappear();
        }
    }

    public void selectWaitingToReceiveRecordAndClickHold() {
        selectWaitingToReceiveRecordFromList();
        clickWaitingToReceiveButton();
    }

    public void searchByDateAndClickWaitingToReceive(String fromDate, String toDate) {
        searchByDate(fromDate, toDate);
        selectWaitingToReceiveRecordAndClickHold();
    }

    public void submitWaitingToReceiveAsHold(
            String lenseQc,
            String fittingQc,
            String powerMatching,
            String finalRemark
    ) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(waitingToReceiveModal));
        clickRadioButton(holdRadioButton);

        selectDropdownByValue(lenseQcDropdown, lenseQc);
        selectDropdownByValue(fittingQcDropdown, fittingQc);
        selectDropdownByValue(powerMatchingDropdown, powerMatching);
        enterFinalRemark(finalRemark);

        clickQualityCheckSubmitButton();
    }

    public void submitWaitingToReceiveAsReceive(String finalRemark) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(waitingToReceiveModal));
        clickRadioButton(receiveRadioButton);

        enterFinalRemark(finalRemark);

        clickQualityCheckSubmitButton();
    }

    public void processFirstWaitingToReceiveRecordAsHold(
            String lenseQc,
            String fittingQc,
            String powerMatching,
            String finalRemark
    ) {
        selectFirstWaitingToReceiveRecordAndClickIcon();
        submitWaitingToReceiveAsHold(lenseQc, fittingQc, powerMatching, finalRemark);
    }

    public void processFirstWaitingToReceiveRecordAsReceive(String finalRemark) {
        selectFirstWaitingToReceiveRecordAndClickIcon();
        submitWaitingToReceiveAsReceive(finalRemark);
    }

    public void processFirstHoldRecordAsHold(
            String lenseQc,
            String fittingQc,
            String powerMatching,
            String finalRemark
    ) {
        selectFirstHoldRecordAndClickIcon();
        submitWaitingToReceiveAsHold(lenseQc, fittingQc, powerMatching, finalRemark);
    }

    public void processFirstHoldRecordAsReceive(String finalRemark) {
        selectFirstHoldRecordAndClickIcon();
        submitWaitingToReceiveAsReceive(finalRemark);
    }

    public void processFirstWaitingToReceiveOrHoldRecordAsHold(
            String lenseQc,
            String fittingQc,
            String powerMatching,
            String finalRemark
    ) {
        if (!selectFirstWaitingToReceiveOrHoldRecordIfAvailable()) {
            return;
        }

        clickWaitingToReceiveButton();
        submitWaitingToReceiveAsHold(lenseQc, fittingQc, powerMatching, finalRemark);
    }

    public void processFirstWaitingToReceiveOrHoldRecordAsReceive(String finalRemark) {
        if (!selectFirstWaitingToReceiveOrHoldRecordIfAvailable()) {
            return;
        }

        clickWaitingToReceiveButton();
        submitWaitingToReceiveAsReceive(finalRemark);
    }

    public boolean selectFirstWaitingToReceiveOrHoldRecordIfAvailable() {
        waitForLoaderToDisappear();

        List<WebElement> rows = driver.findElements(waitingToReceiveOrHoldRows);

        if (rows.isEmpty()) {
            System.out.println("No Waiting to Receive or Hold record found in Optical Delivery list.");
            return false;
        }

        selectRow(rows.get(0));
        return true;
    }

    private void selectRow(WebElement row) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                row
        );

        clickWithJs(row);
        System.out.println("Selected Optical Delivery row: " + row.getText().trim());

        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.attributeContains(row, "class", "selectedrow"),
                    ExpectedConditions.attributeContains(row, "class", "selected")
            ));
        } catch (Exception ignored) {
            System.out.println("Row clicked, but selected class was not found.");
        }
    }

    private void clickRadioButton(By locator) {
        WebElement radioButton = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        if (!radioButton.isSelected()) {
            clickWithJs(radioButton);
        }
    }

    private void selectDropdownByValue(By locator, String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(dropdown);

        try {
            select.selectByValue(value);
        } catch (NoSuchElementException e) {
            select.selectByVisibleText(value);
        }
    }

    private void enterFinalRemark(String finalRemark) {
        WebElement remarkInput = wait.until(ExpectedConditions.elementToBeClickable(finalRemarkInput));
        remarkInput.clear();
        remarkInput.sendKeys(finalRemark);
    }

    private void clickQualityCheckSubmitButton() {
        WebElement submitButton = wait.until(
                ExpectedConditions.elementToBeClickable(qualityCheckSubmitButton)
        );

        clickWithJs(submitButton);
        waitForLoaderToDisappear();
        acceptAlertIfPresent();

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(waitingToReceiveModal));
        } catch (Exception ignored) {
            System.out.println("Quality check submitted, but modal did not close within wait time.");
        }
    }

    private void acceptAlertIfPresent() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception ignored) {
        }
    }

    private void clickWithJs(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void waitForLoaderToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (Exception ignored) {
        }
    }
}
