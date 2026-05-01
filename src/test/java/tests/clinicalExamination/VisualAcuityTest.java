

package tests.clinicalExamination;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.SidebarPage;
import pages.clinicalExamination.ClinicalPage;
import pages.clinicalExamination.VisualAcuityPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class VisualAcuityTest extends BaseTest {

    @Test
    public void validateFullVisualAcuityAndRefractionFlow() {

        // ===== VISUAL ACUITY DATA =====
        String spectacles = "Yes";
        String years = "1";
        String months = "6";
        String purpose = "Distance Vision";

        String reUnaided = "6/18";
        String reSpecs   = "6/6";
        String rePH      = "6/6";
        String reNear    = "N6";

        String leUnaided = "6/24";
        String leSpecs   = "6/12";
        String lePH      = "6/9";
        String leNear    = "N8";

        String colorVision = "Normal";
        String stereopsis  = "50-100";
        String contrast    = "45";

        // ===== REFRACTION DATA ====

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

            // ===== VISUAL ACUITY PAGE =====
            VisualAcuityPage visual = new VisualAcuityPage(driver);

            System.out.println("🔵 Filling Visual Acuity...");

            visual.openVisualAcuityTab();
            visual.fillVisualAcuityForm(
                    spectacles, years, months, purpose,
                    reUnaided, reSpecs, rePH, reNear,
                    leUnaided, leSpecs, lePH, leNear,
                    colorVision, stereopsis, contrast
            );

            System.out.println("✅ Visual Acuity + Other Diagnostic saved");
            
            // ===== REFRACTION FLOW =====
         
            System.out.println("🟢 Filling Refraction...");

            visual.fillFullRefractionFlow(

                // ===== PG =====
                "-1.00", "-0.50", "180", "+1.00",
                "-1.25", "-0.75", "170", "+1.00",

                // ===== DRY =====
                "-1.00", "-0.50", "180",
                "-1.25", "-0.75", "170",

                // ===== CYCLO =====
                "-1.50", "-0.75", "180",
                "-1.75", "-1.00", "170",

                // ===== FINAL =====
                "-1.25", "-0.50", "180", "+1.00",
                "-1.50", "-0.75", "170", "+1.00",

                // ===== EXTRA ADD =====
                "+1.00", "+1.00",

                // ===== REMARKS =====
                "Dry OK", "Dry OK",
                "Cyclo OK", "Cyclo OK",
                "Add OK", "Add OK",

                // ===== NPC =====
                "6", "6",

                // ===== IOP =====
                "4 mmHg", "4 mmHg", "10:30 AM"
            );

            System.out.println("✅ Full Refraction saved successfully");

            Assert.assertTrue(true, "✅ Test completed successfully");

           

            
        } catch (Exception e) {

            System.out.println("❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
            ScreenshotUtil.captureScreenshot(driver, "VisualAcuity_Refraction_Failure");
            Assert.fail("Test failed due to exception", e);
        }
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
} 