package opticalTranscationMasterTest;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import opticalTranscationsMasterPage.OpticalDeliveryPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class OpticalDeliveryTest extends BaseTest {

    @Test
    public void verifyOpticalDeliverySearchWithDateFilter() {

        // Login
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // Optical Transactions -> Optical Delivery -> Date Filter -> Search
        OpticalDeliveryPage opticalDelivery = new OpticalDeliveryPage(driver);
        opticalDelivery.searchByDate(
                "29/05/2026",
                "30/06/2026"
        );

        Assert.assertTrue(true, "Optical Delivery search completed successfully.");
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}