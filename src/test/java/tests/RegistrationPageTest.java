package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.RegistrationPage;
import utils.ConfigReader;
import utils.TestDataGenerator;

public class RegistrationPageTest extends BaseTest {

    @Test
    public void verifyNewRegistrationClick() {

        LoginPage login = new LoginPage(driver);

        System.out.println("Username: " + ConfigReader.getProperty("username"));
        System.out.println("Password: " + ConfigReader.getProperty("password"));

        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password"));

        int registrationLimit = 8; // Change as needed

        for (int count = 1; count <= registrationLimit; count++) {

            try {

                System.out.println("\n========== REGISTRATION " + count + " ==========");

                RegistrationPage registrationPage = new RegistrationPage(driver);

                registrationPage.clickPatientRegistration();
                registrationPage.clickNewRegistration();

                registrationPage.selectNewPatient();
                registrationPage.selectPatientType("WALK IN");

                registrationPage.enterFirstName(TestDataGenerator.getFirstName());
                registrationPage.enterLastName(TestDataGenerator.getLastName());
                registrationPage.enterAgeYear(TestDataGenerator.getAgeYear());
                registrationPage.enterAgeMonth(TestDataGenerator.getAgeMonth());
                registrationPage.enterNextOfKin(TestDataGenerator.getNextOfKin());
                registrationPage.enterContactNumber(TestDataGenerator.getMobileNumber());
                registrationPage.enterAddress(TestDataGenerator.getAddress());

                registrationPage.selectAreaVillage("Samaspur");

                registrationPage.selectDiabeticStatus("No");
                registrationPage.selectOccupation("SERVICE");
                registrationPage.selectQualification("GRADUATE");
                registrationPage.selectIdentityType("DRIVING LICENSE");

                registrationPage.enterDriverLicenseNo(
                        TestDataGenerator.getDrivingLicense());

                registrationPage.selectPaymentCategory("PAID");
                registrationPage.selectCashPaymentMode();

                registrationPage.clickSubmitButton();

                Assert.assertTrue(
                        registrationPage.isPrintReceiptPopupDisplayed(),
                        "Print receipt popup not displayed");

                Assert.assertEquals(
                        registrationPage.getPrintReceiptPopupMessage(),
                        "Are you sure, you want to print receipt?");

                registrationPage.clickPrintReceiptNo();

                System.out.println("✅ Registration Successful : " + count);

            } catch (Exception e) {

                System.out.println("❌ Registration Fail : " + count);
                e.printStackTrace();
            }
        }
    }
}
