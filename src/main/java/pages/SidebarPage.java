package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SidebarPage extends BasePage {

    public SidebarPage(WebDriver driver) {
        super(driver);
    }

    // Dynamic Menu Click
    public void clickMenu(String menuName) {
        By menu = By.xpath("//span[normalize-space()='" + menuName + "']");
        
        click(menu);
    }

    public void clickSubMenu(String subMenu) {
        By submenu = By.xpath("//a[normalize-space()='" + subMenu + "']");
        click(submenu);
    }
    public void clickClinicalExaminationByOptom() {
        By clinicalMenu = By.xpath(
            "//span[contains(@class,'menu-title') and normalize-space()='Clinical Examination By Optom']"
        );

        WebElement menu = wait.until(ExpectedConditions.presenceOfElementLocated(clinicalMenu));

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", menu
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);
    }
}