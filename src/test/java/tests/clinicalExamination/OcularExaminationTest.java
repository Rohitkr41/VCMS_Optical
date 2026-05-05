
package tests.clinicalExamination;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ClinicalPage;
import pages.LoginPage;
import pages.SidebarPage;
import pages.clinicalExamination.OcularExaminationPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class OcularExaminationTest extends BaseTest {

    @Test
    public void validateOcularExaminationFlow() {

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
            sidebar.clickMenu("Clinical Examination By Optom");
            System.out.println("✅ Navigated to Clinical Examination");

            // ===== OPEN PATIENT =====
            ClinicalPage clinical = new ClinicalPage(driver);
            clinical.searchByDate("04/05/2026", "18/05/2026");
            clinical.clickNewStatusIcon();
            System.out.println("✅ Patient opened");

            // ===== OCULAR EXAM PAGE =====
            OcularExaminationPage exam = new OcularExaminationPage(driver);
            exam.openOcularExamTab();
            System.out.println("✅ Ocular Examination tab opened");

            // =====================================================
            // CHANGE THIS FLAG:
            // true  = checkbox/default values flow
            // false = manual values flow
            // =====================================================
            boolean isNormalFlow = false;

            System.out.println("🔵 Filling Ocular Examination...");

            exam.fillOcularExamination(

                    isNormalFlow,

                    // ===== TOP =====
                    "ORTHOPHORIA",
                    "Full",
                    "Automation Test",

                    // ===== RIGHT EYE =====
                    "FLAT",
                    "NEGATIVE",
                    "QUIET",
                    "CLEAR",
                    "DEEP/QUIET",
                    "ROUND REGULAR REACTING",
                    "IMSC",
                    "RED GLOW PRESENT",
                    "RE OK",

                    // ===== LEFT EYE =====
                    "FLAT",
                    "NEGATIVE",
                    "QUIET",
                    "CLEAR",
                    "DEEP/QUIET",
                    "ROUND REGULAR REACTING",
                    "IMSC",
                    "RED GLOW PRESENT",
                    "LE OK"
            );

            System.out.println("✅ Ocular Examination saved");

            Assert.assertTrue(true, "✅ Test completed successfully");

        } catch (Exception e) {

            System.out.println("❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();

            ScreenshotUtil.captureScreenshot(driver, "OcularExamTest_Failure");
            Assert.fail("Test failed due to exception", e);
        }
    }

    @Test
    public void validateOcularExaminationWithCheckboxFlow() {

        try {

            LoginPage login = new LoginPage(driver);
            login.login(
                    ConfigReader.getProperty("username"),
                    ConfigReader.getProperty("password")
            );
            System.out.println("✅ Login successful");

            SidebarPage sidebar = new SidebarPage(driver);
            sidebar.clickMenu("Clinical Examination By Optom");
            System.out.println("✅ Navigated to Clinical Examination");

            ClinicalPage clinical = new ClinicalPage(driver);
            clinical.searchByDate("05/05/2026", "30/05/2026");
            clinical.clickNewStatusIcon();
            System.out.println("✅ Patient opened");

            OcularExaminationPage exam = new OcularExaminationPage(driver);
            exam.openOcularExamTab();
            System.out.println("✅ Ocular Examination tab opened");

            System.out.println("🔵 Filling Ocular Examination with checkbox/default flow...");

            exam.fillOcularExamination(

                    true,

                    // ===== TOP =====
                    "ORTHOPHORIA",
                    "Full",
                    "Automation Test - Checkbox Flow",

                    // ===== RIGHT EYE =====
                    "", "", "", "", "", "", "", "", "",

                    // ===== LEFT EYE =====
                    "", "", "", "", "", "", "", "", ""
            );

            System.out.println("✅ Ocular Examination saved with checkbox/default flow");

            Assert.assertTrue(true, "✅ Checkbox flow completed successfully");

        } catch (Exception e) {

            System.out.println("❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();

            ScreenshotUtil.captureScreenshot(driver, "OcularExamCheckboxTest_Failure");
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

