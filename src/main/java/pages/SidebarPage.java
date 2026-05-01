package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
}