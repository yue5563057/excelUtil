package test;

import com.dongyue.util.anno.IrregularExcel;
import com.dongyue.util.anno.IrregularFieldAnno;
import com.dongyue.util.anno.IrregularFieldNameAnno;
import com.dongyue.util.model.CellValue;
import com.dongyue.util.parse.ExcelUtilFactory;
import com.dongyue.util.parse.MergeExcelUtil;
import com.dongyue.util.parse.StandardExcelParse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {


    public static void main(String[] args) throws IOException {
//        List<TestModel> testModels = standard.standardExcelToList(new File("C:\\Users\\东岳\\Desktop\\水果全国分布表-10省.xlsx"), TestModel.class);
//        for (TestModel testModel : testModels) {
//            System.out.println(testModel.toString());
//        }
       export();
    }

    private static void export() {
        TestExportirregularModel testExportirregularModel = new TestExportirregularModel();
        testExportirregularModel.setFile("测试值");
        testExportirregularModel.setTitle("这是一个大标题。。。。。。。。。。。大标题");
        Class<? extends TestExportirregularModel> aClass = testExportirregularModel.getClass();
        IrregularExcel annotation = aClass.getAnnotation(IrregularExcel.class);
        String sheetName = "";
        String title ="";
        if(annotation==null){
            title = "xxxx";
            sheetName="sheetName";
        }else {
            sheetName = annotation.sheetName();
            title = annotation.title();
        }
        //第一步：创建一个webbook,对应一个excel
        HSSFWorkbook wb=new HSSFWorkbook();
//第二步：在webbook添加一个sheet，与之对应的是excle中的excel
        HSSFSheet sheet = wb.createSheet(sheetName);
// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
// 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Field[] fields = aClass.getDeclaredFields();
        List<CellValue> list = new ArrayList<>();
        for (Field field : fields) {
            IrregularFieldNameAnno fileName = field.getAnnotation(IrregularFieldNameAnno.class);
            if(fileName!=null){
                int cloumn = fileName.cloumn();
                int nameRow = fileName.row();
                CellValue cellValue = new CellValue();
                cellValue.setRow(nameRow);
                cellValue.setColumn(cloumn);
                cellValue.setValue(fileName.title());
                cellValue.setColspan(fileName.colspan());
                cellValue.setRowspan(fileName.rowspan());
                list.add(cellValue);
            }
            IrregularFieldAnno fieldAnno = field.getAnnotation(IrregularFieldAnno.class);
            if(fieldAnno!=null){
                int cloumn1 = fieldAnno.cloumn();
                int nameRow1 = fieldAnno.row();
                CellValue cellValue1 = new CellValue();
                cellValue1.setRow(nameRow1);
                cellValue1.setColumn(cloumn1);
                cellValue1.setColspan(fieldAnno.colspan());
                cellValue1.setRowspan(fieldAnno.rowspan());
                try {
                    field.setAccessible(Boolean.TRUE);
                    cellValue1.setValue(field.get(testExportirregularModel).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                list.add(cellValue1);
            }
        }

//HSSFCell对应的是每一行的数据插入
        for (CellValue cellValue : list) {
            setCellValue(sheet,cellValue,cellStyle);
        }
// 第六步，将文件存到指定位置
        try {
            FileOutputStream fout = new FileOutputStream("d://"+title+".xlsx");
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void setCellValue(Sheet sheet, CellValue cellValue,CellStyle cellStyle) {
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
        if(cellValue.getColspan()!=0||cellValue.getRowspan()!=0){
            sheet.addMergedRegion(new CellRangeAddress(cellValue.getRow(),
                    cellValue.getRow()+cellValue.getRowspan(),
                    cellValue.getColumn(),
                    cellValue.getColumn()+cellValue.getColspan()));

        }
    }

    /**
     * 合并excel解析成一个新的对象
     * @throws FileNotFoundException
     */
    private static void MergeExcelUtil() throws FileNotFoundException {
        StandardExcelParse<TestModel> standard = ExcelUtilFactory.getStandard();
        File file = new File("C:\\Users\\东岳\\Desktop\\已失效无付款信息.xlsx");
        File file1 = new File("C:\\Users\\东岳\\Desktop\\合作社管理员信息.xlsx");
        File file2 = new File("C:\\Users\\东岳\\Desktop\\uploadExcel.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileInputStream fileInputStream1 = new FileInputStream(file1);
        FileInputStream fileInputStream2 = new FileInputStream(file2);
        List<InputStream> fileList = new ArrayList<>();
        fileList.add(fileInputStream);
        fileList.add(fileInputStream1);
        fileList.add(fileInputStream2);
        List<List<String>> lists = new MergeExcelUtil().mergeExcel(fileList);
        List<TestModel> testModels1 = standard.standardExcelToList(lists, TestModel.class);
        for (TestModel testModel : testModels1) {
            System.out.println(testModel.toString());
        }
    }

}
