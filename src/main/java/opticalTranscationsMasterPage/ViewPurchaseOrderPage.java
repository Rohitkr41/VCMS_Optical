package opticalTranscationsMasterPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.BasePage;
public class ViewPurchaseOrderPage extends BasePage {

    public ViewPurchaseOrderPage(WebDriver driver) {
        super(driver);
    }

    private By opticalTransactionsMenu = By.xpath("//span[normalize-space()='Optical Transactions']/ancestor::a");
    private By opticalTransactionsCollapse = By.id("OpticalTransacations");
    private By viewPurchaseOrderMenu = By.cssSelector("a[href='/VCMS_Optical/OpticalRequest/ViewPurchaseOrder']");
    private By addNewButton = By.id("VPO_btnAddNew");

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
    private By poItemsGridRows = By.xpath("//table[@id='VPO_mdltblRecord']//tbody/tr");

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

    private By gstAmountInput = By.id("VPO_txtTotalGST");
    private By finalTotalAmountInput = By.id("VPO_txt_GSTTotal_GrossTotal");

    private By grossTotalInput = By.id("VPO_txtTotalAmount");
    private By totalQtyInput = By.id("VPO_txtTotalQuantity");
    private By cgstInput = By.id("VPO_txtCGST");
    private By sgstInput = By.id("VPO_txtSGST");
    private By igstInput = By.id("VPO_txtIGST");

    private By submitPOTab = By.xpath("//a[contains(text(),'Submit Purchase Order')]");
    private By finalRemarksTextArea = By.id("VPO_txtSubmitPoTextAreaRemark");
    private By freightGSTCheckbox = By.id("VPO_chkFreightChargesWithGST");
    private By freightInput = By.id("VPO_txtFreight");
    private By freightRemarksInput = By.id("VPO_txtFreightRemarks");
    private By finalSubmitButton = By.id("VPO_btnFinalPOSubmit");

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

    public void openOpticalTransactionsMenu() {
        WebElement menu = wait.until(ExpectedConditions.presenceOfElementLocated(opticalTransactionsMenu));
        WebElement collapse = wait.until(ExpectedConditions.presenceOfElementLocated(opticalTransactionsCollapse));

        String classValue = collapse.getAttribute("class");

        if (!classValue.contains("show")) {
            scrollToElement(menu);
            jsClick(menu);
            wait.until(ExpectedConditions.attributeContains(opticalTransactionsCollapse, "class", "show"));
        }

        System.out.println("Optical Transactions menu opened.");
    }

    public void clickViewPurchaseOrder() {
        openOpticalTransactionsMenu();

        WebElement viewPurchaseOrder = wait.until(ExpectedConditions.presenceOfElementLocated(viewPurchaseOrderMenu));
        scrollToElement(viewPurchaseOrder);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(viewPurchaseOrder));
            viewPurchaseOrder.click();
        } catch (Exception e) {
            jsClick(viewPurchaseOrder);
        }

        System.out.println("Clicked on View Purchase Order.");
    }

    public boolean isViewPurchaseOrderPageOpened() {
        try {
            wait.until(ExpectedConditions.urlContains("/VCMS_Optical/OpticalRequest/ViewPurchaseOrder"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddNew() {
        WebElement addNew = wait.until(ExpectedConditions.elementToBeClickable(addNewButton));
        scrollToElement(addNew);

        try {
            addNew.click();
        } catch (Exception e) {
            jsClick(addNew);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(locationDropdown));
        System.out.println("Clicked on Add New button.");
    }

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
        setPurchaseType(localPurchaseRadio, "Local");
    }

    public void selectPurchaseTypeInterState() {
        setPurchaseType(interStatePurchaseRadio, "Inter-State");
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

    private void setPurchaseType(By selectedRadioLocator, String purchaseTypeName) {
        waitForOverlayToDisappear();

        clickRadioByJS(selectedRadioLocator);
        waitForOverlayToDisappear();

        handlePurchaseTypePopupAndClickNo();
        waitForOverlayToDisappear();

        clickRadioByJS(selectedRadioLocator);
        waitForOverlayToDisappear();

        wait.until(driver -> driver.findElement(selectedRadioLocator).isSelected());

        System.out.println("Purchase Type selected: " + purchaseTypeName);
    }

    private void clickRadioByJS(By locator) {
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        scrollToElement(radio);

        ((JavascriptExecutor) driver).executeScript(
                "var el = arguments[0];" +
                "if (!el.checked) { el.click(); }" +
                "el.checked = true;" +
                "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "el.dispatchEvent(new Event('change', { bubbles: true }));" +
                "if (window.jQuery) { $(el).trigger('input').trigger('change'); }",
                radio
        );
    }

    private void ensurePurchaseTypeStillSelected(String purchaseType) {
        By expectedRadio;
        By otherRadio;

        if (purchaseType.equalsIgnoreCase("Local")) {
            expectedRadio = localPurchaseRadio;
            otherRadio = interStatePurchaseRadio;
        } else if (purchaseType.equalsIgnoreCase("Inter-State")
                || purchaseType.equalsIgnoreCase("InterState")) {
            expectedRadio = interStatePurchaseRadio;
            otherRadio = localPurchaseRadio;
        } else {
            throw new IllegalArgumentException("Invalid Purchase Type: " + purchaseType);
        }

        WebElement expected = wait.until(ExpectedConditions.presenceOfElementLocated(expectedRadio));
        WebElement other = wait.until(ExpectedConditions.presenceOfElementLocated(otherRadio));

        if (!expected.isSelected()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].checked = true;" +
                    "arguments[1].checked = false;" +
                    "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                    "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                    "if (window.jQuery) { $(arguments[0]).trigger('input').trigger('change'); }",
                    expected,
                    other
            );
        }

        wait.until(driver -> driver.findElement(expectedRadio).isSelected());
        System.out.println("Purchase Type verified: " + purchaseType);
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

                waitForPopupOverlay();
                System.out.println("Popup handled fast: NO clicked");
            }
        } catch (Exception e) {
            // Popup is optional.
        }
    }

    private void waitForSelectedItemDetailsToLoad() {
        wait.until(driver -> {
            try {
                String rate = driver.findElement(purchaseRateInput).getAttribute("value");
                String mrp = driver.findElement(mrpInput).getAttribute("value");

                return rate != null && !rate.trim().isEmpty()
                        && parseNumber(rate) > 0
                        && mrp != null && !mrp.trim().isEmpty();
            } catch (Exception e) {
                return false;
            }
        });

        waitForOverlayToDisappear();
    }

    public void enterQuantity(String quantity) {
        String qty = (quantity == null || quantity.trim().isEmpty()) ? "1" : quantity.trim();

        waitForOverlayToDisappear();
        setInputValueByJS(quantityInput, qty);
        triggerCalculationEvents(quantityInput);

        wait.until(driver -> nearlyEqual(valueOf(totalQtyInput), parseNumber(qty)));

        System.out.println("Quantity entered successfully: " + qty);
    }

    public void selectFreeItem(boolean isFreeItem, String freeQty) {
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(freeItemCheckbox));
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
            setInputValue(freeItemQtyInput, freeQty.trim());
        }

        System.out.println("Is Free Item selected: " + isFreeItem);
    }

    public void enterDiscount(String discount) {
        String value = (discount == null || discount.trim().isEmpty()) ? "0" : discount.trim();

        waitForOverlayToDisappear();

        WebElement discountField = wait.until(ExpectedConditions.elementToBeClickable(discountInput));
        scrollToElement(discountField);

        ((JavascriptExecutor) driver).executeScript(
                "var el = arguments[0];" +
                "var val = arguments[1];" +
                "el.removeAttribute('readonly');" +
                "el.removeAttribute('disabled');" +
                "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "el.focus();" +
                "setter.call(el, '');" +
                "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "el.dispatchEvent(new Event('change', { bubbles: true }));" +
                "if (window.jQuery) { $(el).val('').trigger('input').trigger('change'); }" +
                "setter.call(el, val);" +
                "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "el.dispatchEvent(new KeyboardEvent('keyup', { bubbles: true }));" +
                "el.dispatchEvent(new Event('change', { bubbles: true }));" +
                "el.dispatchEvent(new Event('blur', { bubbles: true }));" +
                "if (window.jQuery) { $(el).val(val).trigger('input').trigger('keyup').trigger('change').trigger('blur'); }",
                discountField,
                value
        );

        wait.until(driver -> nearlyEqual(valueOf(discountInput), parseNumber(value)));
        waitForOverlayToDisappear();

        System.out.println("Discount entered from test data: " + discountField.getAttribute("value"));
    }

    private void setInputValueByJS(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        scrollToElement(element);

        ((JavascriptExecutor) driver).executeScript(
                "var el = arguments[0];" +
                "var val = arguments[1];" +
                "el.removeAttribute('readonly');" +
                "el.removeAttribute('disabled');" +
                "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "setter.call(el, '');" +
                "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "setter.call(el, val);" +
                "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "el.dispatchEvent(new KeyboardEvent('keyup', { bubbles: true }));" +
                "el.dispatchEvent(new Event('change', { bubbles: true }));" +
                "el.dispatchEvent(new Event('blur', { bubbles: true }));" +
                "if (window.jQuery) {" +
                "  $(el).val(val).trigger('input').trigger('keyup').trigger('change').trigger('blur');" +
                "}",
                element,
                value
        );
    }

    private void triggerCalculationEvents(By locator) {
        WebElement element = driver.findElement(locator);

        ((JavascriptExecutor) driver).executeScript(
                "var el = arguments[0];" +
                "el.dispatchEvent(new Event('input', { bubbles: true }));" +
                "el.dispatchEvent(new KeyboardEvent('keyup', { bubbles: true }));" +
                "el.dispatchEvent(new Event('change', { bubbles: true }));" +
                "el.dispatchEvent(new Event('blur', { bubbles: true }));" +
                "if (window.jQuery) {" +
                "  $(el).trigger('input').trigger('keyup').trigger('change').trigger('blur');" +
                "}",
                element
        );
    }

    public void clickAddPOItems() {
        int beforeCount = getPOGridRowCount();

        WebElement addPOItem = wait.until(ExpectedConditions.presenceOfElementLocated(addPOItemsButton));
        scrollToElement(addPOItem);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                addPOItem
        );

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
                discount,
                true
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

        boolean purchaseTypeSelected = false;

        for (POItemDetails item : items) {
            addSinglePOItem(
                    item.itemType,
                    item.itemSearchText,
                    item.itemNameToSelect,
                    purchaseType,
                    item.quantity,
                    item.isFreeItem,
                    item.freeQty,
                    item.discount,
                    !purchaseTypeSelected
            );

            purchaseTypeSelected = true;
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
            String discount,
            boolean shouldSelectPurchaseType
    ) {
        selectItemType(itemType);
        selectItemName(itemSearchText, itemNameToSelect);
        waitForSelectedItemDetailsToLoad();

        if (shouldSelectPurchaseType) {
            selectPurchaseTypeByName(purchaseType);
        } else {
            ensurePurchaseTypeStillSelected(purchaseType);
        }

        enterQuantity(quantity);

        if (isFreeItem) {
            selectFreeItem(true, freeQty);
        }

        enterDiscount(discount);
        waitForGSTCalculation(quantity, discount);
        clickAddPOItems();
    }

    public void clickAddPOItemsWithDiscount(String discount, String purchaseType) {
        selectPurchaseTypeByName(purchaseType);
        enterDiscount(discount);
        waitForGSTCalculation(driver.findElement(quantityInput).getAttribute("value"), discount);
        clickAddPOItems();

        System.out.println("Clicked on Add PO Items with discount: " + discount);
    }

    private void searchAndSelectAutocompleteOption(By inputLocator, String searchText, String optionText) {
        waitForOverlayToDisappear();

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputLocator));
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
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
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

    private void setInputValue(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        scrollToElement(element);

        if (value == null) {
            value = "";
        }

        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            element.sendKeys(Keys.CONTROL, "a");
            element.sendKeys(Keys.DELETE);
            element.sendKeys(value);
            element.sendKeys(Keys.TAB);
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript(
                    "var el=arguments[0], val=arguments[1];" +
                    "el.removeAttribute('readonly');" +
                    "el.removeAttribute('disabled');" +
                    "el.focus();" +
                    "el.value=val;" +
                    "el.dispatchEvent(new Event('input',{bubbles:true}));" +
                    "el.dispatchEvent(new KeyboardEvent('keyup',{bubbles:true}));" +
                    "el.dispatchEvent(new Event('change',{bubbles:true}));" +
                    "el.dispatchEvent(new Event('blur',{bubbles:true}));" +
                    "if(window.jQuery){$(el).val(val).trigger('input').trigger('keyup').trigger('change').trigger('blur');}",
                    element,
                    value
            );
        }

        String expected = value;

        wait.until(driver -> {
            String actual = element.getAttribute("value");

            if (actual == null) {
                actual = "";
            }

            actual = actual.trim();

            if (actual.equals(expected)) {
                return true;
            }

            if (isNumeric(expected) && isNumeric(actual)) {
                return nearlyEqual(parseNumber(actual), parseNumber(expected));
            }

            return !expected.trim().isEmpty() && actual.contains(expected.trim());
        });

        waitForOverlayToDisappear();
    }

    private boolean isNumeric(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        try {
            parseNumber(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void waitForGSTCalculation(String quantity, String discount) {
        double qty = parseNumber((quantity == null || quantity.trim().isEmpty()) ? "1" : quantity);
        double disc = parseNumber((discount == null || discount.trim().isEmpty()) ? "0" : discount);

        wait.until(driver -> {
            try {
                double rate = valueOf(purchaseRateInput);
                double actualDiscount = valueOf(discountInput);

                double cgst = valueOf(cgstInput);
                double sgst = valueOf(sgstInput);
                double igst = valueOf(igstInput);
                double gstPercent = cgst + sgst + igst;

                double grossBeforeDiscount = rate * qty;
                double taxableAmount = grossBeforeDiscount - (grossBeforeDiscount * disc / 100.0);
                double expectedGST = taxableAmount * gstPercent / 100.0;
                double expectedTotal = taxableAmount + expectedGST;

                double actualGross = valueOf(grossTotalInput);
                double actualGST = valueOf(gstAmountInput);
                double actualTotal = valueOf(finalTotalAmountInput);

                boolean localSelected = driver.findElement(localPurchaseRadio).isSelected();
                boolean interStateSelected = driver.findElement(interStatePurchaseRadio).isSelected();

                System.out.println(
                        "CALC CHECK => local=" + localSelected +
                        ", interstate=" + interStateSelected +
                        ", rate=" + rate +
                        ", qty=" + qty +
                        ", discount=" + actualDiscount +
                        ", cgst=" + cgst +
                        ", sgst=" + sgst +
                        ", igst=" + igst +
                        ", gross=" + actualGross +
                        ", gst=" + actualGST +
                        ", total=" + actualTotal +
                        ", expectedGross=" + taxableAmount +
                        ", expectedGST=" + expectedGST +
                        ", expectedTotal=" + expectedTotal
                );

                return rate > 0
                        && localSelected
                        && !interStateSelected
                        && nearlyEqual(actualDiscount, disc)
                        && nearlyEqual(actualGross, taxableAmount)
                        && nearlyEqual(actualGST, expectedGST)
                        && nearlyEqual(actualTotal, expectedTotal);
            } catch (Exception e) {
                return false;
            }
        });
    }

    private double valueOf(By locator) {
        return parseNumber(driver.findElement(locator).getAttribute("value"));
    }

    private double parseNumber(String value) {
        if (value == null) {
            return 0.0;
        }

        String cleanValue = value.replaceAll("[^0-9.\\-]", "").trim();

        if (cleanValue.isEmpty()) {
            return 0.0;
        }

        return Double.parseDouble(cleanValue);
    }

    private boolean nearlyEqual(double actual, double expected) {
        return Math.abs(actual - expected) <= 0.05;
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
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
            // Overlay is optional.
        }
    }

    private void waitForPopupOverlay() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupOverlay));
        } catch (Exception e) {
            // Popup overlay is optional.
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
        setInputValue(finalRemarksTextArea, remarks == null ? "" : remarks);
        System.out.println("Final Remarks entered: " + remarks);
    }

    public void selectFreightChargesWithGST(boolean shouldSelect) {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(freightGSTCheckbox));
        scrollToElement(checkbox);

        if (checkbox.isSelected() != shouldSelect) {
            try {
                checkbox.click();
            } catch (Exception e) {
                jsClick(checkbox);
            }
        }

        dispatchChangeEvent(checkbox);
        System.out.println("Freight GST checkbox set to: " + shouldSelect);
    }

    public void enterFreightDetails(String freight, String remarks) {
        setInputValue(freightInput, freight == null ? "0" : freight);
        setInputValue(freightRemarksInput, remarks == null ? "" : remarks);

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
