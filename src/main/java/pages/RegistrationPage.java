package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {

    WebDriver driver;
    WebDriverWait wait;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Patient Registration Heading
    By patientRegistrationHeading =
            By.xpath("//span[contains(text(),'Patient Registration')]");

    // Menu Items
    By newRegistrationLink =
            By.xpath("//span[contains(normalize-space(),'New Registration')]");

    By modifyRegistrationLink =
            By.xpath("//li[contains(normalize-space(),'Modify Registration')]");

    By viewReceiptLink =
            By.xpath("//li[contains(normalize-space(),'View Receipt')]");
    
 // 1. Patient Instance radio buttons
    By newPatientLabel = By.xpath("//label[@for='PR_rdbNewPatient']");
    By newPatientRadio = By.id("PR_rdbNewPatient");
    By oldPatientLabel = By.xpath("//label[@for='PR_rdbOldPatient']");
    By oldPatientRadio = By.id("PR_rdbOldPatient");

    // 2. Speciality dropdown
    By specialityDropdown = By.id("PR_ddlSpeciality");

    // 3. Patient Type dropdown
    By patientTypeDropdown = By.id("PR_ddlPatientType");

    // 4. First Name
    By firstNameTextbox = By.id("PR_txtFirstName");

    // 5. Last Name
    By lastNameTextbox = By.id("PR_txtLastName");

    // 6. Gender radio buttons
    By maleRadio = By.id("PR_rdbGenderMale");
    By femaleRadio = By.id("PR_rdbGenderFemale");
    By transgenderRadio = By.id("PR_rdbGenderOther");

    // 7. Age - Year
    By ageYearTextbox = By.id("PR_txtAge");

    // 8. Month
    By ageMonthTextbox = By.id("PR_txtAgeMM");

    // 9. Next Of Kin
    By nextOfKinTextbox = By.id("PR_txtNextofKin");

    // 10. Contact Number
    By contactNumberTextbox = By.id("PR_txtContactNumber");

    // 11. Door No. / Street / Locality
    By addressTextbox = By.id("PR_txtAddress");

    // 12. Area / Village autocomplete input
    By areaVillageTextbox = By.id("PR_txtArea");
    By areaSuggestion = By.xpath("//ul[contains(@class,'ui-autocomplete')]//li[1]");

    // 13. Diabetic Status dropdown
    By diabeticStatusDropdown = By.id("PR_ddlDiabeticStatus");

    // 14. Occupation dropdown
    By occupationDropdown = By.id("PR_ddlOccupation");

    // 15. Qualification dropdown
    By qualificationDropdown = By.id("PR_ddlQualification");

    // 16. Identity Type dropdown
    By identityTypeDropdown = By.id("PR_ddlIdentityType");

    // 17. Driver License No.
    By driverLicenseTextbox = By.id("PR_txtDriverLIcenseNo");
    
 // 18. Payment Category dropdown
    By paymentCategoryDropdown = By.id("PR_ddlPaymentCategory");

    // 19. Payment Mode dropdown
    By paymentModeDropdown = By.id("PR_ddlPaymentMode");

    // 20. Transaction Number
    By transactionNumberTextbox = By.id("PR_txtTransectionId");

    // 21. Bank Name dropdown
    By bankNameDropdown = By.id("PR_ddlBank");

    // 22. Submit button
    By submitButton = By.id("PR_btnSubmit");



    public boolean isRegistrationPageDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(patientRegistrationHeading));
        return driver.findElement(patientRegistrationHeading).isDisplayed();
    }
    
    public void clickPatientRegistration() {
        wait.until(ExpectedConditions.elementToBeClickable(patientRegistrationHeading)).click();
    }
    
    public void clickNewRegistration() {
        wait.until(ExpectedConditions.elementToBeClickable(newRegistrationLink)).click();
    }

    public void clickModifyRegistration() {
        wait.until(ExpectedConditions.elementToBeClickable(modifyRegistrationLink)).click();
    }

    public void clickViewReceipt() {
        wait.until(ExpectedConditions.elementToBeClickable(viewReceiptLink)).click();
    }
    
 // 1. Patient Instance
    public void selectNewPatient() {
        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(newPatientRadio)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );

        try {
            wait.until(ExpectedConditions.elementToBeClickable(newPatientLabel)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void selectOldPatient() {
        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(oldPatientRadio)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );

        try {
            wait.until(ExpectedConditions.elementToBeClickable(oldPatientLabel)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    // 2. Speciality
    public void selectSpeciality(String speciality) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(specialityDropdown));
        new Select(element).selectByVisibleText(speciality);
    }

    // 3. Patient Type
    public void selectPatientType(String patientType) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(patientTypeDropdown));
        new Select(element).selectByVisibleText(patientType);
    }

    // 4. First Name
    public void enterFirstName(String firstName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameTextbox));
        element.clear();
        element.sendKeys(firstName);
    }

    // 5. Last Name
    public void enterLastName(String lastName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameTextbox));
        element.clear();
        element.sendKeys(lastName);
    }

    // 6. Gender
    public void selectMaleGender() {
        wait.until(ExpectedConditions.elementToBeClickable(maleRadio)).click();
    }

    public void selectFemaleGender() {
        wait.until(ExpectedConditions.elementToBeClickable(femaleRadio)).click();
    }

    public void selectTransgenderGender() {
        wait.until(ExpectedConditions.elementToBeClickable(transgenderRadio)).click();
    }

    // 7. Age - Year
    public void enterAgeYear(String ageYear) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(ageYearTextbox));
        element.clear();
        element.sendKeys(ageYear);
    }

    // 8. Month
    public void enterAgeMonth(String month) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(ageMonthTextbox));
        element.clear();
        element.sendKeys(month);
    }

    // 9. Next Of Kin
    public void enterNextOfKin(String nextOfKin) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nextOfKinTextbox));
        element.clear();
        element.sendKeys(nextOfKin);
    }

    // 10. Contact Number
    public void enterContactNumber(String contactNumber) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(contactNumberTextbox));
        element.clear();
        element.sendKeys(contactNumber);
    }

    // 11. Door No. / Street / Locality
    public void enterAddress(String address) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressTextbox));
        element.clear();
        element.sendKeys(address);
    }

    // 12. Area / Village autocomplete
    public void selectAreaVillage(String areaName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(areaVillageTextbox));
        element.clear();
        element.sendKeys(areaName);

        wait.until(ExpectedConditions.visibilityOfElementLocated(areaSuggestion)).click();
    }
    
 // 13. Diabetic Status
    public void selectDiabeticStatus(String diabeticStatus) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(diabeticStatusDropdown));
        new Select(element).selectByVisibleText(diabeticStatus);
    }

    // 14. Occupation
    public void selectOccupation(String occupation) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(occupationDropdown));
        new Select(element).selectByVisibleText(occupation);
    }

    // 15. Qualification
    public void selectQualification(String qualification) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(qualificationDropdown));
        new Select(element).selectByVisibleText(qualification);
    }

    // 16. Identity Type
    public void selectIdentityType(String identityType) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(identityTypeDropdown));
        new Select(element).selectByVisibleText(identityType);
    }

    // 17. Driver License No.
    public void enterDriverLicenseNo(String licenseNo) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(driverLicenseTextbox));
        element.clear();
        element.sendKeys(licenseNo);
    }
    
 // 18. Payment Category
    public void selectPaymentCategory(String paymentCategory) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(paymentCategoryDropdown)
        );
        new Select(element).selectByVisibleText(paymentCategory);
    }

    // 19. Payment Mode
    public void selectPaymentMode(String paymentMode) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(paymentModeDropdown)
        );
        new Select(element).selectByVisibleText(paymentMode);
    }

    // Payment Mode - CASH
    public void selectCashPaymentMode() {
        selectPaymentMode("CASH");
    }

    // Payment Mode - UPI
    public void selectUpiPaymentMode() {
        selectPaymentMode("UPI");
    }

    // Payment Mode - CREDIT CARD
    public void selectCreditCardPaymentMode() {
        selectPaymentMode("CREDIT CARD");
    }

    // Payment Mode - DEBIT CARD
    public void selectDebitCardPaymentMode() {
        selectPaymentMode("DEBIT CARD");
    }

    // Payment Mode - PAYTM
    public void selectPaytmPaymentMode() {
        selectPaymentMode("PAYTM");
    }

    // Payment Mode - ONLINE TRANSFER
    public void selectOnlineTransferPaymentMode() {
        selectPaymentMode("ONLINE TRANSFER");
    }

    // Payment Mode - CHEQUE
    public void selectChequePaymentMode() {
        selectPaymentMode("CHEQUE");
    }

    // 20. Transaction Number
    public void enterTransactionNumber(String transactionNumber) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(transactionNumberTextbox)
        );
        element.clear();
        element.sendKeys(transactionNumber);
    }

    // 21. Bank Name
    public void selectBankName(String bankName) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(bankNameDropdown)
        );
        new Select(element).selectByVisibleText(bankName);
    }

    // 22. Submit
    public void clickSubmitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }
    
 // Submit ke baad custom confirmation popup
    By printReceiptPopup = By.id("popup_container");
    By printReceiptMessage = By.id("popup_message");
    By printReceiptYesButton = By.id("popup_ok");
    By printReceiptNoButton = By.id("popup_cancel");
    
    public boolean isPrintReceiptPopupDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(printReceiptPopup));
        return driver.findElement(printReceiptPopup).isDisplayed();
    }

    public String getPrintReceiptPopupMessage() {
        WebElement message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(printReceiptMessage)
        );
        return message.getText();
    }

    public void clickPrintReceiptYes() {
        wait.until(ExpectedConditions.elementToBeClickable(printReceiptYesButton)).click();
    }

    public void clickPrintReceiptNo() {
        wait.until(ExpectedConditions.elementToBeClickable(printReceiptNoButton)).click();
    }



}
