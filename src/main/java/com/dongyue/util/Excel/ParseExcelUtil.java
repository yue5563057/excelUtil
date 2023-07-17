package com.dongyue.util.Excel;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ParseExcelUtil {

    public static List<List<String>> parseExcel(InputStream inputStream, Integer startRow, Integer sheetIndex) throws IOException {
        Workbook workbook = WorkbookFactory.create(inputStream);
        // 打开Excel中的第一个Sheet
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        if (sheet == null) {
            return new ArrayList<>();
        }
        // 读取Sheet中的数据
        List<List<String>> rowList = new ArrayList<>();
        Integer rowIndex = 0;
        // 行
        for (Row row : sheet) {
            if (rowIndex < startRow) {
                rowIndex++;
                continue;
            }
            List<String> list = new ArrayList();
            // 单元格
            short lastCellNum = row.getLastCellNum();
            for (int i = 0; i <row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                if(cell!=null){
                    switch (cell.getCellType()) {
                        case STRING:
                            list.add(cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                list.add(cell.getDateCellValue().toString());
                            } else {
                                list.add(cell.getNumericCellValue() + "");
                            }
                            break;
                        case BOOLEAN:
                            list.add(cell.getBooleanCellValue() + "");
                            break;
                        case FORMULA:
                            list.add(cell.getCellFormula() + "");
                            break;
                        case BLANK:
                            list.add("");
                            break;
                        default:
                            list.add("");
                    }
                }else {
                    list.add(null);
                }

            }
            rowList.add(list);
        }
        return rowList;
    }

    public static List<List<String>> parseExcel(InputStream inputStream, Integer startRow) throws IOException {
        return parseExcel(inputStream, startRow, 0);
    }

    public static List<List<String>> parseExcel(InputStream inputStream) throws IOException {
        return parseExcel(inputStream, 0, 0);
    }
    public static List<List<String>> parseExcel(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return parseExcel(fileInputStream, 0, 0);
    }
    public static List<List<String>> parseExcel(File file, Integer startRow) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return parseExcel(fileInputStream, startRow, 0);
    }

    public static List<List<String>> parseExcel(File file, Integer startRow,Integer sheetNumber) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        return parseExcel(fileInputStream, startRow, sheetNumber);
    }
}
