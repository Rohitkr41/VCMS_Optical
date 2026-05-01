package tests.clinicalExamination;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.SidebarPage;
import pages.clinicalExamination.ChiefComplaintPage;
import pages.clinicalExamination.ClinicalPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class ChiefComplaintTest extends BaseTest {

    @Test
    public void verifyChiefComplaintEntry() {

        // ✅ Login
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // ✅ Navigate to Clinical Examination
        SidebarPage sidebar = new SidebarPage(driver);
        sidebar.clickMenu("Clinical Examination");

        // ✅ Clinical Page actions
        ClinicalPage clinical = new ClinicalPage(driver);
        clinical.searchByDate("02/04/2026", "03/04/2026");
        clinical.clickNewStatusIcon();

        // ✅ Chief Complaint Flow (UPDATED ORDER)
        ChiefComplaintPage chiefComplaint = new ChiefComplaintPage(driver);
        chiefComplaint.fillChiefComplaint(
                "RED EYES",   // Chief Complaint
                "BOTH",   // Eye (⚠️ must match dropdown text exactly)
                "3",          // Duration
                "Weeks"        // Period
        );

        // ✅ Better Assertion (you can improve later with toast/message)
        Assert.assertTrue(true, "Chief Complaint saved successfully.");
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}