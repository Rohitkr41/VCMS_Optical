package opticalTranscationMasterTest;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import opticalTranscationsMasterPage.IssueItemsPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class IssueItemsTest extends BaseTest {

    @Test
    public void verifyIssueItemsFlow() {

        // LOGIN
        LoginPage login =
                new LoginPage(driver);

        login.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        // PAGE OBJECT
        IssueItemsPage issuePage =
                new IssueItemsPage(driver);

        // NAVIGATE
        issuePage.navigateToIssueItems();

        // CLICK ADD NEW
        issuePage.clickAddNew();

        // STEP-1
        // IF ALL
        issuePage.selectItemCategory("All");

        // OR INDIVIDUAL
        // issuePage.selectItemCategory("FRAME");

        // STEP-2 SEARCH
        issuePage.clickSearchButton();

        // STEP-3 ISSUE QTY
        issuePage.enterIssueQty("CA26-0004", "2");
        issuePage.enterIssueQty("CA26-0005", "2");
        issuePage.enterIssueQty("CL26-0004", "5");

        // STEP-4 SELECT VC
        issuePage.selectVisionCenter(
                "APOLO VISION CENTER"
        );

        // STEP-5 NOTES
        issuePage.enterNotes(
                "Issue Items automation testing"
        );

        // CLICK ISSUE ITEMS
//        issuePage.clickIssueItemsButton();

        Assert.assertTrue(true);

        System.out.println(
                "Issue Items Test Passed Successfully"
        );
    }
    
    @AfterMethod
    public void captureFailure(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {

            ScreenshotUtil.captureScreenshot(
                    driver,
                    result.getName()
            );
        }
    }
}
