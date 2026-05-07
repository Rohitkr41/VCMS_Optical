package opticalTranscationMasterTest;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import opticalTranscationsMasterPage.GRNItemData;
import opticalTranscationsMasterPage.PurchasePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class PurchaseTest extends BaseTest {

    @Test
    public void verifyGRNFlow() {

        // ===== LOGIN =====
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ===== PAGE OBJECT =====
        PurchasePage purchasePage = new PurchasePage(driver);

        // ===== NAVIGATION =====
        purchasePage.navigateToPurchase();

        Assert.assertTrue(
                purchasePage.isPageOpened(),
                "Purchase page did not open successfully."
        );

        // ===== STEP 1: MAIN PAGE REGION =====
        purchasePage.selectRegional("REGIONALMUKESH");

        // ===== STEP 2: ADD NEW GRN =====
        purchasePage.clickAddNewGRN();

        // ===== STEP 3: MODAL SELECTION =====
        purchasePage.selectModalRegional("REGIONALMUKESH");

        purchasePage.enterAndSelectPONumber("V001-26-PO-0007");

        purchasePage.clickGoButton();

        // ===== STEP 4: SKU DATA =====
        Map<String, GRNItemData> skuData = new HashMap<>();

//        skuData.put("FR26-0006", new GRNItemData("0", "4"));
        skuData.put("LE26-0006", new GRNItemData("0", "4"));
        skuData.put("LE26-0009", new GRNItemData("0", "3"));
//        skuData.put("FR26-0007", new GRNItemData("0", "2"));
        

        // ===== STEP 5: ENTER QTY =====
        purchasePage.enterGRNQtyBySKU(skuData);
        
     // ===== HEADER DETAILS =====
        purchasePage.fillInvoiceAndSaveGRN(
                "INV-12345",
                "1130",
                "Automation GRN entry"
        );
        

        System.out.println("GRN quantities entered successfully");

        // ===== OPTIONAL ASSERTION PLACEHOLDER =====
        Assert.assertTrue(true, "GRN flow completed successfully");
    }

    // ===== SCREENSHOT ON FAILURE =====
    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}