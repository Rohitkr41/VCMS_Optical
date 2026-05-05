package opticalTranscationMasterTest;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import opticalTranscationsMasterPage.ViewPurchaseOrderPage;
import opticalTranscationsMasterPage.ViewPurchaseOrderPage.POItemDetails;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class ViewPurchaseOrderTest extends BaseTest {

    @Test
    public void verifyCreatePurchaseOrderWithMultipleItems() {

        // ===== LOGIN =====
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ===== PAGE OBJECT =====
        ViewPurchaseOrderPage viewPurchaseOrderPage = new ViewPurchaseOrderPage(driver);

        // ===== NAVIGATION =====
        viewPurchaseOrderPage.clickViewPurchaseOrder();

        Assert.assertTrue(
                viewPurchaseOrderPage.isViewPurchaseOrderPageOpened(),
                "View Purchase Order page did not open successfully."
        );

        // ===== CLICK ADD NEW =====
        viewPurchaseOrderPage.clickAddNew();

        // ===== MULTIPLE ITEMS DATA =====
        List<POItemDetails> poItems = Arrays.asList(
                new POItemDetails("FRAME", "fr", "FR26-0006", "8", false, "", "5"),
                new POItemDetails("LENS", "le", "LE26-0007", "6", false, "", "8")
        );

        // ===== ADD ITEMS =====
        viewPurchaseOrderPage.addMultipleDirectPOItems(
                "REGIONALMUKESH",
                "LE",
                "LENSKART SOLUTIONS LIMITED",
                "Local",
                poItems
        );

        // ===== SUBMIT PURCHASE ORDER =====
        viewPurchaseOrderPage.submitPurchaseOrder(
                "Automation Test PO",
                true,
                "50",
                "Delivery charges"
        );

        // ===== VALIDATION (BASIC) =====
        Assert.assertTrue(
                viewPurchaseOrderPage.isViewPurchaseOrderPageOpened(),
                "Purchase Order submission failed or page not loaded."
        );

        System.out.println("Test Passed: Purchase Order created & submitted successfully");
    }

    // ===== SCREENSHOT ON FAILURE =====
    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}