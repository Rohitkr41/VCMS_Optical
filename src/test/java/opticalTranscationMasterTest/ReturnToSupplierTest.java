package opticalTranscationMasterTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import opticalTranscationsMasterPage.ReturnToSupplierPage;
import pages.LoginPage;
import utils.ConfigReader;

public class ReturnToSupplierTest extends BaseTest {

    @Test
    public void verifyReturnToSupplierPage() {

        // ===== LOGIN =====
        LoginPage login = new LoginPage(driver);

        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ===== PAGE OBJECT =====
        ReturnToSupplierPage returnPage =
                new ReturnToSupplierPage(driver);

        // ===== NAVIGATE =====
        returnPage.navigateToReturnToSupplier();

        // ===== VALIDATION =====
        Assert.assertTrue(
                returnPage.isPageOpened(),
                "Return To Supplier page did not open successfully."
        );
        
     // CLICK ADD ICON
        returnPage.clickAddNewReturnStock();
        
        returnPage.selectGRNAndClickGo("V001-26-GN-0042");
        
     // ===== ENTER RETURN QTY =====
        returnPage.enterReturnQty("CA26-0004", "2");
        returnPage.enterReturnQty("CA26-0005", "2");
        
        returnPage.enterInvoiceNumber("INV-2026-001");

        returnPage.enterReturnRemarks(
                "Return stock automation testing"
        );

//        returnPage.clickSubmitButton();

        System.out.println(
                "Test Passed : Return To Supplier page opened successfully."
        );
    }
}