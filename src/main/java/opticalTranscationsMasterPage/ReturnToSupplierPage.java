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
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.BasePage;

public class ReturnToSupplierPage extends BasePage {

    public ReturnToSupplierPage(WebDriver driver) {
        super(driver);
    }

    // =========================================================
    // LOCATORS
    // =========================================================

    private By opticalTransactionsMenu =
            By.xpath("//span[normalize-space()='Optical Transactions']/ancestor::a");

    private By returnToSupplierMenu =
            By.xpath("//a[contains(@href,'ReturnItemToSupplier')]");

    private By searchButton =
            By.id("RITS_btnSearch");

    private By addNewReturnStockButton =
            By.id("RITS_btnAddNewReturnStock");

    private By grnNumberInput =
            By.id("RITS_txtmdlGRNDetails");

    private By goButton =
            By.id("RITS_btnmdlAddReturnStock");
    
 // Invoice No Input
    private By invoiceNumberInput =
            By.id("RITS_txtReturnInvoiceNo");

    // Return Remarks Textarea
    private By returnRemarksInput =
            By.id("RITS_txtGRNNotes");

    // Submit Button
    private By submitButton =
            By.id("RITS_btnSaveGRN");

    // =========================================================
    // COMMON WAIT METHODS
    // =========================================================

    protected WebElement waitForVisibility(By locator) {

        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {

        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(locator));
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
    // NAVIGATE TO RETURN TO SUPPLIER
    // =========================================================

    public void navigateToReturnToSupplier() {

        try {

            WebElement opticalMenu =
                    waitForVisibility(opticalTransactionsMenu);

            scrollToElement(opticalMenu);

            jsClick(opticalMenu);

            System.out.println(
                    "Clicked Optical Transactions menu"
            );

            WebElement returnMenu =
                    waitForVisibility(returnToSupplierMenu);

            scrollToElement(returnMenu);

            jsClick(returnMenu);

            System.out.println(
                    "Clicked Return To Supplier menu"
            );

            waitForVisibility(searchButton);

            System.out.println(
                    "Navigated to Return To Supplier page successfully."
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to navigate to Return To Supplier page.",
                    e
            );
        }
    }

    // =========================================================
    // PAGE OPEN CHECK
    // =========================================================

    public boolean isPageOpened() {

        try {

            return waitForVisibility(searchButton)
                    .isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    // =========================================================
    // CLICK ADD NEW RETURN STOCK
    // =========================================================

    public void clickAddNewReturnStock() {

        try {

            WebElement addButton =
                    waitForClickable(addNewReturnStockButton);

            scrollToElement(addButton);

            jsClick(addButton);

            System.out.println(
                    "Clicked Add New Return Stock button successfully."
            );

           
        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to click Add New Return Stock button.",
                    e
            );
        }
    }

    // =========================================================
    // SELECT SPECIFIC GRN AND CLICK GO
    // =========================================================

    public void selectGRNAndClickGo(String expectedGRN) {

        try {

            WebElement grnInput =
                    waitForVisibility(grnNumberInput);

            grnInput.clear();

            grnInput.sendKeys("g");

            System.out.println(
                    "Entered g in GRN field"
            );

           

            // ===== DYNAMIC GRN LOCATOR =====
            By specificGRNLocator = By.xpath(
                    "//td[contains(text(),'" + expectedGRN + "')]"
            );

            // ===== WAIT FOR GRN =====
            WebElement matchingGRN =
                    waitForVisibility(specificGRNLocator);

            String actualGRN =
                    matchingGRN.getText().trim();

            System.out.println(
                    "Expected GRN : " + expectedGRN
            );

            System.out.println(
                    "Actual GRN Found : " + actualGRN
            );

            // ===== VALIDATION =====
            if (!actualGRN.equalsIgnoreCase(expectedGRN)) {

                throw new RuntimeException(
                        "GRN mismatch. Expected : "
                                + expectedGRN
                                + " but found : "
                                + actualGRN
                );
            }

            scrollToElement(matchingGRN);

            // ===== HIGHLIGHT =====
            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].style.border='3px solid red'",
                            matchingGRN
                    );

         

            // ===== CLICK GRN =====
            jsClick(matchingGRN);

            System.out.println(
                    "Selected GRN successfully : " + actualGRN
            );

         

            // ===== CLICK GO =====
            WebElement goBtn =
                    waitForClickable(goButton);

            jsClick(goBtn);

            System.out.println("Clicked GO button");

          

        } catch (Exception e) {

            e.printStackTrace();

       
            throw new RuntimeException(
                    "Unable to select GRN and click GO button.",
                    e
            );
        }
    }

    // =========================================================
    // ENTER RETURN QTY FOR SPECIFIC ITEM
    // =========================================================

    public void enterReturnQty(String itemName, String qty) {

        try {

            JavascriptExecutor js =
                    (JavascriptExecutor) driver;

            // ===== FIND ALL ITEM ROWS =====
            List<WebElement> rows =
                    new WebDriverWait(driver, Duration.ofSeconds(30))
                            .until(
                                    ExpectedConditions
                                            .visibilityOfAllElementsLocatedBy(
                                                    By.xpath(
                                                            "//table[@id='RITS_tblModalGrid']//tbody/tr"
                                                    )
                                            )
                            );

            boolean itemFound = false;

            for (WebElement row : rows) {

                // ===== ITEM NAME =====
                String itemText = row.findElement(
                        By.xpath("./td[@name='fieldFourteen']")
                ).getText().trim();

                System.out.println(
                        "Available Item : " + itemText
                );

                // ===== MATCH ITEM =====
                if (itemText.toLowerCase()
                        .contains(itemName.toLowerCase())) {

                    itemFound = true;

                    System.out.println(
                            "Matched Item : " + itemText
                    );

                    // ===== RETURN QTY INPUT =====
                    WebElement qtyInput = row.findElement(
                            By.xpath(
                                    ".//input[contains(@id,'txt_fieldEight')]"
                            )
                    );

                    scrollToElement(qtyInput);

                    // ===== HIGHLIGHT =====
                    js.executeScript(
                            "arguments[0].style.border='3px solid red'",
                            qtyInput
                    );

                    // ===== OVERWRITE EXISTING VALUE =====
                    qtyInput.click();

                    qtyInput.sendKeys(
                            Keys.chord(Keys.CONTROL, "a"),
                            qty
                    );

                    System.out.println(
                            "Entered Return Qty : "
                                    + qty
                                    + " for Item : "
                                    + itemName
                    );

                    

                    break;
                }
            }

            // ===== ITEM NOT FOUND =====
            if (!itemFound) {

                throw new RuntimeException(
                        "Item NOT found in GRN table : "
                                + itemName
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

          

            throw new RuntimeException(
                    "Unable to enter Return Qty.",
                    e
            );
        }
    }
    
 // =========================================================
 // ENTER INVOICE NUMBER
 // =========================================================

 public void enterInvoiceNumber(String invoiceNo) {

     try {

         WebElement invoiceInput =
                 waitForVisibility(invoiceNumberInput);

         scrollToElement(invoiceInput);

         invoiceInput.click();

         invoiceInput.sendKeys(
                 Keys.chord(Keys.CONTROL, "a"),
                 invoiceNo
         );

         System.out.println(
                 "Entered Invoice Number : " + invoiceNo
         );

       

     } catch (Exception e) {

         e.printStackTrace();

       

         throw new RuntimeException(
                 "Unable to enter Invoice Number.",
                 e
         );
     }
 }
 
//=========================================================
//ENTER RETURN REMARKS
//=========================================================

public void enterReturnRemarks(String remarks) {

  try {

      WebElement remarksBox =
              waitForVisibility(returnRemarksInput);

      scrollToElement(remarksBox);

      remarksBox.click();

      remarksBox.sendKeys(
              Keys.chord(Keys.CONTROL, "a"),
              remarks
      );

      System.out.println(
              "Entered Return Remarks : " + remarks
      );

      

  } catch (Exception e) {

      e.printStackTrace();

      
      

      throw new RuntimeException(
              "Unable to enter Return Remarks.",
              e
      );
  }
}

//=========================================================
//CLICK SUBMIT BUTTON
//=========================================================

public void clickSubmitButton() {

 try {

     WebElement submitBtn =
             waitForClickable(submitButton);

     scrollToElement(submitBtn);

     jsClick(submitBtn);

     System.out.println(
             "Clicked Submit button successfully."
     );

    

 } catch (Exception e) {

     e.printStackTrace();


     throw new RuntimeException(
             "Unable to click Submit button.",
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

            File dest = new File(
                    "D:\\rohit\\VcmsOptical\\screenshots\\"
                            + fileName + ".png"
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