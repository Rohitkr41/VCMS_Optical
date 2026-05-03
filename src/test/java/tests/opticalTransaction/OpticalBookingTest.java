package tests.opticalTransaction;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.opticalTransaction.OpticalBookingPage;
import utils.ConfigReader;

public class OpticalBookingTest extends BaseTest {

    @Test
    public void verifyOpticalBookingClick() {

        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        OpticalBookingPage opticalBookingPage = new OpticalBookingPage(driver);
        opticalBookingPage.openOpticalBookingPage();
        
        opticalBookingPage.clickPlusIcon();
        opticalBookingPage.clickVcmrSearchButton();
        opticalBookingPage.searchVisionCenterPatientByDate("01/05/2026", "06/05/2026");
        
        opticalBookingPage.clickFirstPatientSelectIcon();
        
        opticalBookingPage.selectItemType("FRAME");


        System.out.println("Optical Booking page opened successfully");
    }
}
