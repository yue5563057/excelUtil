package com.dongyue.util.export;

        import org.apache.poi.hssf.usermodel.*;

        import java.io.FileOutputStream;
        import java.util.List;

/**
 * @author 东岳
 */
public class ExportExcel<T> {


    public void export(List<T> dicList){
        //第一步：创建一个webbook,对应一个excel
        HSSFWorkbook wb=new HSSFWorkbook();
//第二步：在webbook添加一个sheet，与之对应的是excle中的excel
        HSSFSheet sheet = wb.createSheet("1");
// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
// 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//HSSFCell对应的是每一行的数据插入
        HSSFCell cell = null;
        for (int i = 0; i < dicList.size(); i++)
        {
            cell = row.createCell((short) i);

            cell.setCellValue("");
            cell.setCellStyle(style);
        }

// 第六步，将文件存到指定位置
        try {
            FileOutputStream fout = new FileOutputStream("D:/Members.xls");
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
