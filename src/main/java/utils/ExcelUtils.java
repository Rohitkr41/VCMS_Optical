package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ExcelUtils {

    public static void exportTableToExcel(WebDriver driver, By rowsLocator, String fileName, String sheetName) {

        try {

            // Excel folder path
            String folderPath = System.getProperty("user.dir") + "/reports/excel/";

            File folder = new File(folderPath);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filePath = folderPath + fileName + ".xlsx";

            // Table rows
            List<WebElement> rows = driver.findElements(rowsLocator);

            // Table headers
            List<WebElement> headers = driver.findElements(By.xpath("//table//thead//th"));

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(sheetName);

            int rowNum = 0;

            // =========================
            // HEADER ROW
            // =========================

            Row headerRow = sheet.createRow(rowNum++);

            for (int i = 0; i < headers.size(); i++) {

                headerRow.createCell(i).setCellValue(headers.get(i).getText());
            }

            // =========================
            // TABLE DATA
            // =========================

            for (WebElement row : rows) {

                Row excelRow = sheet.createRow(rowNum++);

                List<WebElement> cols = row.findElements(By.tagName("td"));

                int colNum = 0;

                for (WebElement col : cols) {

                    excelRow.createCell(colNum++).setCellValue(col.getText());
                }
            }

            FileOutputStream file = new FileOutputStream(filePath);

            workbook.write(file);

            workbook.close();
            file.close();

            System.out.println("Excel Generated : " + filePath);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
