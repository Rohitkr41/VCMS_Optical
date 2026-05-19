package opticalTranscationMasterTest;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import opticalTranscationsMasterPage.ApprovePurchaseOrderPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class ApprovePurchaseOrderTest extends BaseTest {

    @Test
    public void verifyApprovePurchaseOrder() {

        // ===== LOGIN =====
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ===== PAGE OBJECT =====
        ApprovePurchaseOrderPage approvePOPage = new ApprovePurchaseOrderPage(driver);

        // ===== NAVIGATE TO APPROVE PURCHASE ORDER =====
        approvePOPage.navigateToApprovePurchaseOrder();
        Assert.assertTrue(
                approvePOPage.isPageOpened(),
                "Approve Purchase Order page did not open successfully."
        );

        // ===== APPROVE FIRST PENDING PURCHASE ORDER =====
        approvePOPage.navigateToApprovePurchaseOrder();
        
     // Filter POs from 01-05-2026 to 06-05-2026
        approvePOPage.filterPurchaseOrders(
            "REGIONALAJAY",   // Regional (or null to skip)
//        		 "SHRI GURU MAHIPATIRAJ EYE BANK  RESEARCH FOUNDATION TRUST",
//        		 "REGV3M",
            "",                 // Supplier Name (or null)
            "",                 // PO Number (or null)
            "06/05/2026",       // From Date
            "30/05/2026"        // To Date
        );
        

        
     // Approve all "New PO" records with remark
        approvePOPage.approveNewPORecords("Approved via automation test");
        
        System.out.println("Test Passed: First pending Purchase Order approved successfully");
    }

    // ===== SCREENSHOT ON FAILURE =====
    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}