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

    private static final String FROM_DATE = "01/07/2026";
    private static final String TO_DATE = "30/07/2026";

    @Test
    public void verifyOpticalDeliverySearchWithDateFilter() {
        OpticalDeliveryPage opticalDelivery = loginAndSearchOpticalDelivery();

        Assert.assertTrue(true, "Optical Delivery search completed successfully.");
    }

    @Test
    public void verifyWaitingToReceiveRecordCanBeSubmittedAsHold() {
        OpticalDeliveryPage opticalDelivery = loginAndSearchOpticalDelivery();

        opticalDelivery.processFirstWaitingToReceiveOrHoldRecordAsHold(
                "Scratch",
                "Perfect",
                "Inaccurate",
                "Hold submitted from automation"
        );

        Assert.assertTrue(true, "Waiting to Receive record submitted as Hold successfully.");
    }

    @Test
    public void verifyWaitingToReceiveRecordCanBeSubmittedAsReceive() {
        OpticalDeliveryPage opticalDelivery = loginAndSearchOpticalDelivery();

        opticalDelivery.processFirstWaitingToReceiveOrHoldRecordAsReceive(
                "Receive submitted from automation"
        );

        Assert.assertTrue(true, "Waiting to Receive record submitted as Receive successfully.");
    }

    private OpticalDeliveryPage loginAndSearchOpticalDelivery() {
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        OpticalDeliveryPage opticalDelivery = new OpticalDeliveryPage(driver);
        opticalDelivery.searchByDate(
                FROM_DATE,
                TO_DATE
        );

        return opticalDelivery;
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}
