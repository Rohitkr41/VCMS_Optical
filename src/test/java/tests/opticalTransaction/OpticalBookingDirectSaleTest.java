package tests.opticalTransaction;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.opticalTransaction.OpticalBookingDirectSalePage;
import utils.ConfigReader;

public class OpticalBookingDirectSaleTest extends BaseTest {

    @Test
    public void verifyMultiplePatientOpticalBookingDirectSaleFlow() {

        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        OpticalBookingDirectSalePage bookingPage = new OpticalBookingDirectSalePage(driver);

        List<String> vcmrNumbers = Arrays.asList(
                "SGMF-BGT-26-0140",
                "SGMF-BGT-26-0141",
                "SGMF-BGT-26-0142"
        );

        for (String vcmrNo : vcmrNumbers) {
            bookingPage.openOpticalBookingPage();
            bookingPage.clickPlusIcon();

            bookingPage.clickVcmrSearchButton();
            bookingPage.searchVisionCenterPatientByDate("04/05/2026", "16/05/2026");

            bookingPage.selectPatientByVcmrNo(vcmrNo);

            bookingPage.addItemWithQuantity("FRAME", "fr", "FR26-0006", 1);
            bookingPage.addItemWithQuantity("LENS", "le", "LE26-0007", 1);

            String[][] discountData = {
                    {"HRBANA", "GENRAL DISCOUNT", "10", "Frame Discount"},
                    {"BIFOCAL LENSE", "", "5", "Lens Discount"}
            };

            bookingPage.applyDiscountOnMultipleItems(discountData);

            bookingPage.enterExpectedDeliveryDate("10/05/2026");

            String payableAmount = bookingPage.getPayableAmount();
            System.out.println("Payable Amount for " + vcmrNo + ": " + payableAmount);

            bookingPage.makeDirectSalePayment(
                    List.of(
                            OpticalBookingDirectSalePage.PaymentDetails.cash(payableAmount)
                    )
            );

            bookingPage.selectSalesExecutive("AHMED");
            bookingPage.enterCustomerGSTAndAddress("07ABCDE1234F1Z5", "Delhi Test Address");
            bookingPage.enterReceiptRemark(false, "Test receipt remark");

            bookingPage.submitOpticalBooking();

            System.out.println("Direct sale booking completed for VCMR No: " + vcmrNo);
        }
    }
}
