package com.dongyue.util.test;

import com.dongyue.util.Excel.ExcelUtilFactory;
import com.dongyue.util.Excel.ParseExcelUtil;
import com.dongyue.util.Excel.StandardExcelParse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) throws IOException {
        StandardExcelParse<TestModel> standard = ExcelUtilFactory.getStandard();
        List<TestModel> testModels = standard.standardExcelToList(new File("C:\\Users\\Administrator\\Desktop\\哆啦5月8号按状态修改.xls"), TestModel.class);
        for (TestModel testModel : testModels) {
            System.out.println(testModel.toString());
        }
    }

}
