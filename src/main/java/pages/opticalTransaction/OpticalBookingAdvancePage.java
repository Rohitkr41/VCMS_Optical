
package pages.opticalTransaction;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.math.BigDecimal;
import java.util.List;


public class OpticalBookingAdvancePage extends OpticalBookingPage {

    public OpticalBookingAdvancePage(WebDriver driver) {
        super(driver);
    }

    private By itemSearchBox = By.id("OpticalBooking_txtItemBarcode");
    private By suggestionList = By.xpath("//table//tr");
    private String skuOptionXpath = "//td[contains(text(),'%s')]";

    private By gridRows = By.xpath("//table//tbody/tr");
    private By overlay = By.id("V3MOverlay");
    private By popupOverlay = By.id("popup_overlay");
    
 // ===== BOOKING / DELIVERY / ADVANCE =====
    private By expectedDeliveryDateInput = By.id("OpticalBooking_txtExpDeliveryDate");
    private By advanceAmountInput = By.id("OpticalBooking_txtAdvance");
    private By remarksInput = By.id("OpticalBooking_txtRemark");

    // ===== PAYMENT MODES =====
    private By cashCheckbox = By.id("ItemMaster_Cash");
    private By cashAmountInput = By.id("OpticalBooking_txtCashAmount");

    private By upiCheckbox = By.id("ItemMaster_UPI");
    private By upiAmountInput = By.id("OpticalBooking_txtUPIAmount");
    private By upiTransactionInput = By.id("OpticalBooking_txtUPITransactionNo");

    private By cardCheckbox = By.id("ItemMaster_CreditDebit");
    private By cardAmountInput = By.id("OpticalBooking_txtCreditDebitAmount");
    private By cardBankInput = By.id("OpticalBooking_txtCreditDebitBankName");
    private By cardTransactionInput = By.id("OpticalBooking_txtCreditDebitTransactionNo");

    private By neftCheckbox = By.id("ItemMaster_NEFT");
    private By neftAmountInput = By.id("OpticalBooking_txtNEFTAmount");
    private By neftBankInput = By.id("OpticalBooking_txtNEFTBankName");
    private By neftTransactionInput = By.id("OpticalBooking_txtNEFTransactionNo");

    // ===== FINAL DETAILS =====
    private By salesExecutiveDropdown = By.id("OpticalBooking_ddlSalesExecutive");
    private By gstNoInput = By.id("OpticalBooking_txtGSTNO");
    private By customerAddressInput = By.id("OpticalBooking_txtAddress");
    private By frameGivenByPatientCheckbox = By.id("OpticalBooking_chkFrameGivByPat");
    private By receiptRemarkInput = By.id("OpticalBooking_txtReceiptRemark");
    private By submitButton = By.id("OpticalBooking_btnSubmit");


    private By activeDiscountModal = By.xpath(
            "(//div[contains(@class,'modal') and " +
            "(contains(@style,'display: block') or contains(@class,'show')) " +
            "and .//button[normalize-space()='Apply Discount']])[last()]"
    );

    private void waitForOverlayToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
        } catch (Exception e) {
            // ignore
        }
    }

    private void waitForPopupOverlay() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupOverlay));
        } catch (Exception e) {
            // ignore
        }
    }

    public void selectItemTypeAdvance(String itemType) {
        selectItemType(itemType);
    }

    public void addItemAuto(String itemType, String searchText, String skuCode) {

        int beforeCount = driver.findElements(gridRows).size();

        selectItemTypeAdvance(itemType);
        searchAndSelectItem(searchText, skuCode);

        waitForOverlayToDisappear();

        wait.until(driver -> driver.findElements(gridRows).size() > beforeCount);

        System.out.println("Item added: " + skuCode);
    }

    public void searchAndSelectItem(String searchText, String skuCode) {

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(itemSearchBox));
        input.clear();
        input.sendKeys(searchText);

        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionList));
        waitForOverlayToDisappear();

        By skuLocator = By.xpath(String.format(skuOptionXpath, skuCode));
        WebElement skuElement = wait.until(ExpectedConditions.elementToBeClickable(skuLocator));
        skuElement.click();

        System.out.println("Selected SKU: " + skuCode);
    }

    public void addItemWithQuantity(String itemType, String searchText, String skuCode, int qty) {

        selectItemTypeAdvance(itemType);
        searchAndSelectItem(searchText, skuCode);
        waitForOverlayToDisappear();

        System.out.println("Item added first time: " + skuCode);

        for (int i = 1; i < qty; i++) {
            pressEnterToIncreaseQty();
            waitForOverlayToDisappear();
            System.out.println("Qty increased: " + (i + 1));
        }
    }

    public void pressEnterToIncreaseQty() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(itemSearchBox));
        input.sendKeys(Keys.ENTER);
    }

   public void applyDiscountOnItemByDetails(String itemDetails,
                                         String discountType,
                                         String discountValue,
                                         String narration) {

    waitForOverlayToDisappear();
    waitForPopupOverlay();

    By rowLocator = By.xpath(
            "//tr[td[@name='item_Details' and contains(normalize-space(.),\"" + itemDetails + "\")]]"
    );

    WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowLocator));

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", row
    );

    WebElement discountIcon = row.findElement(By.xpath(".//td[@name='OtherDiscount']//a"));

    try {
        new Actions(driver).moveToElement(discountIcon).pause(Duration.ofMillis(300)).click().perform();
    } catch (Exception e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", discountIcon);
    }

    WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(activeDiscountModal));
    waitForPopupOverlay();

    if (discountType != null && !discountType.trim().isEmpty()) {
        WebElement dropdown = modal.findElement(By.id("OD_ddlDiscountTypeName"));
        selectDiscountTypeFromModal(dropdown, discountType);
    }

    WebElement discountGiven = modal.findElement(By.id("OD_txtActualDiscount"));
    enterModalText(discountGiven, discountValue);

    WebElement narrationField = modal.findElement(By.id("OD_DiscountNaaration"));
    enterModalText(narrationField, narration);

    WebElement applyBtn = modal.findElement(By.xpath(".//button[normalize-space()='Apply Discount']"));

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", applyBtn
    );

    waitForPopupOverlay();

    try {
        new Actions(driver).moveToElement(applyBtn).pause(Duration.ofMillis(300)).click().perform();
    } catch (Exception e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", applyBtn);
    }

    boolean closed = waitUntilModalClosed(modal);
    

    if (!closed) {
        System.out.println("Apply Discount clicked but modal is still open.");
        System.out.println("Discount Type: " + getModalElementValue(modal, "OD_ddlDiscountTypeName"));
        System.out.println("Discount Given: " + getModalElementValue(modal, "OD_txtActualDiscount"));
        System.out.println("Narration: " + getModalElementValue(modal, "OD_DiscountNaaration"));

        throw new RuntimeException("Discount modal did not close after clicking Apply Discount.");
    }

    waitForOverlayToDisappear();
    waitForPopupOverlay();

    System.out.println("Discount applied on item: " + itemDetails + " = " + discountValue + "%");
}
   
   private boolean waitUntilModalClosed(WebElement modal) {

	    WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    try {
	        shortWait.until(driver -> {
	            try {
	                String style = modal.getAttribute("style");
	                String className = modal.getAttribute("class");

	                boolean hiddenByStyle = style == null || !style.contains("display: block");
	                boolean hiddenByClass = className == null || !className.contains("show");
	                boolean notDisplayed = !modal.isDisplayed();

	                return notDisplayed || hiddenByStyle || hiddenByClass;
	            } catch (Exception e) {
	                return true;
	            }
	        });

	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}



    private void selectDiscountTypeFromModal(WebElement dropdown, String discountType) {

        if (!dropdown.isEnabled()) {
            System.out.println("Discount Type dropdown disabled, skipped");
            return;
        }

        try {
            Select select = new Select(dropdown);
            select.selectByVisibleText(discountType);

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                    "if (window.jQuery) { $(arguments[0]).trigger('change'); }",
                    dropdown
            );

            System.out.println("Discount Type selected: " + discountType);

        } catch (Exception e) {
            String value = getOptionValueByText(dropdown, discountType);

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].value = arguments[1];" +
                    "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                    "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                    "if (window.jQuery) { $(arguments[0]).trigger('change'); }",
                    dropdown,
                    value
            );

            System.out.println("Discount Type selected via JS: " + discountType);
        }
    }

   private void enterModalText(WebElement element, String value) {

    if (value == null) {
        value = "";
    }

    if (!element.isEnabled()) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('disabled'); arguments[0].removeAttribute('readonly');",
                element
        );
    }

    try {
        element.click();
        element.sendKeys(Keys.CONTROL, "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);
        element.sendKeys(Keys.TAB);
    } catch (Exception e) {
        // JS fallback below
    }

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].value = arguments[1];" +
            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
            "arguments[0].dispatchEvent(new Event('keyup', { bubbles: true }));" +
            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
            "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));" +
            "arguments[0].dispatchEvent(new Event('focusout', { bubbles: true }));" +
            "if (window.jQuery) { $(arguments[0]).trigger('input').trigger('change').trigger('blur'); }",
            element,
            value
    );
}



    private String getOptionValueByText(WebElement dropdown, String text) {

        Select select = new Select(dropdown);

        return select.getOptions().stream()
                .filter(option -> option.getText().trim().equalsIgnoreCase(text.trim()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Discount option not found: " + text))
                .getAttribute("value");
    }

    private String getModalElementValue(WebElement modal, String id) {
        try {
            return modal.findElement(By.id(id)).getAttribute("value");
        } catch (Exception e) {
            return "NOT_FOUND";
        }
    }

    public void applyDiscountOnMultipleItems(String[][] discountData) {

        for (String[] data : discountData) {
            applyDiscountOnItemByDetails(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            );
        }
    }

    public void applyDiscountGivenOnMultipleItems(String[][] discountData) {

        for (String[] data : discountData) {
            applyDiscountOnItemByDetails(
                    data[0],
                    "",
                    data[1],
                    ""
            );
        }
    }
    
    public void enterExpectedDeliveryDate(String deliveryDate) {

        waitForOverlayToDisappear();

        WebElement dateField = wait.until(
                ExpectedConditions.presenceOfElementLocated(expectedDeliveryDateInput)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('readonly');" +
                "arguments[0].value = arguments[1];" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));" +
                "if (window.jQuery) { $(arguments[0]).trigger('change').trigger('blur'); }",
                dateField,
                deliveryDate
        );

        System.out.println("Expected Delivery Date entered: " + deliveryDate);
    }

   public void enterAdvanceAndRemarks(String advanceAmount, String remarks) {

    waitForOverlayToDisappear();

    enterText(advanceAmountInput, advanceAmount);
    waitUntilInputValueIs(advanceAmountInput, advanceAmount);

    enterText(remarksInput, remarks);

    waitForOverlayToDisappear();

    System.out.println("Advance Amount entered: " + advanceAmount);
}

    
    public void payByCash(String amount) {

        selectPaymentMode(cashCheckbox);
        enterText(cashAmountInput, amount);

        System.out.println("Cash payment entered: " + amount);
    }

    public void payByUPI(String amount, String transactionId) {

        selectPaymentMode(upiCheckbox);
        enterText(upiAmountInput, amount);
        enterText(upiTransactionInput, transactionId);

        System.out.println("UPI payment entered: " + amount);
    }

    public void payByCard(String amount, String bankName, String transactionId) {

        selectPaymentMode(cardCheckbox);
        enterText(cardAmountInput, amount);
        enterText(cardBankInput, bankName);
        enterText(cardTransactionInput, transactionId);

        System.out.println("Card payment entered: " + amount);
    }

    public void payByNEFT(String amount, String bankName, String transactionId) {

        selectPaymentMode(neftCheckbox);
        enterText(neftAmountInput, amount);
        enterText(neftBankInput, bankName);
        enterText(neftTransactionInput, transactionId);

        System.out.println("NEFT payment entered: " + amount);
    }
    
    public void selectSalesExecutive(String salesExecutiveName) {

        waitForOverlayToDisappear();

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(salesExecutiveDropdown)
        );

        Select select = new Select(dropdown);
        select.selectByVisibleText(salesExecutiveName);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                "if (window.jQuery) { $(arguments[0]).trigger('change'); }",
                dropdown
        );

        System.out.println("Sales Executive selected: " + salesExecutiveName);
    }

    public void enterCustomerGSTAndAddress(String gstNo, String address) {

        enterText(gstNoInput, gstNo);
        enterText(customerAddressInput, address);

        System.out.println("GST and Address entered");
    }

    public void enterReceiptRemark(boolean frameGivenByPatient, String receiptRemark) {

        if (frameGivenByPatient) {
            selectPaymentMode(frameGivenByPatientCheckbox);
        }

        enterText(receiptRemarkInput, receiptRemark);

        System.out.println("Receipt remark entered");
    }

    public void submitOpticalBooking() {

        waitForOverlayToDisappear();
        waitForPopupOverlay();

        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(submitButton));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", submit
        );

        try {
            new Actions(driver).moveToElement(submit).pause(Duration.ofMillis(300)).click().perform();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
        }

        waitForOverlayToDisappear();

        System.out.println("Optical Booking submitted successfully");
    }

   private void selectPaymentMode(By checkboxLocator) {

    WebElement checkbox = wait.until(
            ExpectedConditions.presenceOfElementLocated(checkboxLocator)
    );

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", checkbox
    );

    if (!checkbox.isSelected()) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
    }

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
            "if (window.jQuery) { $(arguments[0]).trigger('change'); }",
            checkbox
    );

    waitForOverlayToDisappear();
}

    
   
    private void waitUntilInputValueIs(By locator, String expectedValue) {

        wait.until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                String actualValue = element.getAttribute("value");

                if (actualValue == null) {
                    return false;
                }

                return normalizeAmount(actualValue).equals(normalizeAmount(expectedValue));
            } catch (Exception e) {
                return false;
            }
        });
    }

    private String normalizeAmount(String amount) {

        if (amount == null || amount.trim().isEmpty()) {
            return "";
        }

        return new BigDecimal(amount.replace(",", "").trim())
                .stripTrailingZeros()
                .toPlainString();
    }

    


    private void enterText(By locator, String value) {

        if (value == null) {
            value = "";
        }

        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element
        );

        if (!element.isEnabled()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].removeAttribute('disabled'); arguments[0].removeAttribute('readonly');",
                    element
            );
        }

        try {
            element.click();
            element.sendKeys(Keys.CONTROL, "a");
            element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys(value);
            element.sendKeys(Keys.TAB);
        } catch (Exception e) {
            // JS fallback below
        }

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];" +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('keyup', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));" +
                "if (window.jQuery) { $(arguments[0]).trigger('input').trigger('change').trigger('blur'); }",
                element,
                value
        );
    }
    
    public enum PaymentMode {
        CASH,
        UPI,
        CARD,
        NEFT
    }

    public static class PaymentDetails {

        private PaymentMode mode;
        private String amount;
        private String bankName;
        private String transactionId;

        private PaymentDetails(PaymentMode mode, String amount, String bankName, String transactionId) {
            this.mode = mode;
            this.amount = amount;
            this.bankName = bankName;
            this.transactionId = transactionId;
        }

        public static PaymentDetails cash(String amount) {
            return new PaymentDetails(PaymentMode.CASH, amount, "", "");
        }

        public static PaymentDetails upi(String amount, String transactionId) {
            return new PaymentDetails(PaymentMode.UPI, amount, "", transactionId);
        }

        public static PaymentDetails card(String amount, String bankName, String transactionId) {
            return new PaymentDetails(PaymentMode.CARD, amount, bankName, transactionId);
        }

        public static PaymentDetails neft(String amount, String bankName, String transactionId) {
            return new PaymentDetails(PaymentMode.NEFT, amount, bankName, transactionId);
            
        }
        
    }

    public void makeAdvancePayment(String advanceAmount,
                                   String remarks,
                                   List<PaymentDetails> payments) {

        validateAdvancePayments(advanceAmount, payments);

        enterAdvanceAndRemarks(advanceAmount, remarks);

        for (PaymentDetails payment : payments) {

            switch (payment.mode) {
                case CASH:
                    payByCash(payment.amount);
                    break;

                case UPI:
                    payByUPI(payment.amount, payment.transactionId);
                    break;

                case CARD:
                    payByCard(payment.amount, payment.bankName, payment.transactionId);
                    break;

                case NEFT:
                    payByNEFT(payment.amount, payment.bankName, payment.transactionId);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported payment mode: " + payment.mode);
            }
        }

        System.out.println("Advance payment completed. Advance: " + advanceAmount);
    }

    private void validateAdvancePayments(String advanceAmount, List<PaymentDetails> payments) {

        if (payments == null || payments.isEmpty()) {
            throw new IllegalArgumentException("At least one payment mode is required.");
        }

        BigDecimal advance = parseAmount(advanceAmount);
        BigDecimal totalPayment = BigDecimal.ZERO;

        for (PaymentDetails payment : payments) {

            if (payment.mode == null) {
                throw new IllegalArgumentException("Payment mode cannot be null.");
            }

            BigDecimal amount = parseAmount(payment.amount);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Payment amount must be greater than zero: " + payment.amount);
            }

            if (requiresTransactionId(payment.mode)
                    && (payment.transactionId == null || payment.transactionId.trim().isEmpty())) {
                throw new IllegalArgumentException(payment.mode + " transaction id is required.");
            }

            if ((payment.mode == PaymentMode.CARD || payment.mode == PaymentMode.NEFT)
                    && (payment.bankName == null || payment.bankName.trim().isEmpty())) {
                throw new IllegalArgumentException(payment.mode + " bank name is required.");
            }

            totalPayment = totalPayment.add(amount);
        }

        if (totalPayment.compareTo(advance) != 0) {
            throw new IllegalArgumentException(
                    "Total payment must be equal to advance amount. Advance: "
                            + advance + ", Payment Total: " + totalPayment
            );
        }
    }

    private boolean requiresTransactionId(PaymentMode mode) {
        return mode == PaymentMode.UPI || mode == PaymentMode.CARD || mode == PaymentMode.NEFT;
    }

    private BigDecimal parseAmount(String amount) {

        if (amount == null || amount.trim().isEmpty()) {
            throw new IllegalArgumentException("Amount cannot be blank.");
        }

        return new BigDecimal(amount.replace(",", "").trim());
      
    }
    

    
    
}
