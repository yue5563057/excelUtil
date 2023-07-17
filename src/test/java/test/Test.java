package test;

import com.dongyue.util.Excel.ExcelUtilFactory;
import com.dongyue.util.Excel.MergeExcelUtil;
import com.dongyue.util.Excel.StandardExcelParse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) throws IOException {
        StandardExcelParse<TestModel> standard = ExcelUtilFactory.getStandard();
//        List<TestModel> testModels = standard.standardExcelToList(new File("C:\\Users\\东岳\\Desktop\\水果全国分布表-10省.xlsx"), TestModel.class);
//        for (TestModel testModel : testModels) {
//            System.out.println(testModel.toString());
//        }
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
