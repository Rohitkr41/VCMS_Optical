package utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {

    private static final int HEADER_ROW_INDEX = 6;      // Excel row 7
    private static final int DATA_START_ROW_INDEX = 7;  // Excel row 8

    public static List<CampRegistrationData> getCampRegistrationData(String filePath) {

        List<CampRegistrationData> patients = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            Row headerRow = sheet.getRow(HEADER_ROW_INDEX);
            Map<String, Integer> columns = new HashMap<>();

            for (Cell cell : headerRow) {
                String header = formatter.formatCellValue(cell).trim().toUpperCase();
                if (!header.isEmpty()) {
                    columns.put(header, cell.getColumnIndex());
                }
            }

            for (int i = DATA_START_ROW_INDEX; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                String gender = getValue(row, columns, "GENDER", formatter);
                if (gender.isEmpty()) continue;

                CampRegistrationData data = new CampRegistrationData();

                data.setCampName(getValue(row, columns, "EICHER MOBILE VAN", formatter));
                data.setGender(gender);
                data.setNextOfKin(getValue(row, columns, "NEXT OF KIN", formatter));
                data.setDistrict(getValue(row, columns, "DISTRICT", formatter));
                data.setQualification(getValue(row, columns, "PROFESSION/QUALIFICATION", formatter));
                data.setOccupation(getValue(row, columns, "OCCUPATION", formatter));
                data.setIdentityType(getValue(row, columns, "IDENTITY TYPE", formatter));
                data.setDrivingExp(getValue(row, columns, "EXPERIENCE", formatter));
                data.setRemarks("Automation Test");

                setAgeValues(data, getValue(row, columns, "AGE", formatter));

                data.setPreviousEyeCheckup(getValue(row, columns, "PREVIOUS EYE CHECKUP", formatter));
                data.setEyeExaminedCenter(getValue(row, columns, "EYE CARE CENTER", formatter));
                data.setEyeExaminedDate(getOptionalValue(row, columns, "EYE EXAMINED DATE", formatter));

                data.setPreviousEarCheckup(getValue(row, columns, "PREVIOUS EAR CHECKUP", formatter));
                data.setEarExaminedCenter(getValue(row, columns, "EAR CARE CENTER", formatter));
                data.setEarExaminedDate(getOptionalValue(row, columns, "EAR EXAMINED DATE", formatter));

                patients.add(data);
            }

        } catch (Exception e) {
            throw new RuntimeException("Excel data read karne mein issue hai: " + filePath, e);
        }

        return patients;
    }

    private static String getValue(Row row, Map<String, Integer> columns, String columnName, DataFormatter formatter) {
        Integer index = columns.get(columnName.toUpperCase());

        if (index == null) {
            throw new RuntimeException("Column not found in Excel: " + columnName);
        }

        Cell cell = row.getCell(index);
        return cell == null ? "" : formatter.formatCellValue(cell).trim();
    }

    private static String getOptionalValue(Row row, Map<String, Integer> columns, String columnName, DataFormatter formatter) {
        Integer index = columns.get(columnName.toUpperCase());

        if (index == null) {
            return "";
        }

        Cell cell = row.getCell(index);
        return cell == null ? "" : formatter.formatCellValue(cell).trim();
    }

    private static void setAgeValues(CampRegistrationData data, String age) {
        String year = "";
        String month = "";

        if (age != null && !age.trim().isEmpty()) {
            age = age.toUpperCase().trim();

            if (age.contains("Y")) {
                year = age.substring(0, age.indexOf("Y")).trim();
            }

            if (age.contains("Y") && age.contains("M")) {
                month = age.substring(age.indexOf("Y") + 1, age.indexOf("M")).trim();
            }
        }

        data.setAgeYear(year);
        data.setAgeMonth(month);
    }
}