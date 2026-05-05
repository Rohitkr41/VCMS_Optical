package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.RegistrationPage;
import utils.ConfigReader;

public class RegistrationPageTest extends BaseTest {

    @Test
    public void verifyNewRegistrationClick() {
        RegistrationPage registrationPage = new RegistrationPage(driver);

        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        registrationPage.clickPatientRegistration();
        registrationPage.clickNewRegistration();
        
        registrationPage.selectNewPatient();
        registrationPage.selectSpeciality("EYE");
        registrationPage.selectPatientType("WALK IN");
        registrationPage.enterFirstName("reshmi");
        registrationPage.enterLastName("sharma");
//        registrationPage.selectMaleGender();
        registrationPage.enterAgeYear("27");
        registrationPage.enterAgeMonth("8");
        registrationPage.enterNextOfKin("sharma family");
        registrationPage.enterContactNumber("7890654320");
        registrationPage.enterAddress("House No 12, Main Road");
//        registrationPage.selectAreaVillage("MALLAUR");
        registrationPage.selectAreaVillage("Alampur Fatehpur");
        registrationPage.selectDiabeticStatus("No");
        registrationPage.selectOccupation("SERVICE");
        registrationPage.selectQualification("GRADUATE");
        registrationPage.selectIdentityType("DRIVING LICENSE");
        registrationPage.enterDriverLicenseNo("DL1234567890");
        
        registrationPage.selectPaymentCategory("PAID");
        registrationPage.selectCashPaymentMode();
//        registrationPage.enterTransactionNumber("UPI123456789");
//        registrationPage.selectBankName("STATE BANK OF INDIA");
        registrationPage.clickSubmitButton();
        
        Assert.assertTrue(
                registrationPage.isPrintReceiptPopupDisplayed(),
                "Print receipt confirmation popup is not displayed"
        );

        Assert.assertEquals(
                registrationPage.getPrintReceiptPopupMessage(),
                "Are you sure, you want to print receipt?"
        );

        registrationPage.clickPrintReceiptNo();

        // Expected page/field check after click
        Assert.assertTrue(driver.getPageSource().contains("New Registration"));
    }

  
}
