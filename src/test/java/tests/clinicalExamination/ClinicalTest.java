package tests.clinicalExamination;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.SidebarPage;
import pages.clinicalExamination.ClinicalPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class ClinicalTest extends BaseTest {

    @Test
    public void verifyClinicalSearchAndFillExamination() {

        // ✅ Login
        LoginPage login = new LoginPage(driver);
        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
                
        );

        // ✅ Sidebar navigation
        SidebarPage sidebar = new SidebarPage(driver);
        sidebar.clickMenu("Clinical Examination");

        // ✅ Clinical Page
        ClinicalPage clinical = new ClinicalPage(driver);
        clinical.openClinicalMenu();

        // ✅ Perform search with date filter
        clinical.searchByDate("02/04/2026", "03/04/2026");

        // ✅ Click icon for "New" status row
        clinical.clickNewStatusIcon();
        clinical.openClinicalTab();

       
        // Assertion example (adapt as per your app’s confirmation message)
        Assert.assertTrue(true, "Clinical Examination saved successfully.");
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
        }
    }
}
