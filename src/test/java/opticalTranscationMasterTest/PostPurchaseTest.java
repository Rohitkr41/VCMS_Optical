package opticalTranscationMasterTest;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import opticalTranscationsMasterPage.PostPurchasePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class PostPurchaseTest extends BaseTest {

    @Test
    public void verifyPostPurchase() {

        // ===== LOGIN =====
        LoginPage login = new LoginPage(driver);

        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ===== PAGE OBJECT =====
        PostPurchasePage postPurchasePage =
                new PostPurchasePage(driver);

        // ===== NAVIGATE TO POST PURCHASE PAGE =====
        postPurchasePage.navigateToPostPurchasePage();

        Assert.assertTrue(
                postPurchasePage.isPageOpened(),
                "Post Purchase page did not open successfully."
        );

        // ===== SELECT RECORD & POST PURCHASE =====
        postPurchasePage.selectRecordAndPostPurchase(
                "V001-26-GN-0045"                //Enter Manually PURCHASE NO.
        );

        System.out.println(
                "Test Passed: Purchase posted successfully."
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
