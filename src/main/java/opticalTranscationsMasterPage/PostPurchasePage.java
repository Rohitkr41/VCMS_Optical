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

public class PostPurchasePage extends BasePage {

    public PostPurchasePage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================

    private By opticalTransactionsMenu =
            By.xpath("//span[normalize-space()='Optical Transactions']/ancestor::a");

    private By purchaseMenu =
            By.cssSelector("a[href='/VCMS_Optical/OpticalRequest/ViewGoodsReceiptNotes']");

    private By searchButton = By.id("VGRN_btnSearch");

    private By regionalDropdown = By.id("VGRN_ddlSearchRegional");

    private By supplierInput = By.id("VGRN_txtSupplierName");

    private By poNumberInput = By.id("VGRN_txtPONumber");

    private By purchaseNumberInput = By.id("VGRN_txtPurchaseNumber");

    private By fromDateInput = By.id("VGRN_txtFromDate");

    private By toDateInput = By.id("VGRN_txtToDate");

    private By tableRows =
            By.xpath("//table[@id='VGRN_tblRecord']//tbody//tr");

    private By postPurchaseButton = By.id("VGRN_btnPostGRN");

    private By overlay = By.id("V3MOverlay");

    // ================= NAVIGATION =================

    public void navigateToPostPurchasePage() {

        try {

            WebElement menu = driver.findElement(opticalTransactionsMenu);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", menu);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", menu);

            WebElement purchasePage = driver.findElement(purchaseMenu);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", purchasePage);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", purchasePage);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(src,
                    new File("D:\\rohit\\VcmsOptical\\screenshots\\PostPurchasePage.png"));

            System.out.println("Navigated to Post Purchase Page Successfully");

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to navigate to Post Purchase Page.", e);
        }
    }

    // ================= PAGE OPEN CHECK =================

    public boolean isPageOpened() {

        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton));

            return driver.findElement(searchButton).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    // ================= FILTER RECORDS =================

    public void filterPurchaseRecords(String regional,
                                      String supplier,
                                      String poNumber,
                                      String purchaseNumber,
                                      String fromDate,
                                      String toDate) {

        waitForOverlayToDisappear();

        if (regional != null && !regional.trim().isEmpty()) {
            selectDropdownByNormalizedText(regionalDropdown, regional);
        }

        if (supplier != null && !supplier.trim().isEmpty()) {

            driver.findElement(supplierInput).clear();
            driver.findElement(supplierInput).sendKeys(supplier);
        }

        if (poNumber != null && !poNumber.trim().isEmpty()) {

            driver.findElement(poNumberInput).clear();
            driver.findElement(poNumberInput).sendKeys(poNumber);
        }

        if (purchaseNumber != null && !purchaseNumber.trim().isEmpty()) {

            driver.findElement(purchaseNumberInput).clear();
            driver.findElement(purchaseNumberInput).sendKeys(purchaseNumber);
        }

        setDateValue(fromDateInput, fromDate);
        setDateValue(toDateInput, toDate);

        driver.findElement(searchButton).click();

        waitForOverlayToDisappear();
    }

    // ================= SELECT RECORD & POST =================

   public void selectRecordAndPostPurchase(String purchaseNo) {

    try {

        waitForOverlayToDisappear();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(20));

        // ===== CLICK SEARCH BUTTON FIRST =====
        WebElement searchBtn =
                wait.until(ExpectedConditions
                        .elementToBeClickable(searchButton));

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView(true);",
                        searchBtn
                );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        searchBtn
                );

        System.out.println("Clicked Search Button");

        waitForOverlayToDisappear();

        // ===== WAIT FOR TABLE RECORDS =====
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                        By.xpath("//table[@id='VGRN_tblRecord']//tbody//tr")
                ));

        List<WebElement> rows =
                driver.findElements(tableRows);

        if (rows.isEmpty()) {

            throw new RuntimeException(
                    "No Purchase records found in table."
            );
        }

        boolean recordFound = false;

        for (WebElement row : rows) {

            try {

                String rowText =
                        normalizeText(row.getText());

                System.out.println("Row Text : " + rowText);

                if (rowText.contains(
                        normalizeText(purchaseNo))) {

                    recordFound = true;

                    ((JavascriptExecutor) driver)
                            .executeScript(
                                    "arguments[0].scrollIntoView(true);",
                                    row
                            );

                    ((JavascriptExecutor) driver)
                            .executeScript(
                                    "arguments[0].click();",
                                    row
                            );

                    System.out.println(
                            "Selected Purchase Record : "
                                    + purchaseNo
                    );

                    waitForOverlayToDisappear();

                    // ===== CLICK POST PURCHASE BUTTON =====
                    WebElement postBtn =
                            wait.until(ExpectedConditions
                                    .elementToBeClickable(
                                            postPurchaseButton
                                    ));

                    ((JavascriptExecutor) driver)
                            .executeScript(
                                    "arguments[0].scrollIntoView(true);",
                                    postBtn
                            );

                    ((JavascriptExecutor) driver)
                            .executeScript(
                                    "arguments[0].click();",
                                    postBtn
                            );

                    System.out.println(
                            "Clicked Post Purchase Button"
                    );

                    waitForOverlayToDisappear();

                    confirmPostPurchasePopup();

                    break;
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        if (!recordFound) {

            throw new RuntimeException(
                    "Purchase Record not found : "
                            + purchaseNo
            );
        }

    } catch (Exception e) {

        throw new RuntimeException(
                "Failed while selecting and posting purchase record.",
                e
        );
    }
}

    // ================= CONFIRM POPUP =================

    public void confirmPostPurchasePopup() {

        try {

            WebDriverWait wait =
                    new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement yesButton =
                    wait.until(ExpectedConditions.elementToBeClickable(
                            By.id("popup_ok")));

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);",
                            yesButton);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();",
                            yesButton);

            waitForOverlayToDisappear();

            System.out.println(
                    "Clicked YES button on Post Purchase confirmation popup.");

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to confirm Post Purchase popup.", e);
        }
    }

    // ================= HELPER =================

    private void waitForOverlayToDisappear() {

        try {

            WebDriverWait wait =
                    new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions
                    .invisibilityOfElementLocated(overlay));

        } catch (Exception e) {

            // ignore
        }
    }

    private void selectDropdownByNormalizedText(By dropdownLocator,
                                                String expectedText) {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement dropdown =
                wait.until(ExpectedConditions
                        .elementToBeClickable(dropdownLocator));

        wait.until(driver ->
                new Select(dropdown).getOptions().size() > 1);

        Select select = new Select(dropdown);

        String expected = normalizeText(expectedText);

        for (WebElement option : select.getOptions()) {

            if (normalizeText(option.getText())
                    .equalsIgnoreCase(expected)) {

                select.selectByVisibleText(option.getText());

                return;
            }
        }

        for (WebElement option : select.getOptions()) {

            String actual = normalizeText(option.getText());

            if (actual.toLowerCase().contains(expected.toLowerCase())
                    || expected.toLowerCase()
                    .contains(actual.toLowerCase())) {

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
                "Cannot locate dropdown option : "
                        + expectedText
                        + ". Available options : "
                        + availableOptions);
    }

    private void setDateValue(By locator, String value) {

        if (value == null || value.trim().isEmpty()) {
            return;
        }

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value=arguments[1]; "
                        + "arguments[0].dispatchEvent(new Event('change'));",
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
    
}