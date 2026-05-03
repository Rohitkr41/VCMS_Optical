package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TableUtils {

    public static boolean isTableEmpty(WebDriver driver) {

        By rows = By.xpath("//table//tbody//tr");

        int size = driver.findElements(rows).size();

        if(size == 0) {

            System.out.println("No Record Found");

            return true;
        }

        System.out.println("Records Found: " + size);

        return false;
    }

}
