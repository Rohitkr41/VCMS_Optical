package opticalTranscationsMasterPage;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.BasePage;

public class PostReturnToSupplierPage extends BasePage {

    private WebDriverWait wait;

    public PostReturnToSupplierPage(WebDriver driver) {

        super(driver);

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    // ================= LOCATORS =================

    private By opticalTransactionsMenu =
            By.xpath("//span[normalize-space()='Optical Transactions']/ancestor::a");

    private By returnToSupplierMenu =
            By.xpath("//a[contains(@href,'ReturnItemToSupplier')]");

    private By searchButton =
            By.id("RITS_btnSearch");

    private By tableRows =
            By.xpath("//table[@id='RITS_tblRecord']//tbody/tr");

    private By postReturnButton =
            By.id("RITS_btnReturnStock");

    private By popupYesButton =
            By.id("popup_ok");

    private By overlay =
            By.id("V3MOverlay");

    // ================= NAVIGATION =================

    public void navigateToReturnToSupplierPage() {

        try {

            clickUsingJS(opticalTransactionsMenu);

            clickUsingJS(returnToSupplierMenu);

            waitForVisibility(searchButton);

            captureScreenshot(
                    "PostReturnToSupplierPage"
            );

            System.out.println(
                    "Navigated to Return To Supplier Page Successfully"
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to navigate to Return To Supplier Page.",
                    e
            );
        }
    }

    // ================= PAGE OPEN CHECK =================

    public boolean isPageOpened() {

        try {

            waitForVisibility(searchButton);

            return driver.findElement(searchButton)
                    .isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    // ================= SEARCH RECORDS =================

    public void searchReturnRecords() {

        try {

            waitForOverlayToDisappear();

            clickUsingJS(searchButton);

            waitForOverlayToDisappear();

            waitForTableRows(tableRows);

            System.out.println(
                    "Clicked Search Button"
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to search return records.",
                    e
            );
        }
    }

    // ================= SELECT RECORD & POST =================

    public void selectReturnRecordAndPost(String returnNo) {

        try {

            waitForOverlayToDisappear();

            searchReturnRecords();

            List<WebElement> rows =
                    driver.findElements(tableRows);

            System.out.println(
                    "Total Rows Found : " + rows.size()
            );

            if (rows.isEmpty()) {

                throw new RuntimeException(
                        "No Return records found in table."
                );
            }

            boolean recordFound = false;

            for (WebElement row : rows) {

                String rowText =
                        normalizeText(row.getText());

                System.out.println(
                        "Row Text : " + rowText
                );

                if (rowText.contains(
                        normalizeText(returnNo)
                )) {

                    recordFound = true;

                    scrollIntoView(row);

                    clickElement(row);

                    System.out.println(
                            "Selected Return Record : "
                                    + returnNo
                    );

                    waitForOverlayToDisappear();

                    clickUsingJS(postReturnButton);

                    System.out.println(
                            "Clicked Post Return Button"
                    );

                    waitForOverlayToDisappear();

                    confirmReturnPostPopup();

                    break;
                }
            }

            if (!recordFound) {

                throw new RuntimeException(
                        "Return Record not found : "
                                + returnNo
                );
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed while selecting and posting return record.",
                    e
            );
        }
    }

    // ================= CONFIRM POPUP =================

    public void confirmReturnPostPopup() {

        try {

            clickUsingJS(popupYesButton);

            waitForOverlayToDisappear();

            System.out.println(
                    "Clicked YES button on confirmation popup."
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to confirm popup.",
                    e
            );
        }
    }

    // ================= COMMON WAIT METHODS =================

    protected WebElement waitForVisibility(By locator) {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        locator
                )
        );
    }

    protected WebElement waitForClickable(By locator) {

        return wait.until(
                ExpectedConditions.elementToBeClickable(
                        locator
                )
        );
    }

    private void waitForTableRows(By locator) {

        wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(
                        locator,
                        0
                )
        );
    }

    private void waitForOverlayToDisappear() {

        try {

            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            overlay
                    )
            );

        } catch (Exception e) {

            // ignore
        }
    }

    // ================= COMMON ACTION METHODS =================

    private void scrollIntoView(WebElement element) {

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        element
                );
    }

    private void clickElement(WebElement element) {

        try {

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            element
                    )
            );

            element.click();

        } catch (Exception e) {

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            element
                    );
        }
    }

    private void clickUsingJS(By locator) {

        WebElement element =
                waitForClickable(locator);

        scrollIntoView(element);

        clickElement(element);
    }

    // ================= SCREENSHOT =================

    private void captureScreenshot(String fileName) {

        try {

            File src =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(
                    src,
                    new File(
                            "D:\\rohit\\VcmsOptical\\screenshots\\"
                                    + fileName
                                    + ".png"
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // ================= NORMALIZE TEXT =================

    private String normalizeText(String text) {

        if (text == null) {

            return "";
        }

        return text.replace('\u00A0', ' ')
                .replaceAll("\\s+", " ")
                .trim();
    }
}