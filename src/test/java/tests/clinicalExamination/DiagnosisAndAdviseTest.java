package tests.clinicalExamination;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ClinicalPage;
import pages.LoginPage;
import pages.SidebarPage;
import pages.clinicalExamination.DiagnosisAndAdvisePage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class DiagnosisAndAdviseTest extends BaseTest {

    @Test
    public void validatePatientStatusAndAdviseFlow() {

        try {

            // ===== LOGIN =====
            LoginPage login = new LoginPage(driver);
            login.login(
                    ConfigReader.getProperty("username"),
                    ConfigReader.getProperty("password")
            );
            System.out.println("Login successful");

            // ===== NAVIGATION =====
            SidebarPage sidebar = new SidebarPage(driver);
            sidebar.clickMenu("Clinical Examination By Optom");
            System.out.println("Navigated to Clinical Examination");

            // ===== OPEN PATIENT =====
            ClinicalPage clinical = new ClinicalPage(driver);
            clinical.searchByDate("11/05/2026", "30/05/2026");
            clinical.clickNewOrInProgressStatusIcon();
            System.out.println("Patient opened");

            // ===== PATIENT STATUS & ADVISE =====
            DiagnosisAndAdvisePage diag = new DiagnosisAndAdvisePage(driver);
          
            System.out.println("Filling Patient Status & Advise...");

            diag.completeDiagnosisAndPatientStatusFlow(
                    "A PATTERN ET",              // Eye Diagnosis
                    "BOTH",                      // Eye
                    true,                        // Follow-up in Vision Center
                    true,                        // Refer to Hospital
                    true,                        // Prescribe Spectacles
                    "CONJUNCTIVITIS",            // Advise For
                    "SINGLE VISION GLASSES",     // Spectacles Type
                    "Patient will visit later",  // Other Remarks
                    true,                        // Hospital Visit Date checkbox
                    "30/05/2026",                // Hospital Visit Date
                    false,                       // Hold Patient
                    null,                        // Hold Reason
                    null                         // Hold Remarks
            );


            System.out.println("Patient Status & Advise completed");
            Assert.assertTrue(true);

        } catch (Exception e) {

            System.out.println("TEST FAILED: " + e.getMessage());
            e.printStackTrace();

            ScreenshotUtil.captureScreenshot(driver, "PatientStatusAndAdviseTest_Failure");
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
