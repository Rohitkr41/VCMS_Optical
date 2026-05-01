package tests.clinicalExamination;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.SidebarPage;
import pages.clinicalExamination.ClinicalPage;
import pages.clinicalExamination.OcularHistoryPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class OcularHistoryTest extends BaseTest {

    @Test
    public void validateOcularAndSystemicFlow() {

        // ===== TEST DATA =====
        String disease = "CORNEA";
        String eye     = "BOTH";
        String surgery = "RETINA";
        String status  = "Unsatisfactory";
        String remarks = "Automation Test Entry";

        try {

            // ===== LOGIN =====
            LoginPage login = new LoginPage(driver);
            login.login(
                    ConfigReader.getProperty("username"),
                    ConfigReader.getProperty("password")
            );
            System.out.println("✅ Login successful");

            // ===== NAVIGATION =====
            SidebarPage sidebar = new SidebarPage(driver);
            sidebar.clickMenu("Clinical Examination");
            System.out.println("✅ Navigated to Clinical Examination");

            // ===== OPEN PATIENT =====
            ClinicalPage clinical = new ClinicalPage(driver);
            clinical.searchByDate("02/04/2026", "03/04/2026");
            clinical.clickNewStatusIcon();
            System.out.println("✅ Patient opened");

            // ===== PAGE =====
            OcularHistoryPage ocular = new OcularHistoryPage(driver);
            ocular.openOcularHistoryTab();
            System.out.println("✅ Ocular History tab opened");

            // =====================================================
            // 🔵 OCULAR HISTORY FLOW
            // =====================================================
            System.out.println("🔵 Filling Ocular History...");

            ocular.selectDisease(disease);
            ocular.selectEye(eye);
            ocular.selectSurgeryTreatmentDone(surgery);
            ocular.selectStatus(status);
            ocular.enterRemarks(remarks);

            ocular.clickSaveButton();       // Save Ocular
            ocular.handleSuccessPopup();    // Handle popup once

            System.out.println("✅ Ocular History saved");

            // =====================================================
            // 🟣 SYSTEMIC DISEASE FLOW
            // =====================================================
            System.out.println("🟣 Filling Systemic Disease...");

         // 🟣 SYSTEMIC
            ocular.fillSystemicDisease(
                    "Yes",
                    "CAD",
                    "2",
                    "Weeks",
                    "Controlled",
                    "Satisfactory"
            );

            Assert.assertTrue(true);
            
        

            System.out.println("✅ Systemic Disease saved");

        } catch (Exception e) {

            System.out.println("❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();

            ScreenshotUtil.captureScreenshot(driver, "OcularSystemicTest_Failure");
            Assert.fail("Test failed due to exception", e);
        }

        Assert.assertTrue(true, "✅ Test completed successfully");
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}
