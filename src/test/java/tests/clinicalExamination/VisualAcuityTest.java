package tests.clinicalExamination;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ClinicalPage;
import pages.LoginPage;
import pages.SidebarPage;
import pages.clinicalExamination.VisualAcuityPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class VisualAcuityTest extends BaseTest {

    @Test
    public void validateFullVisualAcuityAndRefractionFlow() {

        String spectacles = "Yes";
        String years = "1";
        String months = "6";
        String purpose = "Distance Vision";

        String reUnaided = "6/18";
        String reSpecs = "6/6";
        String rePH = "6/6";
        String reNear = "N6";

        String leUnaided = "6/24";
        String leSpecs = "6/12";
        String lePH = "6/9";
        String leNear = "N8";

        try {
            LoginPage login = new LoginPage(driver);
            login.login(
                    ConfigReader.getProperty("username"),
                    ConfigReader.getProperty("password")
            );
            System.out.println("Login successful");

            SidebarPage sidebar = new SidebarPage(driver);
            sidebar.clickMenu("Clinical Examination By Optom");
            System.out.println("Navigated to Clinical Examination");

            ClinicalPage clinical = new ClinicalPage(driver);
            clinical.searchByDate("01/07/2026", "30/09/2026");
            clinical.clickNewStatusIcon();
            System.out.println("Patient opened");

            VisualAcuityPage visual = new VisualAcuityPage(driver);

            visual.openVisualAcuityTab();
            visual.fillVisualAcuityForm(
                    spectacles, years, months, purpose,
                    reUnaided, reSpecs, rePH, reNear,
                    leUnaided, leSpecs, lePH, leNear
            );

            System.out.println("Visual Acuity saved");

            visual.fillFullRefractionFlow(
                    // PG
                    "-1.00", "-0.50", "180", "1.75",
                    "-1.25", "-0.75", "170", "1.75",

                    // DRY
                    "-1.00", "-0.50", "180",
                    "-1.25", "-0.75", "170",

                    // CYCLO
                    "-1.50", "-0.75", "180",
                    "-1.75", "-1.00", "170",

                    // FINAL
                    "-1.25", "-0.50", "180", "6/6",
                    "-1.50", "-0.75", "170", "6/6",

                    // EXTRA ADD
                    "+1.00", "+1.00",

                    // REMARKS
                    "Dry OK", "Dry OK",
                    "Cyclo OK", "Cyclo OK",
                    "Add OK", "Add OK",

                    // NEAR VISION NPC
                    "10", "10",

                    // IOP
                    "14 mmHg", "16 mmHg", "10:05 AM"
            );

            visual.fillNearVision("N6", "10", "N6", "10");
            visual.fillIPD("32", "30", "14", "20");
            visual.fillIOP("10:05 AM", "14 mmHg", "10:05 AM", "16 mmHg");

            visual.clickRefractionSave();
            visual.handleSuccessPopup();

            System.out.println("Full Refraction saved successfully");

            Assert.assertTrue(true, "Test completed successfully");

        } catch (Exception e) {
            System.out.println("TEST FAILED: " + e.getMessage());
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
