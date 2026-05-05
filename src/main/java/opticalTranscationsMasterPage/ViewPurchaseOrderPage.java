package opticalTranscationsMasterPage;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;



import pages.BasePage;

public class ViewPurchaseOrderPage extends BasePage {

    public ViewPurchaseOrderPage(WebDriver driver) {
        super(driver);
    }

    // ===== MENU LOCATORS =====
    private By opticalTransactionsMenu = By.xpath(
            "//span[normalize-space()='Optical Transactions']/ancestor::a"
    );

    private By opticalTransactionsCollapse = By.id("OpticalTransacations");

    private By viewPurchaseOrderMenu = By.cssSelector(
            "a[href='/VCMS_Optical/OpticalRequest/ViewPurchaseOrder']"
    );

    private By addNewButton = By.id("VPO_btnAddNew");

    // ===== ADD NEW PURCHASE ORDER LOCATORS =====
    private By directPORadio = By.id("VPO_rdbmdlDirect");
    private By challanBasedPORadio = By.id("VPO_rdbmdlChallanBased");

    private By locationDropdown = By.id("VPO_ddlmdlEyeHospital");
    private By supplierNameInput = By.id("VPO_txtItemSupplier");
    private By itemTypeDropdown = By.id("VPO_ddlItemCategory");
    private By itemNameInput = By.id("VPO_txtItemName");

    private By localPurchaseRadio = By.id("PurchaseOrder_rdbLocalSale");
    private By interStatePurchaseRadio = By.id("PurchaseOrder_rdbInterStateSale");

    private By quantityInput = By.id("VPO_txtQuanity");
    private By freeItemCheckbox = By.id("VPO_chkFreeItem");
    private By freeItemQtyInput = By.id("VPO_txtFreeItem");
    private By discountInput = By.id("VPO_txtDiscount");
   

    private By addPOItemsButton = By.id("VPO_btnAddIndentListInGrid");
    private By poItemsGridRows = By.xpath("//table[@id='VPO_mdltblRecord']//tr");

    private By overlay = By.id("V3MOverlay");
    private By popupOverlay = By.id("popup_overlay");

    private By visibleAutocompleteOptions = By.xpath(
            "//ul[contains(@class,'ui-autocomplete') and not(contains(@style,'display: none'))]//li | " +
            "//div[contains(@class,'ui-autocomplete') and not(contains(@style,'display: none'))]//li | " +
            "//table[contains(@class,'ui-autocomplete') or contains(@id,'ui-id')]//tr | " +
            "//tr[td]"
    );
    
    private By itemTotalGSTText = By.id("PurchaseOrder_spnItemTotalGST");
    private By purchaseRateInput = By.id("VPO_txtPruchaseRate");
    private By mrpInput = By.id("VPO_txtMRP");
    
    private By purchaseTypePopup = By.id("popup_container");
    private By popupNoButton = By.id("popup_cancel");
    private By popupYesButton = By.id("popup_ok");
    
    //submitStep
 // ===== SUBMIT PO LOCATORS =====
    private By submitPOTab = By.xpath("//a[contains(text(),'Submit Purchase Order')]");

    private By finalRemarksTextArea = By.id("VPO_txtSubmitPoTextAreaRemark");

    private By freightGSTCheckbox = By.id("VPO_chkFreightChargesWithGST");

    private By freightInput = By.id("VPO_txtFreight");
    private By freightRemarksInput = By.id("VPO_txtFreightRemarks");

    private By finalSubmitButton = By.id("VPO_btnFinalPOSubmit");

    
    
    // ===== DATA CLASS FOR MULTIPLE ITEMS =====
    public static class POItemDetails {

        public String itemType;
        public String itemSearchText;
        public String itemNameToSelect;
        public String quantity;
        public boolean isFreeItem;
        public String freeQty;
        public String discount;

        public POItemDetails(
                String itemType,
                String itemSearchText,
                String itemNameToSelect,
                String quantity,
                boolean isFreeItem,
                String freeQty,
                String discount
        ) {
            this.itemType = itemType;
            this.itemSearchText = itemSearchText;
            this.itemNameToSelect = itemNameToSelect;
            this.quantity = quantity;
            this.isFreeItem = isFreeItem;
            this.freeQty = freeQty;
            this.discount = discount;
        }
    }

    // ===== MENU ACTION METHODS =====
    public void openOpticalTransactionsMenu() {
        WebElement menu = wait.until(
                ExpectedConditions.presenceOfElementLocated(opticalTransactionsMenu)
        );

        WebElement collapse = wait.until(
                ExpectedConditions.presenceOfElementLocated(opticalTransactionsCollapse)
        );

        String classValue = collapse.getAttribute("class");

        if (!classValue.contains("show")) {
            scrollToElement(menu);
            jsClick(menu);

            wait.until(ExpectedConditions.attributeContains(
                    opticalTransactionsCollapse,
                    "class",
                    "show"
            ));
        }

        System.out.println("Optical Transactions menu opened.");
    }

    public void clickViewPurchaseOrder() {
        openOpticalTransactionsMenu();

        WebElement viewPurchaseOrder = wait.until(
                ExpectedConditions.presenceOfElementLocated(viewPurchaseOrderMenu)
        );

        scrollToElement(viewPurchaseOrder);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(viewPurchaseOrder));
            viewPurchaseOrder.click();
        } catch (Exception e) {
            jsClick(viewPurchaseOrder);
        }

        System.out.println("Clicked on View Purchase Order.");
    }
    
    
    private void handlePurchaseTypePopupAndClickNo() {
    try {
        List<WebElement> popups = driver.findElements(purchaseTypePopup);

        if (!popups.isEmpty() && popups.get(0).isDisplayed()) {

            WebElement noBtn = driver.findElement(popupNoButton);

            try {
                noBtn.click();
            } catch (Exception e) {
                jsClick(noBtn);
            }

            System.out.println("Popup handled fast → NO clicked");
        }

    } catch (Exception e) {
        // ignore
    }
}

    public boolean isViewPurchaseOrderPageOpened() {
        try {
            wait.until(ExpectedConditions.urlContains(
                    "/VCMS_Optical/OpticalRequest/ViewPurchaseOrder"
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddNew() {
        WebElement addNew = wait.until(
                ExpectedConditions.elementToBeClickable(addNewButton)
        );

        scrollToElement(addNew);

        try {
            addNew.click();
        } catch (Exception e) {
            jsClick(addNew);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(locationDropdown));
        System.out.println("Clicked on Add New button.");
    }

    // ===== ADD NEW PO METHODS =====
    public void selectPODirect() {
        selectRadioButton(directPORadio);
        System.out.println("PO Type selected: Direct");
    }

    public void selectPOChallanBased() {
        selectRadioButton(challanBasedPORadio);
        System.out.println("PO Type selected: Challan Based");
    }

    public void selectLocation(String locationName) {
        selectDropdownByVisibleText(locationDropdown, locationName);
        System.out.println("Location selected: " + locationName);
    }

    public void selectSupplierName(String searchText, String supplierNameToSelect) {
        searchAndSelectAutocompleteOption(supplierNameInput, searchText, supplierNameToSelect);
        System.out.println("Supplier selected: " + supplierNameToSelect);
    }

    public void selectItemType(String itemType) {
        selectDropdownByVisibleText(itemTypeDropdown, itemType);
        System.out.println("Item Type selected: " + itemType);
    }

    public void selectItemName(String searchText, String itemNameToSelect) {
        searchAndSelectAutocompleteOption(itemNameInput, searchText, itemNameToSelect);
        System.out.println("Item Name selected: " + itemNameToSelect);
    }

public void selectPurchaseTypeLocal() {
    setPurchaseType(localPurchaseRadio, interStatePurchaseRadio, "Local");
    System.out.println("Purchase Type selected: Local");
}

public void selectPurchaseTypeInterState() {
    setPurchaseType(interStatePurchaseRadio, localPurchaseRadio, "Inter-State");
    System.out.println("Purchase Type selected: Inter-State");
}

private void selectPurchaseTypeByName(String purchaseType) {
    if (purchaseType.equalsIgnoreCase("Local")) {
        selectPurchaseTypeLocal();
    } else if (purchaseType.equalsIgnoreCase("Inter-State")
            || purchaseType.equalsIgnoreCase("InterState")) {
        selectPurchaseTypeInterState();
    } else {
        throw new IllegalArgumentException("Invalid Purchase Type: " + purchaseType);
    }
}



	private void setPurchaseType(By selectedRadioLocator, By otherRadioLocator, String purchaseTypeName) {
    WebElement selectedRadio = wait.until(
            ExpectedConditions.presenceOfElementLocated(selectedRadioLocator)
    );

    WebElement otherRadio = wait.until(
            ExpectedConditions.presenceOfElementLocated(otherRadioLocator)
    );

    scrollToElement(selectedRadio);

    ((JavascriptExecutor) driver).executeScript(
            "var selected = arguments[0];" +
            "var other = arguments[1];" +
            "selected.removeAttribute('disabled');" +
            "other.removeAttribute('disabled');" +
            "selected.checked = true;" +
            "other.checked = false;" +
            "selected.dispatchEvent(new Event('input', { bubbles: true }));" +
            "selected.dispatchEvent(new Event('change', { bubbles: true }));" +
            "if (window.jQuery) { $(selected).trigger('change'); }",
            selectedRadio,
            otherRadio
    );

    wait.until(driver -> {
        try {
            return driver.findElement(selectedRadioLocator).isSelected()
                    && !driver.findElement(otherRadioLocator).isSelected();
        } catch (Exception e) {
            return false;
        }
    });

    waitForOverlayToDisappear();
    handlePurchaseTypePopupAndClickNo();   // alert
    System.out.println("Purchase Type force selected: " + purchaseTypeName);
}


	private void waitForSelectedItemDetailsToLoad() {
	    wait.until(driver -> {
	        try {
	            String rate = driver.findElement(purchaseRateInput).getAttribute("value");
	            String mrp = driver.findElement(mrpInput).getAttribute("value");

	            return rate != null && !rate.trim().isEmpty()
	                    && mrp != null && !mrp.trim().isEmpty();
	        } catch (Exception e) {
	            return false;
	        }
	    });

	    waitForOverlayToDisappear();
	}

	
    public void enterQuantity(String quantity) {
        enterText(quantityInput, quantity);
        System.out.println("Quantity entered: " + quantity);
    }

    public void selectFreeItem(boolean isFreeItem, String freeQty) {
        WebElement checkbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(freeItemCheckbox)
        );

        scrollToElement(checkbox);

        if (checkbox.isSelected() != isFreeItem) {
            try {
                checkbox.click();
            } catch (Exception e) {
                jsClick(checkbox);
            }
        }

        dispatchChangeEvent(checkbox);

        if (isFreeItem && freeQty != null && !freeQty.trim().isEmpty()) {
            enterText(freeItemQtyInput, freeQty);
        }

        System.out.println("Is Free Item selected: " + isFreeItem);
    }

   public void enterDiscount(String discount) {
    forceSetDiscount(discount);
    System.out.println("Discount entered: " + discount);
}

   private void forceSetDiscount(String discount) {
    final String discountValue = discount == null ? "0" : discount.trim();

    WebElement element = wait.until(
            ExpectedConditions.presenceOfElementLocated(discountInput)
    );

    scrollToElement(element);

    ((JavascriptExecutor) driver).executeScript(
            "var el = arguments[0];" +
            "var val = arguments[1];" +
            "el.removeAttribute('readonly');" +
            "el.removeAttribute('disabled');" +
            "el.focus();" +
            "el.value = '';" +
            "if (window.jQuery) { $(el).val('').trigger('input').trigger('change'); }" +
            "el.dispatchEvent(new Event('input', { bubbles: true }));" +
            "el.dispatchEvent(new Event('change', { bubbles: true }));",
            element,
            discountValue
    );

    wait.until(driver -> {
        String currentValue = element.getAttribute("value");
        return currentValue == null || currentValue.trim().isEmpty();
    });

    ((JavascriptExecutor) driver).executeScript(
            "var el = arguments[0];" +
            "var val = arguments[1];" +
            "el.value = val;" +
            "if (window.jQuery) { $(el).val(val); }" +
            "el.dispatchEvent(new Event('input', { bubbles: true }));" +
            "el.dispatchEvent(new KeyboardEvent('keyup', { bubbles: true }));" +
            "el.dispatchEvent(new Event('change', { bubbles: true }));",
            element,
            discountValue
    );

    wait.until(driver -> {
        String actualValue = element.getAttribute("value");
        return actualValue != null && actualValue.trim().startsWith(discountValue);
    });

    ((JavascriptExecutor) driver).executeScript(
            "arguments[0].blur();" +
            "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));" +
            "if (window.jQuery) { $(arguments[0]).trigger('blur'); }",
            element
    );

    // Page script blur ke baad value overwrite kare to dobara force set
    sleepForDiscountCalculation();

    ((JavascriptExecutor) driver).executeScript(
            "var el = arguments[0];" +
            "var val = arguments[1];" +
            "el.value = val;" +
            "if (window.jQuery) { $(el).val(val).trigger('input').trigger('change').trigger('blur'); }" +
            "el.dispatchEvent(new Event('input', { bubbles: true }));" +
            "el.dispatchEvent(new Event('change', { bubbles: true }));" +
            "el.dispatchEvent(new Event('blur', { bubbles: true }));",
            element,
            discountValue
    );

    wait.until(driver -> {
        String actualValue = element.getAttribute("value");
        return actualValue != null
                && !actualValue.trim().equals("100.00")
                && actualValue.trim().startsWith(discountValue);
    });

    waitForOverlayToDisappear();
}
   
   private void sleepForDiscountCalculation() {
	    try {
	        Thread.sleep(500);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }
	}


    public void clickAddPOItems() {
        int beforeCount = getPOGridRowCount();

        WebElement addPOItem = wait.until(
                ExpectedConditions.presenceOfElementLocated(addPOItemsButton)
        );

        scrollToElement(addPOItem);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(addPOItem));
            new Actions(driver)
                    .moveToElement(addPOItem)
                    .pause(Duration.ofMillis(300))
                    .click()
                    .perform();
        } catch (Exception e) {
            jsClick(addPOItem);
        }

        waitForOverlayToDisappear();
        waitForPopupOverlay();

        wait.until(driver -> getPOGridRowCount() > beforeCount);

        System.out.println("Clicked on Add PO Items.");
    }

    public void addDirectPOItem(
            String locationName,
            String supplierSearchText,
            String supplierNameToSelect,
            String itemType,
            String itemSearchText,
            String itemNameToSelect,
            String purchaseType,
            String quantity,
            boolean isFreeItem,
            String freeQty,
            String discount
    ) {
        selectPODirect();
        selectLocation(locationName);
        selectSupplierName(supplierSearchText, supplierNameToSelect);
        addSinglePOItem(
                itemType,
                itemSearchText,
                itemNameToSelect,
                purchaseType,
                quantity,
                isFreeItem,
                freeQty,
                discount
        );

        System.out.println("Direct PO item added successfully.");
    }

    public void addMultipleDirectPOItems(
            String locationName,
            String supplierSearchText,
            String supplierNameToSelect,
            String purchaseType,
            List<POItemDetails> items
    ) {
        selectPODirect();
        selectLocation(locationName);
        selectSupplierName(supplierSearchText, supplierNameToSelect);

        for (POItemDetails item : items) {
            addSinglePOItem(
                    item.itemType,
                    item.itemSearchText,
                    item.itemNameToSelect,
                    purchaseType,
                    item.quantity,
                    item.isFreeItem,
                    item.freeQty,
                    item.discount
            );

            System.out.println("PO item added: " + item.itemNameToSelect);
        }

        System.out.println("Multiple Direct PO items added successfully.");
    }

   private void addSinglePOItem(
        String itemType,
        String itemSearchText,
        String itemNameToSelect,
        String purchaseType,
        String quantity,
        boolean isFreeItem,
        String freeQty,
        String discount
) {
    selectItemType(itemType);
    selectItemName(itemSearchText, itemNameToSelect);

    waitForSelectedItemDetailsToLoad();

    selectPurchaseTypeByName(purchaseType);

    enterQuantity(quantity);
    selectFreeItem(isFreeItem, freeQty);

    clickAddPOItemsWithDiscount(discount, purchaseType);
}



    
    
    public void clickAddPOItemsWithDiscount(String discount, String purchaseType) {
    int beforeCount = getPOGridRowCount();

    // Re-apply purchase type just before Add button click
    selectPurchaseTypeByName(purchaseType);

    forceDiscountValueBeforeAdd(discount);

    WebElement addPOItem = wait.until(
            ExpectedConditions.presenceOfElementLocated(addPOItemsButton)
    );

    scrollToElement(addPOItem);

    try {
        wait.until(ExpectedConditions.elementToBeClickable(addPOItem));
        new Actions(driver)
                .moveToElement(addPOItem)
                .pause(Duration.ofMillis(100))
                .click()
                .perform();
    } catch (Exception e) {
        jsClick(addPOItem);
    }

    waitForOverlayToDisappear();
    waitForPopupOverlay();

    wait.until(driver -> getPOGridRowCount() > beforeCount);

    System.out.println("Clicked on Add PO Items with discount: " + discount);
}

    
    private void forceDiscountValueBeforeAdd(String discount) {
        final String discountValue = discount == null || discount.trim().isEmpty()
                ? "0"
                : discount.trim();

        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(discountInput)
        );

        scrollToElement(element);

        ((JavascriptExecutor) driver).executeScript(
                "var el = arguments[0];" +
                "var val = arguments[1];" +
                "el.removeAttribute('readonly');" +
                "el.removeAttribute('disabled');" +
                "el.focus();" +
                "el.value = val;" +
                "if (window.jQuery) { $(el).val(val); }",
                element,
                discountValue
        );

        String actualValue = element.getAttribute("value");

        if (!actualValue.trim().equals(discountValue)) {
            throw new RuntimeException(
                    "Discount value not set properly. Expected: "
                            + discountValue + ", Actual: " + actualValue
            );
        }

        System.out.println("Discount force set before Add PO Items: " + discountValue);
    }

    



    // ===== HELPER METHODS =====
    private void selectDropdownByVisibleText(By locator, String visibleText) {
        waitForOverlayToDisappear();

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        scrollToElement(dropdown);

        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                "if (window.jQuery) { $(arguments[0]).trigger('change'); }",
                dropdown
        );

        waitForOverlayToDisappear();
    }

    private void searchAndSelectAutocompleteOption(
            By inputLocator,
            String searchText,
            String optionText
    ) {
        waitForOverlayToDisappear();

        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(inputLocator)
        );

        scrollToElement(input);

        input.click();
        input.sendKeys(Keys.CONTROL, "a");
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(searchText);

        waitForOverlayToDisappear();

        By exactOption = By.xpath(
                "//*[contains(@class,'ui-menu-item') or self::li or self::td or self::tr]" +
                "[contains(normalize-space(.),\"" + optionText + "\")]"
        );

        WebElement option;

        try {
            option = wait.until(ExpectedConditions.elementToBeClickable(exactOption));
        } catch (Exception e) {
            List<WebElement> options = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(visibleAutocompleteOptions)
            );

            option = options.stream()
                    .filter(WebElement::isDisplayed)
                    .filter(ele -> ele.getText().trim().contains(optionText))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException(
                            "Autocomplete option not found: " + optionText
                    ));
        }

        scrollToElement(option);

        try {
            option.click();
        } catch (Exception e) {
            jsClick(option);
        }

        waitForOverlayToDisappear();
        input.sendKeys(Keys.TAB);
    }

    private void selectRadioButton(By locator) {
        WebElement radio = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );

        scrollToElement(radio);

        if (!radio.isSelected()) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(radio));
                radio.click();
            } catch (Exception e) {
                jsClick(radio);
            }
        }

        dispatchChangeEvent(radio);
        waitForOverlayToDisappear();
    }

    private void enterText(By locator, String value) {
        if (value == null) {
            value = "";
        }

        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );

        scrollToElement(element);

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

        waitForOverlayToDisappear();
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                element
        );
    }

    private void dispatchChangeEvent(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                "if (window.jQuery) { $(arguments[0]).trigger('change'); }",
                element
        );
    }

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

    private int getPOGridRowCount() {
        return driver.findElements(poItemsGridRows).size();
    }
    
    public void clickSubmitPurchaseOrderTab() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(submitPOTab));

        scrollToElement(tab);

        try {
            tab.click();
        } catch (Exception e) {
            jsClick(tab);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(finalRemarksTextArea));

        System.out.println("Submit Purchase Order tab opened");
    }
    
    public void enterFinalRemarks(String remarks) {
        WebElement textarea = wait.until(ExpectedConditions.elementToBeClickable(finalRemarksTextArea));

        textarea.clear();
        textarea.sendKeys(remarks);

        System.out.println("Final Remarks entered: " + remarks);
    }
    
    public void selectFreightChargesWithGST(boolean shouldSelect) {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(freightGSTCheckbox));

        if (checkbox.isSelected() != shouldSelect) {
            checkbox.click();
        }

        System.out.println("Freight GST checkbox set to: " + shouldSelect);
    }
    
    public void enterFreightDetails(String freight, String remarks) {

        WebElement freightField = wait.until(ExpectedConditions.elementToBeClickable(freightInput));
        freightField.clear();
        freightField.sendKeys(freight);

        WebElement remarksField = wait.until(ExpectedConditions.elementToBeClickable(freightRemarksInput));
        remarksField.clear();
        remarksField.sendKeys(remarks);

        System.out.println("Freight: " + freight + " | Remarks: " + remarks);
    }
    
    
    public void clickFinalSubmit() {

        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(finalSubmitButton));

        scrollToElement(submitBtn);

        try {
            submitBtn.click();
        } catch (Exception e) {
            jsClick(submitBtn);
        }

        waitForOverlayToDisappear();

        System.out.println("Final Submit clicked");
    }
    
    public void submitPurchaseOrder(
            String remarks,
            boolean isFreightGST,
            String freight,
            String freightRemarks
    ) {
        clickSubmitPurchaseOrderTab();

        enterFinalRemarks(remarks);

        selectFreightChargesWithGST(isFreightGST);

        if (isFreightGST) {
            enterFreightDetails(freight, freightRemarks);
        }

        clickFinalSubmit();

        System.out.println("Purchase Order Submitted Successfully");
    }
    
}
