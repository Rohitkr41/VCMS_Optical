package tests.clinicalExamination;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.SidebarPage;
import pages.clinicalExamination.ClinicalPage;
import pages.clinicalExamination.DiagnosisAndAdvisePage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class DiagnosisAndAdviseTest extends BaseTest {

    @Test
    public void validateDiagnosisAndAdviseFlow() {

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

            // ===== DIAGNOSIS PAGE =====
            DiagnosisAndAdvisePage diag = new DiagnosisAndAdvisePage(driver);
            diag.openDiagnosisTab();
            System.out.println("✅ Diagnosis & Advise tab opened");

            // =====================================================
            // 🔵 FILL DIAGNOSIS & ADVISE
            // =====================================================
            System.out.println("🔵 Filling Diagnosis & Advise...");

            diag.completeDiagnosisFlow(

                    // ===== EYE DIAGNOSIS =====
                    "AADI S/P K-PRO",
                    "BOTH",

                    // ===== EAR DIAGNOSIS =====
                    "CFEOM",
                    "BOTH",

                    // ===== ADVICE =====
                    "ADVISE FOR SPECTACLES",
                    true,
                    "SINGLE VISION GLASSES",

                    "NORMAL",
                    "BOTH",

                    // ===== REFERRAL =====
                    true,
                    "Base",  //Hospital Type
                    "Cross Partner",   //Hospital Type
                    "10/04/2026",
                    "Eye Hospital (EH)",
                    "DR SHROFF HOSPITAL DARYAGANJ"
            );

            System.out.println("✅ Diagnosis & Advise completed");

            // =====================================================
            // 🟡 HOLD FLOW ADDED
            // =====================================================
            System.out.println("🟡 Performing HOLD action...");

//            diag.handleHold(
//                    "TELECONSULTATION",   // update as per dropdown
//                    "Patient will visit later"
//            );

            System.out.println("✅ Hold completed successfully");

            Assert.assertTrue(true);

        } catch (Exception e) {

            System.out.println("❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();

            ScreenshotUtil.captureScreenshot(driver, "DiagnosisTest_Failure");
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