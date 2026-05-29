package opticalTranscationMasterTest;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import opticalTranscationsMasterPage.WareHousePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class WareHouseTest extends BaseTest {

    @Test
    public void verifyWareHouseNavigation() {

        // ===== LOGIN =====
        LoginPage login = new LoginPage(driver);

        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ===== PAGE OBJECT =====
        WareHousePage wareHousePage = new WareHousePage(driver);

        // ===== NAVIGATION =====
        wareHousePage.navigateToWareHousePage();
        
        // Step 1
//        wareHousePage.bookedToUnderFitting();

        // Step 2
        wareHousePage.underFittingToReadyToDispatch();

        // Step 3
        wareHousePage.readyToDispatchToDispatch();
        
        wareHousePage.fillDispatchDetails(
                "By Courier",
                "BLUE DART",
                "100",
                "TRK12345",
                "Courier dispatched successfully"
        );

        // ===== VALIDATION =====
        Assert.assertTrue(
                wareHousePage.isWareHousePageOpened(),
                "WareHouse page is not opened"
        );

        System.out.println("WareHouse page opened successfully.");
    }
    

    // ===== SCREENSHOT ON FAILURE =====
    @AfterMethod
    public void captureFailure(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {

            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}