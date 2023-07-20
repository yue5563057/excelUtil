package com.dongyue.util.parse;

import com.dongyue.util.StringUtil;
import com.dongyue.util.anno.*;
import com.dongyue.util.model.CellValue;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出异形表
 */
public class ExportIrregularExcelUtil {

    public static <T> void export(T t, OutputStream outputStream) {

        Class<T> aClass = (Class<T>) t.getClass();
        IrregularExcel annotation = aClass.getAnnotation(IrregularExcel.class);
        String sheetName = "";
        if (annotation == null) {
            sheetName = "sheetName";
        } else {
            sheetName = annotation.sheetName();
        }
        //第一步：创建一个webbook,对应一个excel
        HSSFWorkbook wb = new HSSFWorkbook();
        //第二步：在webbook添加一个sheet，与之对应的是excle中的excel
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        int sheetMaxRow = 0;
        int sheetMaxClomun = 0;

        Field[] fields = aClass.getDeclaredFields();
        List<CellValue> list = new ArrayList<>();
        for (Field field : fields) {
            IrregularFieldNameAnno fileName = field.getAnnotation(IrregularFieldNameAnno.class);
            if (fileName != null) {
                CellValue cellValue = createCellValue(fileName.row(),fileName.cloumn(),fileName.title(),fileName.colspan(),fileName.rowspan());
                sheetMaxClomun = Math.max(fileName.colspan()+fileName.cloumn(),sheetMaxClomun);
                sheetMaxRow = Math.max(fileName.row(),sheetMaxRow);
                list.add(cellValue);
            }
            IrregularFieldAnno fieldAnno = field.getAnnotation(IrregularFieldAnno.class);
            if (fieldAnno != null) {
                try {
                    field.setAccessible(Boolean.TRUE);
                    CellValue cellValue1  = createCellValue(fieldAnno.cloumn(),fieldAnno.row()
                            ,field.get(t).toString(),fieldAnno.colspan(),fieldAnno.rowspan());
                    sheetMaxClomun = Math.max(fieldAnno.colspan()+fieldAnno.cloumn(),sheetMaxClomun);
                    sheetMaxRow = Math.max(fieldAnno.row(),sheetMaxRow);
                    list.add(cellValue1);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            IrregularItem irregularItem = field.getAnnotation(IrregularItem.class);
            if (irregularItem != null) {
                //获取f的类
                Class<?> fieldClazz = field.getType();
                //判断是否为基本类型
                if (fieldClazz.isPrimitive()) {
                    continue;
                }
                //判断fc是否和List相同或者其父类
                if (fieldClazz.isAssignableFrom(List.class)) {
                    //如果是List类型，得到其Generic的类型
                    Type fc = field.getGenericType();
                    if (fc instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) fc;
                        //得到泛型里的class类型对象。
                        Class listItem = (Class) pt.getActualTypeArguments()[0];
                        //获取最大的列
                        Integer maxcolumn = 0;
                        Map<Integer, String> cloumnFeild = new HashMap<>();
                        Field[] declaredFields = listItem.getDeclaredFields();
                        for (Field declaredField : declaredFields) {
                            StandardExcelAttr standardExcelAttr = declaredField.getAnnotation(StandardExcelAttr.class);
                            //导出列表
                            int i = standardExcelAttr.excelColumn();
                            maxcolumn = Math.max(i, maxcolumn);
                            cloumnFeild.put(i, declaredField.getName());
                        }
                        sheetMaxClomun = Math.max(maxcolumn,sheetMaxClomun);
                        try {
                            field.setAccessible(true);
                            List list1 = (List) field.get(t);
                            Integer listRow = irregularItem.startRow();
                            for (Object o : list1) {
                                if (listRow.equals(irregularItem.startRow())) {
                                    //第一行打印标题
                                    for (int i = 0; i < maxcolumn; i++) {
                                        String s = cloumnFeild.get(i);
                                        if (StringUtil.isNotNull(s)) {
                                            Field field1 = listItem.getDeclaredField(s);
                                            StandardExcelAttr annotation1 = field1.getAnnotation(StandardExcelAttr.class);
                                            CellValue cellValue1 = createCellValue(listRow,i,annotation1.title(),0,0);
                                            list.add(cellValue1);
                                        }
                                    }
                                    listRow++;
                                }
                                for (int i = 0; i < maxcolumn; i++) {
                                    String s = cloumnFeild.get(i);
                                    if (StringUtil.isNotNull(s)) {
                                        Field field1 = listItem.getDeclaredField(s);
                                        field1.setAccessible(true);
                                        CellValue cellValue1 = createCellValue(listRow,i,field1.get(o).toString(),0,0);
                                        list.add(cellValue1);
                                    }
                                }
                                listRow++;
                            }
                            sheetMaxRow = Math.max(listRow,sheetMaxRow);
                        } catch (IllegalAccessException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < sheetMaxRow; i++) {
            for (int j = 0; j < sheetMaxClomun; j++) {
                Row row1 = sheet.getRow(i);
                if (row1 == null) {
                    row1 = sheet.createRow(i);
                }
                Cell cell1 = row.getCell(j);
                if (cell1 == null) {
                    cell1 = row.createCell(j);
                }
                cell1 = row1.createCell(j);
                // 使用 HSSFCellStyle 对象设置单元格边框
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cell1.setCellStyle(cellStyle);
            }
        }
        //HSSFCell对应的是每一行的数据插入
        for (CellValue cellValue : list) {
            setCellValue(sheet, cellValue, cellStyle);
        }
        // 第六步，将文件存到指定位置
        try {
            wb.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CellValue  createCellValue(int nameRow,int column,String value,int cloSpan,int rowSpan){
        CellValue cellValue = new CellValue();
        cellValue.setRow(nameRow);
        cellValue.setColumn(column);
        cellValue.setValue(value);
        cellValue.setColspan(cloSpan);
        cellValue.setRowspan(rowSpan);
        return cellValue;
    }


    private static void setCellValue(Sheet sheet, CellValue cellValue, CellStyle cellStyle) {
        Row row = sheet.getRow(cellValue.getRow());
        if (row == null) {
            row = sheet.createRow(cellValue.getRow());
        }
        Cell cell = row.getCell(cellValue.getColumn());
        if (cell == null) {
            cell = row.createCell(cellValue.getColumn());
        }





        cell.setCellValue(cellValue.getValue());
        cell.setCellStyle(cellStyle);
        if (cellValue.getColspan() != null && cellValue.getRowspan() != null && (cellValue.getColspan() != 0 || cellValue.getRowspan() != 0)) {
            sheet.addMergedRegion(new CellRangeAddress(cellValue.getRow(),
                    cellValue.getRow() + cellValue.getRowspan(),
                    cellValue.getColumn(),
                    cellValue.getColumn() + cellValue.getColspan()));

        }

    }


}
