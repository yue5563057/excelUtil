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
                CellValue cellValue = createCellValue(fileName.row(), fileName.cloumn(), fileName.title(), fileName.colspan(), fileName.rowspan());
                sheetMaxClomun = Math.max(fileName.colspan() + fileName.cloumn(), sheetMaxClomun);
                sheetMaxRow = Math.max(fileName.row(), sheetMaxRow);
                list.add(cellValue);
            }
            IrregularFieldAnno fieldAnno = field.getAnnotation(IrregularFieldAnno.class);
            if (fieldAnno != null) {
                try {
                    field.setAccessible(Boolean.TRUE);
                    CellValue cellValue1 = createCellValue(fieldAnno.row(), fieldAnno.cloumn()
                            , field.get(t).toString(), fieldAnno.colspan(), fieldAnno.rowspan());
                    sheetMaxClomun = Math.max(fieldAnno.colspan() + fieldAnno.cloumn(), sheetMaxClomun);
                    sheetMaxRow = Math.max(fieldAnno.row(), sheetMaxRow);
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
                            maxcolumn = Math.max(i + 1, maxcolumn);
                            cloumnFeild.put(i, declaredField.getName());
                        }
                        sheetMaxClomun = Math.max(maxcolumn, sheetMaxClomun);
                        try {
                            field.setAccessible(true);
                            List list1 = (List) field.get(t);
                            Integer listRow = irregularItem.startRow();
                            for (Object item : list1) {
                                listRow += title(listRow,irregularItem,maxcolumn,cloumnFeild,listItem,list);
                                //判断item是否有List 有的话需要先打印list
                                listRow += value(item,maxcolumn,cloumnFeild,listItem,listRow,list);
//                                boolean assignableFrom = false;
//                                Integer itemRow = 0;
//                                Field[] declaredFields1 = item.getClass().getDeclaredFields();
//                                for (Field field1 : declaredFields1) {
//                                    Class<?> fieldType = field1.getType();
//                                    assignableFrom = fieldType.isAssignableFrom(List.class);
//                                    if (assignableFrom) {
//                                        field1.setAccessible(true);
//                                        List collection = (List) field1.get(item);
//                                        itemRow = collection.size();
//                                        break;
//                                    }
//                                }
//                                Integer addRow;
//                                if (assignableFrom) {
//                                    for (int i = 0; i < maxcolumn; i++) {
//                                        String s = cloumnFeild.get(i);
//                                        if (StringUtil.isNotNull(s)) {
//                                            Field field1 = listItem.getDeclaredField(s);
//                                            Class<?> fieldType = field1.getType();
//                                            boolean isCollection = fieldType.isAssignableFrom(List.class);
//                                            if (isCollection) {
//                                                field1.setAccessible(true);
//                                                List collection = (List) field1.get(item);
//                                                Integer collectionIndex = listRow;
//                                                for (Object o : collection) {
//                                                    Class<?> item2Class = o.getClass();
//                                                    Field[] declaredFields2 = item2Class.getDeclaredFields();
//                                                    for (Field field2 : declaredFields2) {
//                                                        StandardExcelAttr attr = field2.getAnnotation(StandardExcelAttr.class);
//                                                        int itemColumn = attr.excelColumn();
//                                                        field2.setAccessible(true);
//                                                        Object o1 = field2.get(o);
//                                                        CellValue cellValue1;
//                                                        if (o1 == null) {
//                                                            cellValue1 = createCellValue(collectionIndex, i + itemColumn, "", 0, 0);
//                                                        } else {
//                                                            cellValue1 = createCellValue(collectionIndex, i + itemColumn, o1.toString(), 0, 0);
//                                                        }
//                                                        list.add(cellValue1);
//                                                    }
//                                                    collectionIndex++;
//                                                }
//                                            } else {
//                                                field1.setAccessible(true);
//                                                CellValue cellValue1;
//                                                if (field1.get(item) == null) {
//                                                    cellValue1 = createCellValue(listRow, i, "", 0, itemRow - 1);
//                                                } else {
//                                                    cellValue1 = createCellValue(listRow, i, field1.get(item).toString(), 0, itemRow - 1);
//                                                }
//                                                list.add(cellValue1);
//                                            }
//                                        }
//                                    }
//                                    addRow= (itemRow);
//                                } else {
//                                    for (int i = 0; i < maxcolumn; i++) {
//                                        String s = cloumnFeild.get(i);
//                                        if (StringUtil.isNotNull(s)) {
//                                            Field field1 = listItem.getDeclaredField(s);
//                                            field1.setAccessible(true);
//                                            CellValue cellValue1;
//                                            if (field1.get(item) == null) {
//                                                cellValue1 = createCellValue(listRow, i, "", 0, 0);
//                                            } else {
//                                                cellValue1 = createCellValue(listRow, i, field1.get(item).toString(), 0, 0);
//                                            }
//                                            list.add(cellValue1);
//                                        }
//                                    }
//                                    addRow =1;
//                                }
//                                listRow += addRow;
                            }
                            sheetMaxRow = Math.max(listRow, sheetMaxRow);
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


    private static Integer maxRow(Object item, Integer maxRow) throws IllegalAccessException {
        Field[] fields = item.getClass().getDeclaredFields();
        boolean assignableFrom = false;
        Integer row =0;
        for (Field field1 : fields) {
            Class<?> fieldType = field1.getType();
            assignableFrom = fieldType.isAssignableFrom(List.class);
            if (assignableFrom) {
                field1.setAccessible(true);
                List collection = (List) field1.get(item);
                for (Object o : collection) {
                    row += maxRow(o,maxRow);
                }
                break;
            }
        }
        if(!assignableFrom){
            return 1;
        }
        return maxRow+row;
    }

    private static Boolean isList(Object item) throws IllegalAccessException {
        Field[] fields = item.getClass().getDeclaredFields();
        for (Field field1 : fields) {
            Class<?> fieldType = field1.getType();
            boolean assignableFrom = fieldType.isAssignableFrom(List.class);
            if (assignableFrom) {
                return true;
            }
        }
        return false;
    }



    private static Integer value(Object item,Integer maxcolumn,Map<Integer, String> cloumnFeild,Class listItem,Integer listRow,List<CellValue> list
    ) throws IllegalAccessException, NoSuchFieldException {
        //判断item是否有List 有的话需要先打印list
        Integer itemRow=maxRow(item,0);
        Boolean assignableFrom = isList(item);
//        Field[] declaredFields1 = item.getClass().getDeclaredFields();
//        for (Field field1 : declaredFields1) {
//            Class<?> fieldType = field1.getType();
//            assignableFrom = fieldType.isAssignableFrom(List.class);
//            if (assignableFrom) {
//                field1.setAccessible(true);
//                List collection = (List) field1.get(item);
//                itemRow = collection.size();
//                break;
//            }
//        }
        Integer addRow = 0 ;
        if (assignableFrom) {
            for (int i = 0; i < maxcolumn; i++) {
                String s = cloumnFeild.get(i);
                if (StringUtil.isNotNull(s)) {
                    Field field1 = listItem.getDeclaredField(s);
                    Class<?> fieldType = field1.getType();
                    boolean isCollection = fieldType.isAssignableFrom(List.class);
                    if (isCollection) {
                        StandardExcelAttr annotation = field1.getAnnotation(StandardExcelAttr.class);
                        if(annotation!=null&&annotation.forIn()){
                            field1.setAccessible(true);
                            List collection = (List) field1.get(item);
                            Integer collectionIndex = listRow;
                            for (Object o : collection) {
                                Type genericType = field1.getGenericType();
                                ParameterizedType pt1 = (ParameterizedType) genericType;
                                Class listItem1 = (Class) pt1.getActualTypeArguments()[0];
                                Integer maxcolumn1 = 0;
                                Map<Integer, String> cloumnFeild1 = new HashMap<>();
                                Field[] declaredFields = listItem1.getDeclaredFields();
                                for (Field declaredField : declaredFields) {
                                    StandardExcelAttr standardExcelAttr = declaredField.getAnnotation(StandardExcelAttr.class);
                                    if(standardExcelAttr!=null){
                                        //导出列表
                                        int column = standardExcelAttr.excelColumn();
                                        maxcolumn1 = Math.max(column + 1, maxcolumn1);
                                        cloumnFeild1.put(column, declaredField.getName());
                                    }
                                }
                                addRow += value(o ,maxcolumn1,cloumnFeild1,listItem1,collectionIndex,list);
                                collectionIndex+=addRow;
                            }
                        }else{
                            field1.setAccessible(true);
                            List collection = (List) field1.get(item);
                            Integer collectionIndex = listRow;
                            for (Object o : collection) {
                                CellValue cellValue1;
                                if (o == null) {
                                    cellValue1 = createCellValue(collectionIndex, i, "", 0, 0);
                                } else {
                                    cellValue1 = createCellValue(collectionIndex, i, o.toString(), 0, 0);
                                }
                                list.add(cellValue1);
                                collectionIndex++;
                            }
                        }
                    } else {
                        field1.setAccessible(true);
                        CellValue cellValue1;
                        if (field1.get(item) == null) {
                            cellValue1 = createCellValue(listRow, i, "", 0, itemRow - 1);
                        } else {
                            cellValue1 = createCellValue(listRow, i, field1.get(item).toString(), 0, itemRow - 1);
                        }
                        list.add(cellValue1);
                    }
                }
            }
            addRow = (itemRow);
        } else {
            for (int i = 0; i < maxcolumn; i++) {
                String s = cloumnFeild.get(i);
                if (StringUtil.isNotNull(s)) {
                    Field field1 = listItem.getDeclaredField(s);
                    field1.setAccessible(true);
                    CellValue cellValue1;
                    if (field1.get(item) == null) {
                        cellValue1 = createCellValue(listRow, i, "", 0, 0);
                    } else {
                        cellValue1 = createCellValue(listRow, i, field1.get(item).toString(), 0, 0);
                    }
                    list.add(cellValue1);
                }
            }
            addRow =1;
        }
        return addRow;
    }



    private static Integer title(Integer listRow, IrregularItem irregularItem,
                              Integer maxcolumn, Map<Integer, String> cloumnFeild,Class listItem,List<CellValue> list) throws NoSuchFieldException {
        if (listRow.equals(irregularItem.startRow())) {
            //第一行打印标题
            for (int i = 0; i < maxcolumn; i++) {
                String s = cloumnFeild.get(i);
                if (StringUtil.isNotNull(s)) {
                    Field field1 = listItem.getDeclaredField(s);
                    update(field1,listRow,i,list);
                }
            }
            return 1;
        }
        return 0;
    }
    private static void update(Field field1,Integer listRow,Integer i,List<CellValue> list){
        StandardExcelAttr annotation1 = field1.getAnnotation(StandardExcelAttr.class);
        if (annotation1.useListTitle()) {
            Class<?> fieldType = field1.getType();
            boolean assignableFrom = fieldType.isAssignableFrom(List.class);
            if (assignableFrom) {
                Type genericType = field1.getGenericType();
                ParameterizedType pt1 = (ParameterizedType) genericType;
                Class listItem1 = (Class) pt1.getActualTypeArguments()[0];
                Field[] fields1 = listItem1.getDeclaredFields();
                for (Field field2 : fields1) {
                    StandardExcelAttr annotation2 = field2.getAnnotation(StandardExcelAttr.class);
                    update(field2,listRow,annotation2.excelColumn(),list);
                }
            }
        } else {
            if(!annotation1.useListTitle()){
                CellValue cellValue1 = createCellValue(listRow, i, annotation1.title(), 0, 0);
                list.add(cellValue1);
            }

        }
    }

    private static CellValue createCellValue(int nameRow, int column, String value, int cloSpan, int rowSpan) {
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
