package tests.opticalTransaction;

import java.util.List;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.opticalTransaction.OpticalBookingAdvancePage;
import utils.ConfigReader;

public class OpticalBookingAdvanceTest extends BaseTest {

    @Test
    public void verifyOpticalBookingAdvanceFlow() {

        // Step 1: Login
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // Step 2: Initialize Page
        OpticalBookingAdvancePage bookingPage = new OpticalBookingAdvancePage(driver);

        // Step 3: Navigate to Optical Booking
        bookingPage.openOpticalBookingPage();
        bookingPage.clickPlusIcon();

        // Step 4: Open VCMR Search
        bookingPage.clickVcmrSearchButton();

        // Step 5: Search Patient
        bookingPage.searchVisionCenterPatientByDate("01/05/2026", "06/05/2026");

        // Step 6: Select Patient
        bookingPage.clickFirstPatientSelectIcon();

        // Step 7: Add Multiple Items
        bookingPage.addItemWithQuantity("FRAME", "fr", "FR26-0006", 1);
        bookingPage.addItemWithQuantity("LENS", "le", "LE26-0007", 1);

        // Step 8: Apply Discount On Multiple Items
        String[][] discountData = {
                {"HRBANA", "GENRAL DISCOUNT", "10", "Frame Discount"},
                {"BIFOCAL LENSE", "", "5", "Lens Discount"}
        };

        bookingPage.applyDiscountOnMultipleItems(discountData);

        // Step 9: Expected Delivery Date
        bookingPage.enterExpectedDeliveryDate("10/05/2026");

        // Step 10: Advance Payment
        bookingPage.makeAdvancePayment(
                "300",
                "Advance paid by cash",
                List.of(
                        OpticalBookingAdvancePage.PaymentDetails.cash("300")
                )
        );

        // Step 11: Final Details
        bookingPage.selectSalesExecutive("AHMED");
        bookingPage.enterCustomerGSTAndAddress("07ABCDE1234F1Z5", "Delhi Test Address");
        bookingPage.enterReceiptRemark(false, "Test receipt remark");

        // Step 12: Submit Booking
        bookingPage.submitOpticalBooking();

        System.out.println("Optical Booking Advance Flow executed successfully");
        System.out.println("Advance bppking");
    }
}
