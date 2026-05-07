package opticalTranscationsMasterPage;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.BasePage;

public class PurchasePage extends BasePage {

    public PurchasePage(WebDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====
    private By opticalTransactionsMenu = By.xpath("//span[normalize-space()='Optical Transactions']/ancestor::a");
    
    private By purchaseMenu = By.cssSelector("a[href='/VCMS_Optical/OpticalRequest/ViewGoodsReceiptNotes']");
    
    private By overlay = By.id("V3MOverlay");

    // 👉 Change this if you know exact element on Purchase page
   
    
    private By regionalDropdown = By.id("VGRN_ddlSearchEyeHospital");
    private By addNewGRNButton = By.id("VGRN_btnAddNewGRN");
    
    //Enter Regioanl and po number
    private By modalRegionalDropdown = By.id("VGRN_ddlmdlSearchEyeHospital");
    

    // Auto-suggestion dropdown list (common pattern for autocomplete)
    private By poSuggestionRows = By.xpath("//table[@id='VGRN_tblSearchPOData']//tr");
    
   
    private By grnRows = By.xpath("//tbody[@class='bodyTable']//tr");
    
    private By invoiceNoInput = By.id("VGRN_txtInvoiceNumber");      // update if different
    private By invoiceAmountInput = By.id("VGRN_txtInvoiceAmount"); // update if different
    private By notesInput = By.id("VGRN_txtGRNNotes");              // update if different
    private By saveGRNButton = By.id("VGRN_btnSaveGRN");         // update if different

    // ===== NAVIGATION =====
    public void navigateToPurchase() {
        try {
            WebElement menu = driver.findElement(opticalTransactionsMenu);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", menu);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);

            WebElement purchase = driver.findElement(purchaseMenu);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", purchase);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", purchase);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // Wait for page load
            wait.until(ExpectedConditions.urlContains("ViewGoodsReceiptNotes"));

            waitForOverlayToDisappear();

            // Screenshot
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File("D:\\rohit\\VcmsOptical\\screenshots\\purchasePage.png"));

            System.out.println("Navigated to Purchase page successfully.");

        } catch (Exception e) {
            throw new RuntimeException("Unable to navigate to Purchase page.", e);
        }
    }

    // ===== PAGE VALIDATION =====
    public boolean isPageOpened() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("ViewGoodsReceiptNotes"));
            return driver.getCurrentUrl().contains("ViewGoodsReceiptNotes");
        } catch (Exception e) {
            return false;
        }
    }

    // ===== HELPER =====
    private void waitForOverlayToDisappear() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (Exception e) {
            // ignore
        }
    }
    
    public void selectRegional(String regionalName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement dropdown = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(regionalDropdown)
            );

            Select select = new Select(dropdown);
            select.selectByVisibleText(regionalName);

            System.out.println("Selected Regional: " + regionalName);

        } catch (Exception e) {
            throw new RuntimeException("Failed to select regional.", e);
        }
    }
    
    public void clickAddNewGRN() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement addBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(addNewGRNButton)
            );

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);

            waitForOverlayToDisappear();

            System.out.println("Clicked on Add New GRN button.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to click Add New GRN button.", e);
        }
    }
    
    public void selectModalRegional(String regionalName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement dropdown = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(modalRegionalDropdown)
            );

            Select select = new Select(dropdown);
            select.selectByVisibleText(regionalName);

            System.out.println("Modal Regional selected: " + regionalName);

        } catch (Exception e) {
            throw new RuntimeException("Failed to select modal regional.", e);
        }
    }
    
    public void enterAndSelectPONumber(String poNumber) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement poInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("VGRN_txtPODetails"))
        );

        poInput.clear();
        poInput.sendKeys(poNumber);

        // Wait for table to appear (NOT ul/li)
        List<WebElement> rows = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(poSuggestionRows)
        );

        boolean found = false;

        for (WebElement row : rows) {

            // PO number is in 5th column (td[5])
            WebElement poCell = row.findElement(By.xpath("./td[5]"));
            String text = poCell.getText().trim();

            System.out.println("Found PO: " + text);

            if (text.equalsIgnoreCase(poNumber)) {

                // Scroll
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", row);

                // Click row (NOT cell)
                try {
                    row.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", row);
                }

                System.out.println("Selected PO: " + text);
                found = true;
                break;
            }
        }

        // 🔥 Fallback (keyboard)
        if (!found) {
            System.out.println("Fallback: Keyboard select");
            poInput.sendKeys(Keys.ARROW_DOWN);
            poInput.sendKeys(Keys.ENTER);
        }

    } catch (Exception e) {
        throw new RuntimeException("Failed to select PO from table dropdown.", e);
    }
}
    
    public void clickGoButton() {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement goBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("VGRN_btnmdlAddGRN"))
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goBtn);

        waitForOverlayToDisappear();

        System.out.println("GO button clicked");

    } catch (Exception e) {
        throw new RuntimeException("GO button click failed", e);
    }
}
    
    // ENter REC FREE QTY. and REC QTY Value
    public void enterGRNQuantities(String freeQty, String recQty) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            List<WebElement> rows = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(grnRows)
            );

            if (rows.isEmpty()) {
                System.out.println("No items found in GRN table.");
                return;
            }

            for (int i = 0; i < rows.size(); i++) {

                WebElement row = rows.get(i);

                // REC. FREE QTY input
                WebElement freeQtyInput = row.findElement(By.xpath(".//td[@name='fieldTen']//input"));

                // REC. QTY input
                WebElement recQtyInput = row.findElement(By.xpath(".//td[@name='onlineStatus']//input"));

                // Scroll to row
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", row);

                // Clear and enter values
                freeQtyInput.clear();
                freeQtyInput.sendKeys(freeQty);

                recQtyInput.clear();
                recQtyInput.sendKeys(recQty);

                System.out.println("Row " + (i + 1) + " updated: FreeQty=" + freeQty + ", RecQty=" + recQty);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to enter GRN quantities.", e);
        }
    }
    
   
   public void enterGRNQtyBySKU(Map<String, GRNItemData> skuData) {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    List<WebElement> rows = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//tbody[@class='bodyTable']//tr"))
    );

    for (WebElement row : rows) {

        try {
            String sku = row.findElement(
                    By.xpath(".//td[@name='fieldThirtyFour']")
            ).getText().trim();

            if (!skuData.containsKey(sku)) {
                continue;
            }

            GRNItemData data = skuData.get(sku);

            WebElement freeQtyInput = row.findElement(
                    By.xpath(".//td[@name='fieldTen']//input")
            );

            WebElement recQtyInput = row.findElement(
                    By.xpath(".//td[@name='onlineStatus']//input")
            );

            // 🔥 bring row into focus
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", row);

            row.click();

            // ===== STEP 1: CLEAR + ENTER FREE QTY =====
            clearAndType(freeQtyInput, data.getFreeQty());

            // ===== STEP 2: CLEAR + ENTER REC QTY =====
            clearAndType(recQtyInput, data.getRecQty());

            System.out.println("Updated SKU: " + sku +
                    " | FreeQty=" + data.getFreeQty() +
                    " | RecQty=" + data.getRecQty());

        } catch (Exception e) {
            System.out.println("Error processing row: " + e.getMessage());
        }
    }
}
    

   
   protected void clearAndType(WebElement element, String value) {

	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        wait.until(ExpectedConditions.elementToBeClickable(element));

	        element.click();

	        // 🔥 STEP 1: CLEAR properly (important for grid inputs)
	        element.sendKeys(Keys.CONTROL + "a");
	        element.sendKeys(Keys.DELETE);

	        Thread.sleep(200); // UI settle

	        // 🔥 STEP 2: ENTER NEW VALUE
	        element.sendKeys(value);

	        // 🔥 trigger UI event
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].dispatchEvent(new Event('input'));",
	                element
	        );

	        Thread.sleep(300); // allow recalculation

	    } catch (Exception e) {

	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].value='';" +
	                "arguments[0].value='" + value + "';" +
	                "arguments[0].dispatchEvent(new Event('input'));",
	                element
	        );
	    }
	}
  
   
	   public void fillInvoiceAndSaveGRN(String invoiceNo,
	           String invoiceAmount,
	           String notes) {
	
	try {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	// ===== INVOICE NO =====
	WebElement invoiceNoEl = wait.until(
	ExpectedConditions.visibilityOfElementLocated(invoiceNoInput)
	);
	clearAndType(invoiceNoEl, invoiceNo);
	
	// ===== INVOICE AMOUNT =====
	WebElement amountEl = wait.until(
	ExpectedConditions.visibilityOfElementLocated(invoiceAmountInput)
	);
	clearAndType(amountEl, invoiceAmount);
	
	// ===== NOTES =====
	WebElement notesEl = wait.until(
	ExpectedConditions.visibilityOfElementLocated(notesInput)
	);
	clearAndType(notesEl, notes);
	
	Thread.sleep(300); // UI settle before save
	
	// ===== SAVE GRN =====
	WebElement saveBtn = wait.until(
	ExpectedConditions.elementToBeClickable(saveGRNButton)
	);
	
	((JavascriptExecutor) driver)
	.executeScript("arguments[0].scrollIntoView(true);", saveBtn);
	
	((JavascriptExecutor) driver)
	.executeScript("arguments[0].click();", saveBtn);
	
	System.out.println("GRN Saved Successfully");
	
	} catch (Exception e) {
	throw new RuntimeException("Failed to save GRN", e);
	}
	}
    
   
}