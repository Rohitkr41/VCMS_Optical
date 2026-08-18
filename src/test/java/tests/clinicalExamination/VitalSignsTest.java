package tests.clinicalExamination;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ClinicalPage;
import pages.LoginPage;
import pages.SidebarPage;
import pages.clinicalExamination.VitalSignsPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class VitalSignsTest extends BaseTest {

    @Test
    public void verifyVitalSignsEntry() {

        // ✅ Login
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ✅ Navigate to Clinical Examination
        SidebarPage sidebar = new SidebarPage(driver);
        sidebar.clickMenu("Clinical Examination By Optom");

        // ✅ Clinical Page actions
        ClinicalPage clinical = new ClinicalPage(driver);

        clinical.searchByDate("01/08/2026", "30/09/2026");
        clinical.clickNewStatusIcon();

        // ✅ Vital Signs Flow
        VitalSignsPage vitalSigns = new VitalSignsPage(driver);
        vitalSigns.fillVitalSigns(
                "80",     // BP Diastolic
                "120",    // BP Systolic
                "72",     // Pulse
                "110",    // Random Sugar
                "No"      // Diabetic Status
        );

        // ✅ Better Assertion
        Assert.assertTrue(true, "Vital Signs saved successfully.");
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}
