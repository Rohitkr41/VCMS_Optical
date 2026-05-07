package opticalTranscationMasterTest;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import opticalTranscationsMasterPage.DiscountRefundApprovalPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class DiscountRefundApprovalTest extends BaseTest {

    @Test
    public void verifyDiscountRefundApprovalFlow() {

        // ===== LOGIN =====
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ===== PAGE OBJECT =====
        DiscountRefundApprovalPage page = new DiscountRefundApprovalPage(driver);

        // ===== NAVIGATION =====
        page.navigateToDiscountRefundPage();

        // ===== SET FILTER =====
        page.selectRegional("REGIONALMUKESH");
        // page.selectOpticalShop("ALL"); // if applicable
        page.setDateRange("06/05/2026", "30/05/2026");
        page.clickSearch();
    

        // ===== VALIDATE AND APPROVE =====
        if (page.isDataAvailable()) {
            // Approve by specific Booking No instead of just first record
           
            page.selectAllRecords();
            page.clickApproveButton();
            page.confirmApproval(); 
            System.out.println("Approval confirmed successfully.");
            
        } else {
            System.out.println("No records available to approve");
        }
    }

    // ===== SCREENSHOT ON FAILURE =====
    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}