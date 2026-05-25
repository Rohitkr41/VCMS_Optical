package opticalTranscationsMasterPage;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.BasePage;

public class IssueItemsPage extends BasePage {

    public IssueItemsPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // LOCATORS
    // =========================================================

    private By opticalTransactionsMenu =
            By.xpath("//span[normalize-space()='Optical Transactions']/ancestor::a");

    private By issueItemsMenu =
            By.xpath("//a[contains(normalize-space(),'Issue Items')]");

    private By addNewButton =
            By.id("StockIssue_btnAddNewIssueItem");

    // ITEM CATEGORY
    private By itemCategoryDropdown =
            By.id("StockIssue_ddlMdlItemCategory");

    // SEARCH BUTTON
    private By searchButton =
            By.id("StockIssue_btnSearchIssuedItem");


    // VISION CENTER
    private By visionCenterDropdown =
            By.id("StockIssue_ddlMdlVisionCenter");

    // NOTES
    private By notesInput =
            By.id("StockIssue_txtNote");

    // ISSUE BUTTON
    private By issueItemsButton =
            By.id("StockIssue_btnIssueStock");

    // =========================================================
    // WAIT METHODS
    // =========================================================

    protected WebElement waitForVisibility(By locator) {

        return new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(
                        ExpectedConditions
                                .visibilityOfElementLocated(locator)
                );
    }

    protected WebElement waitForClickable(By locator) {

        return new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(
                        ExpectedConditions
                                .elementToBeClickable(locator)
                );
    }

    protected void scrollToElement(WebElement element) {

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        element
                );
    }

    private void jsClick(WebElement element) {

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        element
                );
    }

    // =========================================================
    // NAVIGATE TO ISSUE ITEMS
    // =========================================================

    public void navigateToIssueItems() {

        try {

            WebElement opticalMenu =
                    waitForVisibility(opticalTransactionsMenu);

            scrollToElement(opticalMenu);

            jsClick(opticalMenu);

            System.out.println(
                    "Clicked Optical Transactions menu"
            );

            WebElement issueMenu =
                    waitForVisibility(issueItemsMenu);

            scrollToElement(issueMenu);

            jsClick(issueMenu);

            System.out.println(
                    "Clicked Issue Items menu"
            );

            waitForVisibility(addNewButton);

            System.out.println(
                    "Issue Items page opened successfully"
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to open Issue Items page",
                    e
            );
        }
    }

    // =========================================================
    // PAGE OPEN VALIDATION
    // =========================================================

    public boolean isPageOpened() {

        try {

            return waitForVisibility(addNewButton)
                    .isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    // =========================================================
    // CLICK ADD NEW
    // =========================================================

    public void clickAddNew() {

        try {

            WebElement addBtn =
                    waitForClickable(addNewButton);

            scrollToElement(addBtn);

            jsClick(addBtn);

            System.out.println(
                    "Clicked Add New button"
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to click Add New button",
                    e
            );
        }
    }

    // =========================================================
    // SELECT ITEM CATEGORY
    // =========================================================

    public void selectItemCategory(String categoryName) {

        try {

            WebElement dropdown =
                    waitForVisibility(itemCategoryDropdown);

            scrollToElement(dropdown);

            Select select =
                    new Select(dropdown);

            select.selectByVisibleText(categoryName);

            System.out.println(
                    "Selected Item Category : "
                            + categoryName
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to select item category",
                    e
            );
        }
    }

    // =========================================================
    // CLICK SEARCH BUTTON
    // =========================================================

    public void clickSearchButton() {

        try {

            WebElement searchBtn =
                    waitForClickable(searchButton);

            scrollToElement(searchBtn);

            jsClick(searchBtn);

            captureScreenshot("SearchButtonClicked");

            System.out.println(
                    "Clicked Search button"
            );

            Thread.sleep(4000);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to click Search button",
                    e
            );
        }
    }

    // =========================================================
    // ENTER ISSUE QTY + CLICK CHECKBOX
    // =========================================================

 public void enterIssueQty(String itemCode, String qty) {

    try {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // FAST WAIT ONLY FOR TABLE
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table[@id='StockIssue_tblMdlRecord']/tbody/tr")
        ));

        List<WebElement> rows = driver.findElements(
                By.xpath("//table[@id='StockIssue_tblMdlRecord']/tbody/tr")
        );

        System.out.println("Total Rows Found : " + rows.size());

        boolean itemFound = false;

        for (WebElement row : rows) {

            try {

                // DIRECT FETCH ONLY REQUIRED TDs (FAST)
                String currentItemCode = row.findElement(
                        By.xpath("./td[9]")
                ).getText().trim();

                if (!currentItemCode.equalsIgnoreCase(itemCode)) {
                    continue;
                }

                itemFound = true;

                System.out.println("Item Found : " + currentItemCode);

                // CHECKBOX (FAST DIRECT)
                WebElement checkbox = row.findElement(
                        By.xpath("./td[6]//input[@type='checkbox']")
                );
                // ISSUE QTY INPUT (FAST DIRECT)
                WebElement qtyBox = row.findElement(
                        By.xpath("./td[21]//input")
                );

                qtyBox.click();
                qtyBox.sendKeys(Keys.CONTROL + "a");
                qtyBox.sendKeys(qty);

                System.out.println("Qty Entered : " + qty);

                if (!checkbox.isSelected()) {
                    jsClick(checkbox);
                }

               

                captureScreenshot("enterIssueQty");

                break;

            } catch (Exception e) {
                // ignore row
            }
        }

        if (!itemFound) {
            captureScreenshot("enterIssueQty");
            throw new RuntimeException("Item NOT found : " + itemCode);
        }

    } catch (Exception e) {
        captureScreenshot("enterIssueQty");
        throw new RuntimeException("Unable to enter issue qty", e);
    }
}

    // =========================================================
    // SELECT VC RADIO
    // =========================================================

    public void selectToLocationVC() {

        try {

            List<WebElement> radios =
                    driver.findElements(
                            By.xpath("//input[@type='radio']")
                    );

            WebElement vcRadio =
                    radios.get(0);

            scrollToElement(vcRadio);

            jsClick(vcRadio);

            System.out.println(
                    "Selected To Location VC radio"
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to select VC radio",
                    e
            );
        }
    }
    
 // =========================================================
    // SELECT Regional RADIO
    // =========================================================

    public void selectToLocationRegional() {

        try {

            List<WebElement> radios =
                    driver.findElements(
                            By.id("StockIssue_rdbSelf")
                    );

            WebElement vcRadio =
                    radios.get(0);

            scrollToElement(vcRadio);

            jsClick(vcRadio);

            System.out.println(
                    "Selected To Location VC radio"
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to select VC radio",
                    e
            );
        }
    }

    // =========================================================
    // SELECT VISION CENTER
    // =========================================================

    public void selectVisionCenter(String vcName) {

        try {

            WebElement dropdown =
                    waitForVisibility(visionCenterDropdown);

            scrollToElement(dropdown);

            Select select =
                    new Select(dropdown);

            select.selectByVisibleText(vcName);

            System.out.println(
                    "Selected Vision Center : "
                            + vcName
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to select Vision Center",
                    e
            );
        }
    }

    // =========================================================
    // ENTER NOTES
    // =========================================================

    public void enterNotes(String notes) {

        try {

            WebElement noteBox =
                    waitForVisibility(notesInput);

            scrollToElement(noteBox);

            noteBox.click();

            noteBox.sendKeys(
                    Keys.chord(Keys.CONTROL, "a"),
                    notes
            );

            System.out.println(
                    "Entered Notes : "
                            + notes
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to enter notes",
                    e
            );
        }
    }

    // =========================================================
    // CLICK ISSUE ITEMS BUTTON
    // =========================================================

    public void clickIssueItemsButton() {

        try {

            WebElement issueBtn =
                    waitForClickable(issueItemsButton);

            scrollToElement(issueBtn);

            jsClick(issueBtn);

            System.out.println(
                    "Clicked Issue Items button"
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to click Issue Items button",
                    e
            );
        }
    }

    // =========================================================
    // SCREENSHOT METHOD
    // =========================================================

    public void captureScreenshot(String fileName) {

        try {

            File src =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            File dest =
                    new File(
                            "D:\\rohit\\VcmsOptical\\screenshots\\"
                                    + fileName
                                    + ".png"
                    );

            FileUtils.copyFile(src, dest);

            System.out.println(
                    "Screenshot captured : "
                            + dest.getAbsolutePath()
            );

        } catch (Exception e) {

            System.out.println(
                    "Failed to capture screenshot : "
                            + e.getMessage()
            );
        }
    }
}