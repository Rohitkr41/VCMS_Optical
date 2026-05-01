package tests.clinicalExamination;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.LoginPage;
import pages.SidebarPage;
import pages.clinicalExamination.ClinicalPage;
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
            sidebar.clickMenu("Clinical Examination");
            System.out.println("✅ Navigated to Clinical Examination");

            // ===== OPEN PATIENT =====
            ClinicalPage clinical = new ClinicalPage(driver);
            clinical.searchByDate("02/04/2026", "03/04/2026");
            clinical.clickNewStatusIcon();
            System.out.println("✅ Patient opened");

            // ===== OCULAR EXAM PAGE =====
            OcularExaminationPage exam = new OcularExaminationPage(driver);
            exam.openOcularExamTab();
            System.out.println("✅ Ocular Examination tab opened");
            
            

            // =====================================================
            // 🔵 FILL OCULAR EXAMINATION
            // =====================================================
            System.out.println("🔵 Filling Ocular Examination...");

            exam.fillOcularExamination(

                    false, // ===== TOP =====
            		"ORTHOPHORIA",
            		"Full"   ,         // Movement
                    "Automation Test",  // Remarks
                    
                    
                    
                    // ===== RIGHT EYE =====
                    "FLAT",   // Lid
                    "NEGATIVE",   // Roplas
                    "QUIET",   // Conjunctiva
                    "CLEAR",    // Cornea
                    "DEEP/QUIET",     // Anterior Chamber
                    "ROUND REGULAR REACTING",    // Pupil
                    "DISTANT VISION",    // Lens
                    "DR AI NEGATIVE",   // Fundus
                    "RE OK",    // Remarks

                    // ===== LEFT EYE =====
                    "FLAT",
                    "NEGATIVE",
                    "QUIET",
                    "CLEAR",
                    "DEEP/QUIET",
                    "ROUND REGULAR REACTING",
                    "DISTANT VISION",
                    "DR AI NEGATIVE",
                    "LE OK"
            );

            System.out.println("✅ Ocular Examination saved");

            Assert.assertTrue(true);

        } catch (Exception e) {

            System.out.println("❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();

            ScreenshotUtil.captureScreenshot(driver, "OcularExamTest_Failure");
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
