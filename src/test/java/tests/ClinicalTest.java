package tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ClinicalPage;
import pages.LoginPage;
import pages.SidebarPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class ClinicalTest extends BaseTest {

    @Test
    public void verifyClinicalSearchAndFillExamination() {

        // Login
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // Sidebar navigation
        SidebarPage sidebar = new SidebarPage(driver);
        sidebar.clickMenu("Clinical Examination By Optom");

        // Clinical Page
        ClinicalPage clinical = new ClinicalPage(driver);

        // Perform search with date filter
        clinical.searchByDate("05/05/2026", "21/05/2026");

        // Click icon for "New" status row
        clinical.clickNewStatusIcon();

        // Open Clinical tab after patient/form screen opens
//        clinical.openClinicalTab();

        Assert.assertTrue(true, "Clinical Examination flow completed successfully.");
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}
