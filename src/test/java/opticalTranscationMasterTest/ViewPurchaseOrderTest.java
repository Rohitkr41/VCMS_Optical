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
        		 new POItemDetails("FRAME", "ga", "FR26-0005", "30", false, "", "4"), //QTY , "", discount%
                new POItemDetails("FRAME", "hr", "FR26-0006", "30", false, "", "5"), //QTY , "", discount%
                new POItemDetails("FRAME", "fr", "FR26-0007", "30", false, "", "3"),  //QTY , "", discount%
               
                new POItemDetails("CASE", "ca", "CA26-0005", "30", false, "", "0"), //case
                new POItemDetails("CASE", "ca", "CA26-0004", "30", false, "", "0"),
                
              new POItemDetails("CONTACT LENS", "cl", "CL26-0004", "30", false, "", "0"), //contact lense
//                new POItemDetails("CONTACT LENS", "cl", "CA26-0005", "30", false, "", "0"), //contact lense
                
                new POItemDetails("GOGGLES", "go", "GO26-0001", "30", false, "", "0"), //goggles
                new POItemDetails("GOGGLES", "go", "GO26-0002", "30", false, "", "0"), //goggles              
                new POItemDetails("LOW VISION AIDS", "lv", "LV26-0001", "30", false, "", "0"), //low vision aids
                new POItemDetails("LOW VISION AIDS", "lv", "LV26-0002", "30", false, "", "0"), //low vision aids
                new POItemDetails("LOW VISION AIDS", "lv", "LV26-0003", "30", false, "", "0"), //low vision aids
                
                new POItemDetails("SOLUTION", "so", "SO26-0002", "30", false, "", "0"), //solution
                new POItemDetails("SOLUTION", "so", "SO26-0003", "30", false, "", "0"), //solution
                
                new POItemDetails("SELVETS", "se", "SE26-0005", "30", false, "", "0"), //SELVETS
                new POItemDetails("SELVETS", "se", "SE26-0004", "30", false, "", "0") //SELVETS
//                
//                              
////                new POItemDetails("LENS", "le", "LE26-0007", "10", false, "", ""),
////                new POItemDetails("LENS", "le", "LE26-0007", "10", false, "", "")
        );

        // ===== ADD ITEMS =====
        viewPurchaseOrderPage.addMultipleDirectPOItems(
                "REGIONALAJAY",
//                "SHRI GURU MAHIPATIRAJ EYE BANK  RESEARCH FOUNDATION TRUST",
//                "REGV3M",
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
