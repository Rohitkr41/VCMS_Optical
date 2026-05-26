package opticalTranscationMasterTest;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import opticalTranscationsMasterPage.PostReturnToSupplierPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class PostReturnToSupplierTest extends BaseTest {

    @Test
    public void verifyPostReturnToSupplier() {

        // ===== LOGIN =====

        LoginPage login =
                new LoginPage(driver);

        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ===== PAGE OBJECT =====

        PostReturnToSupplierPage returnPage =
                new PostReturnToSupplierPage(driver);

        // ===== NAVIGATE =====

        returnPage.navigateToReturnToSupplierPage();

        Assert.assertTrue(
                returnPage.isPageOpened(),
                "Return To Supplier page did not open successfully."
        );

        // ===== SELECT RECORD & POST =====

        returnPage.selectReturnRecordAndPost(
                "V001-26-IR-0015"
        );

        System.out.println(
                "Test Passed : Return posted successfully."
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