package opticalTranscationMasterTest;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import opticalTranscationsMasterPage.ReceiveItemsPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class ReceiveItemsTest extends BaseTest {

    @Test
    public void verifyReceiveItems() {

        // ===== LOGIN =====

        LoginPage login =
                new LoginPage(driver);

        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ===== PAGE OBJECT =====

        ReceiveItemsPage receivePage =
                new ReceiveItemsPage(driver);

        // ===== NAVIGATE =====

        receivePage.navigateToReceiveItemsPage();

        Assert.assertTrue(
                receivePage.isPageOpened(),
                "Receive Items page did not open successfully."
        );

        // ===== SELECT RECORD & RECEIVE =====

        receivePage.selectReceiveRecordAndReceive(
                "V001-26-IS-0008"
        );

        System.out.println(
                "Test Passed : Item received successfully."
        );
    }

    // ===== SCREENSHOT ON FAILURE =====

    @AfterMethod
    public void captureFailure(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {

            ScreenshotUtil.captureScreenshot(
                    driver,
                    result.getName()
            );
        }
    }
}